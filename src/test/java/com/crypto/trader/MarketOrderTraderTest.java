package com.crypto.trader;

import com.crypto.calculator.Calculator;
import com.crypto.calculator.bulk.CryptoCoinHistoryTrendCalculator;
import com.crypto.dao.*;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.*;
import com.crypto.entities.pkey.WithdrawalPk;
import com.crypto.enums.LoggingLevel;
import com.crypto.enums.MarketOrderStatus;
import com.crypto.util.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Jan Wicherink on 29-6-15.
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class MarketOrderTraderTest {

    @EJB
    private MarketOrderTrader trader;

    @EJB
    private MarketOrderDao marketOrderDao;

    @EJB
    private CurrencyDao currencyDao;

    @EJB
    private TradingDao tradingDao;

    @EJB
    private TradeRuleDao tradeRuleDao;

    @EJB
    private FundDao fundDao;

    @EJB
    private
    TradePairDao tradePairDao;


    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((WithdrawalDao.class).getPackage())
                .addPackage((Withdrawal.class).getPackage())
                .addPackage(WithdrawalPk.class.getPackage())
                .addPackage(MacdDao.class.getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(CryptocoinHistoryDaoImpl.class.getPackage())
                .addPackage(SignalBulkDataHandler.class.getPackage())
                .addPackage(DataPersister.class.getPackage())
                .addPackage(Trader.class.getPackage())
                .addPackage(Logger.class.getPackage())
                .addPackage(CryptoCoinHistoryTrendCalculator.class.getPackage())
                .addPackage(Calculator.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    TradingSite tradingSite;
    Currency currency;
    CryptoCurrency cryptoCurrency;
    TradePair tradePair;
    Trading trading;
    Wallet wallet;
    Map<Currency, Fund> funds;
    CryptocoinHistory cryptocoinHistory;


    @Test
    @UsingDataSet("datasets/it_test_dataset_7.xml")
    public void testBadBuy() {

        tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        currency = new Currency("EUR", "Euro", "&euro");
        cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 1F);
        trading = new Trading(1, 10F, 100F, 100F, 100F, false, false, false, 10F, true, true, 2, tradePair);

        wallet = new Wallet(trading, 100F, 10F, currency, cryptoCurrency, 100F);

        Fund currencyFund = new Fund(tradePair, 1000F, currency);
        Fund cryptoCurrencyFund = new Fund(tradePair, 1000F, cryptoCurrency);

        funds = new HashMap<>();
        funds.put(currency, currencyFund);
        funds.put(cryptoCurrency, cryptoCurrencyFund);

        trader.setFunds(funds);
        trader.setWallet(wallet);
        trader.setTrading(trading);

        Logger logger = new Logger();
        trader.setLogger(logger);

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final LocalDateTime date = LocalDateTime.parse("2015-07-13 12:00", formatter);
        final Timestamp timestamp = Timestamp.valueOf(date);
        final CryptocoinHistory cryptocoinHistory = new CryptocoinHistory(100, timestamp, tradePair, 100F, 100F, 100F, 100F, 100L);

        trading.setCheckBadBuy(true);
        assertFalse(trader.badBuyTrade(cryptocoinHistory));

        cryptocoinHistory.setClose(198F);
        assertFalse(trader.badBuyTrade(cryptocoinHistory));

        cryptocoinHistory.setClose(199F);
        assertTrue(trader.badBuyTrade(cryptocoinHistory));

        cryptocoinHistory.setClose(199F);
        assertTrue(trader.badBuyTrade(cryptocoinHistory));

        trading.setCheckBadBuy(false);
        assertFalse(trader.badBuyTrade(cryptocoinHistory));
    }


    @Test
    @UsingDataSet("datasets/it_test_dataset_7.xml")
    public void testBadSell() {

        tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        currency = new Currency("EUR", "Euro", "&euro");
        cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 1F);
        trading = new Trading(1, 10F, 100F, 100F, 100F, false, false, false, 10F, true, true, 2, tradePair);

        wallet = new Wallet(trading, 100F, 10F, currency, cryptoCurrency, 100F);

        Fund currencyFund = new Fund(tradePair, 1000F, currency);
        Fund cryptoCurrencyFund = new Fund(tradePair, 1000F, cryptoCurrency);

        funds = new HashMap<>();
        funds.put(currency, currencyFund);
        funds.put(cryptoCurrency, cryptoCurrencyFund);

        trader.setFunds(funds);
        trader.setWallet(wallet);
        trader.setTrading(trading);

        Logger logger = new Logger();
        trader.setLogger(logger);

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final LocalDateTime date = LocalDateTime.parse("2015-07-13 12:00", formatter);
        final Timestamp timestamp = Timestamp.valueOf(date);
        final CryptocoinHistory cryptocoinHistory = new CryptocoinHistory(100, timestamp, tradePair, 222F, 222F, 222F, 222F, 100L);

        trading.setCheckBadSellWallet(false);
        trading.setCheckBadSell(true);
        trading.setMinProfitPercentage(10F);

        assertTrue(trader.badSellTrade(cryptocoinHistory));

        trading.setCheckBadSell(false);
        assertFalse(trader.badSellTrade(cryptocoinHistory));

        trading.setCheckBadSell(true);
        cryptocoinHistory.setClose(223F);
        assertFalse(trader.badSellTrade(cryptocoinHistory));

        trading.setCheckBadSell(true);
        trading.setCheckBadSellWallet(true);
        trading.setMinProfitPercentage(0F);

        cryptocoinHistory.setClose(99F);
        assertTrue(trader.badSellTrade(cryptocoinHistory));

        cryptocoinHistory.setClose(102F);
        assertFalse(trader.badSellTrade(cryptocoinHistory));

        trading.setMinProfitPercentage(9F);
        cryptocoinHistory.setClose(111F);
        assertTrue(trader.badSellTrade(cryptocoinHistory));

        cryptocoinHistory.setClose(112F);
        assertFalse(trader.badSellTrade(cryptocoinHistory));
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_32.xml")
    public void testTradeAtIndex() {

        currency = currencyDao.get("DLR");
        cryptoCurrency = (CryptoCurrency) currencyDao.get("BTC");
        assertNotNull(currency);
        assertNotNull(cryptoCurrency);

        tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        // trading = new Trading(1, 10F, 100F, 100F, 100F, false, false, false, 10F, true, true, 2, tradePair);
        trading = tradingDao.get(1);
        assertNotNull(trading);

        wallet = new Wallet(trading, 100F, 10F, currency, cryptoCurrency, 100F);

        Fund currencyFund = fundDao.get(tradePair, currency);
        Fund cryptoCurrencyFund = fundDao.get(tradePair, cryptoCurrency);

        assertNotNull (cryptoCurrencyFund);
        assertNotNull (currencyFund);

        funds = new HashMap<>();
        funds.put(currency, currencyFund);
        funds.put(cryptoCurrency, cryptoCurrencyFund);

        trader.setFunds(funds);
        trader.setWallet(wallet);
        trader.setTrading(trading);

        Logger logger = new Logger();
        trader.setLogger(logger);

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final LocalDateTime date = LocalDateTime.parse("2015-07-13 12:00", formatter);
        final Timestamp timestamp = Timestamp.valueOf(date);
        final CryptocoinHistory cryptocoinHistory = new CryptocoinHistory(100, timestamp, tradePair, 222F, 222F, 222F, 222F, 100L);

        trading.setCheckBadSellWallet(false);
        trading.setCheckBadSell(true);
        trading.setCheckBadBuy(true);
        trading.setMinProfitPercentage(10F);

        final MarketOrder lastSellOrder = marketOrderDao.getLastSell(1000, trading);

        assertEquals(new Integer(6), lastSellOrder.getIndex());

        trader.tradeAtIndex(cryptocoinHistory);

        final MarketOrder sellOrder = marketOrderDao.getLastSell(1000, trading);

        assertEquals(new Integer(7), lastSellOrder.getIndex());
    }
}
