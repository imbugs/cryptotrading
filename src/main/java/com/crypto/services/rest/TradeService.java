package com.crypto.services.rest;

/**
 * Service to start a trading
 *
 * Created by Jan Wicherink on 12-9-2015.
 */

import com.crypto.dao.FundDao;
import com.crypto.dao.TradingDao;
import com.crypto.dao.WalletDao;
import com.crypto.entities.*;
import com.crypto.trader.MarketOrderTrader;
import com.crypto.util.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.HashMap;
import java.util.Map;

@Stateless
@Path("/")
public class TradeService {

    @EJB
    private TradingDao tradingDao;

    @EJB
    private FundDao fundDao;

    @EJB
    private WalletDao walletDao;

    @GET
    @Path("/trade/{tradingId}/{fromIndex}/{toIndex}")
    public void trade(@PathParam("tradingId") Integer tradingId, @PathParam("fromIndex") Integer fromIndex, @PathParam("toIndex") Integer toIndex) {

        final Trading trading = tradingDao.get(tradingId);
        final Currency currency = trading.getTradePair().getCurrency();
        final CryptoCurrency cryptoCurrency = trading.getTradePair().getCryptoCurrency();

        final Fund coinsFund = fundDao.get(trading.getTradePair(), currency);
        final Fund cryptoCoinsFund = fundDao.get(trading.getTradePair(), cryptoCurrency);

        final Map<Currency, Fund> funds = new HashMap<>();
        funds.put(currency, coinsFund);
        funds.put(cryptoCurrency, cryptoCoinsFund);

        Wallet wallet = walletDao.get(trading);

        Logger logger = new Logger();

        final MarketOrderTrader marketOrderTrader = new MarketOrderTrader(fromIndex, funds, wallet, trading, logger);

        marketOrderTrader.trade();
    }
}
