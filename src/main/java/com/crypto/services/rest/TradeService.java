package com.crypto.services.rest;

/**
 * Service to start a trading
 *
 * Created by Jan Wicherink on 12-9-2015.
 */

import com.crypto.dao.FundDao;
import com.crypto.dao.MarketOrderDao;
import com.crypto.dao.TradingDao;
import com.crypto.dao.WalletDao;
import com.crypto.entities.*;
import com.crypto.trader.MarketOrderTrader;
import com.crypto.util.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.Serializable;
import java.util.HashMap;
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
    private WalletDao walletDao;

    @EJB
    private MarketOrderDao marketOrderDao;

    @GET
    @Path("/trade/{tradingId}/{fromIndex}/{toIndex}/{coins}/{cryptoCoins}")
    public void trade(@PathParam("tradingId") Integer tradingId, @PathParam("fromIndex") Integer fromIndex, @PathParam("toIndex") Integer toIndex,
                      @PathParam("coins") Float coins, @PathParam("cryptoCoins") Float cryptoCoins) {

        final Trading trading = tradingDao.get(tradingId);
        final Currency currency = trading.getTradePair().getCurrency();
        final CryptoCurrency cryptoCurrency = trading.getTradePair().getCryptoCurrency();

        // Add the coins and crypto coins for this trading to the funding
        final Fund coinsFund = new Fund(trading.getTradePair(), coins, currency);
        final Fund cryptoCoinsFund = new Fund(trading.getTradePair(), cryptoCoins, cryptoCurrency);

        // Replace the current funds in the database with the ones past as arguments.
        fundDao.deleteAll(trading.getTradePair());
        fundDao.perist(coinsFund);
        fundDao.perist(cryptoCoinsFund);

        final Map<Currency, Fund> funds = new HashMap<>();
        funds.put(currency, coinsFund);
        funds.put(cryptoCurrency, cryptoCoinsFund);

        // Start with empty wallet, wallet is to be refunded from funds.
        final Wallet wallet = new Wallet(trading,0F, 0F, currency, cryptoCurrency, 0F);
        walletDao.deleteAll(trading);
        walletDao.persist(wallet);

        // Delete all market orders
        marketOrderDao.deleteAll(trading);

        Logger logger = new Logger();

        marketOrderTrader.setFromIndex(fromIndex);
        marketOrderTrader.setToIndex(toIndex);
        marketOrderTrader.setFunds(funds);
        marketOrderTrader.setWallet(wallet);
        marketOrderTrader.setTrading(trading);
        marketOrderTrader.setLogger(logger);

        marketOrderTrader.trade();
    }
}
