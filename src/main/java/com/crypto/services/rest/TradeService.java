package com.crypto.services.rest;

/**
 * Service to start a trading
 *
 * Created by Jan Wicherink on 12-9-2015.
 */

import com.crypto.dao.*;
import com.crypto.entities.*;
import com.crypto.services.rest.wrapper.Balance;
import com.crypto.trader.MarketOrderTrader;
import com.crypto.util.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@Path("/")
public class TradeService implements Serializable {

    private static final long serialVersionUID = -1843805809568098698L;

    @EJB
    private TradingDao tradingDao;

    @EJB
    private MarketOrderTrader marketOrderTrader;

    @EJB
    private FundDao fundDao;

    @EJB
    private WithdrawalDao withdrawalDao;

    @EJB
    private WalletDao walletDao;

    @EJB
    private MarketOrderDao marketOrderDao;

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private LoggingDao loggingDao;

    @EJB
    private Logger logger;

    @GET
    @Path("/trade/{tradingId}/{fromIndex}/{toIndex}/{coins}/{cryptoCoins}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Balance> trade(@PathParam("tradingId") Integer tradingId, @PathParam("fromIndex") Integer fromIndex, @PathParam("toIndex") Integer toIndex,
                               @PathParam("coins") Float coins, @PathParam("cryptoCoins") Float cryptoCoins) {

        final Trading trading = tradingDao.get(tradingId);
        final TradePair tradePair = trading.getTradePair();
        final Currency currency = tradePair.getCurrency();
        final CryptoCurrency cryptoCurrency = tradePair.getCryptoCurrency();

        // Add the coins and crypto coins for this trading to the funding
        Fund fundCoinsBeforeTrade = new Fund(tradePair, coins, currency);
        Fund fundCryptoCoinsBeforeTrade = new Fund(tradePair, cryptoCoins, cryptoCurrency);

        // Replace the current funds in the database with the ones past as arguments.
        fundDao.deleteAll(tradePair);
        final Map<Currency, Fund> funds = new HashMap<>();
        funds.put(currency, fundCoinsBeforeTrade);
        funds.put(cryptoCurrency, fundCryptoCoinsBeforeTrade);
        fundDao.persist(fundCoinsBeforeTrade);
        fundDao.persist(fundCryptoCoinsBeforeTrade);

        // Remove withdrawals from the funds
        withdrawalDao.deleteAll(trading);

        // Start with empty wallet, wallet is to be refunded from funds.
        Wallet walletBeforeTrade = new Wallet(trading,0F, 0F, currency, cryptoCurrency, 0F);
        walletDao.deleteAll(trading);
        walletDao.persist(walletBeforeTrade);

        // Delete all market orders
        marketOrderDao.deleteAll(trading);

        // Delete current logger
        loggingDao.deleteAll(trading);

        marketOrderTrader.setFromIndex(fromIndex);
        marketOrderTrader.setToIndex(toIndex);
        marketOrderTrader.setFunds(funds);
        marketOrderTrader.setWallet(walletBeforeTrade);
        marketOrderTrader.setTrading(trading);
        marketOrderTrader.setLogger(logger);

        CryptocoinHistory currentRate = cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(tradePair, fromIndex);

        final Balance balanceBeforeTrade = new Balance(0F, 0F, fundCoinsBeforeTrade, fundCryptoCoinsBeforeTrade, null, null, currentRate.getClose());
        balanceBeforeTrade.calculateTotalValue();

        marketOrderTrader.trade();

        // After trade, get balance sheet of trading.
        Fund fundCoinsAfterTrade = fundDao.get(tradePair,currency);
        Fund fundCryptoCoinsAfterTrade = fundDao.get(tradePair, cryptoCurrency);
        Wallet walletAfterTrade = walletDao.get(trading);
        Withdrawal withdrawalCoins = withdrawalDao.get(trading, currency);
        Withdrawal withdrawalCryptoCoins = withdrawalDao.get(trading, cryptoCurrency);

        currentRate = cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(tradePair, toIndex);

        // Balance after trading
        final Balance balanceAfterTrade = new Balance(walletAfterTrade.getCoins(), walletAfterTrade.getCryptoCoins(), fundCoinsAfterTrade, fundCryptoCoinsAfterTrade, withdrawalCoins, withdrawalCryptoCoins, currentRate.getClose());
        balanceAfterTrade.calculateTotalValue();
        Float profit = balanceAfterTrade.getTotalValue() - balanceBeforeTrade.getTotalValue();
        balanceAfterTrade.setProfit(profit);

        // Balance without trading
        final Balance balanceWithoutTrade = new Balance(0F, 0F, fundCoinsBeforeTrade, fundCryptoCoinsBeforeTrade, null, null, currentRate.getClose());
        balanceWithoutTrade.calculateTotalValue();
        profit = balanceWithoutTrade.getTotalValue() - balanceBeforeTrade.getTotalValue();
        balanceWithoutTrade.setProfit(profit);

        List<Balance> balanceList = new ArrayList<>();
        balanceList.add(balanceBeforeTrade);
        balanceList.add(balanceAfterTrade);
        balanceList.add(balanceWithoutTrade);

        return balanceList;
    }
}
