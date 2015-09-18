package com.crypto.trader;

import com.crypto.calculator.Calculator;
import com.crypto.calculator.bulk.CryptoCoinHistoryTrendCalculator;
import com.crypto.dao.MacdDao;
import com.crypto.dao.WithdrawalDao;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Jan Wicherink on 29-6-15.
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TraderTest {

    @EJB
    private Trader trader;

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

    @Before
    public void setup() {

        tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        currency = new Currency("EUR", "Euro", "&euro");
        cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 1F);
        trading = new Trading(1, 10F, 100F, 100F, 100F, false, false, false, 10F, true, true, 2, tradePair);

        wallet = new Wallet(trading, 100F, 100F, currency, cryptoCurrency, 100F);

        Fund currencyFund = new Fund(tradePair, 1000F, currency);
        Fund cryptoCurrencyFund = new Fund(tradePair, 1000F, cryptoCurrency);

        funds = new HashMap<>();
        funds.put(currency, currencyFund);
        funds.put(cryptoCurrency, cryptoCurrencyFund);

        trader.setFunds(funds);
        trader.setWallet(wallet);
        trader.setTrading(trading);

        Logger logger = new Logger();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse("2015-07-13 12:00", formatter);
        Timestamp timestamp = Timestamp.valueOf(date);

        cryptocoinHistory = new CryptocoinHistory(new Integer(30), timestamp, tradePair, 22.17F, 22.18F, 22.19F, 22.20F, 500L);
    }

    /**
     * Test creation of sell market order.
     */
    @Test
    public void testCreateSellMarketOrder() {

        // Arrange
        // Act
        SellMarketOrder sellMarketOrder = trader.createSellMarketOrder(cryptocoinHistory);

        //Assert
        assertNotNull(sellMarketOrder);

        assertEquals(22.20F, sellMarketOrder.getExchangeRate(), 0.01F);
        assertEquals(100F, sellMarketOrder.getCryptoCoins(), 0.01F);
        assertEquals(2197.8F, sellMarketOrder.getCoins(), 0.01F);
        assertEquals(22.20F, sellMarketOrder.getFee(), 0.01F);
    }


    /**
     * Test creation of sell market order.
     */
    @Test
    public void testCreateSellMarketOrderWithMaxCryptoCoinsPercentageOfTenPercent() {

        // Arrange
        this.trading.setMaxTradingCryptoCoinsPerc(10F);

        // Act
        SellMarketOrder sellMarketOrder = trader.createSellMarketOrder(cryptocoinHistory);

        //Assert
        assertNotNull(sellMarketOrder);

        assertEquals(22.20F, sellMarketOrder.getExchangeRate(), 0.01F);
        assertEquals(10F, sellMarketOrder.getCryptoCoins(), 0.01F);
        assertEquals(219.78F, sellMarketOrder.getCoins(), 0.01F);
        assertEquals(2.220F, sellMarketOrder.getFee(), 0.01F);
    }


    /**
     * Test creation of sell market order with no cryptocoins available
     */
    @Test
    public void testCreateSellMarketOrderWithNoCryptoCoins() {

        // Arrange
        this.trading.setMaxTradingCryptoCoinsPerc(0F);

        // Act
        SellMarketOrder sellMarketOrder = trader.createSellMarketOrder(cryptocoinHistory);

        //Assert
        assertNull(sellMarketOrder);
    }


    /**
     * Test creation of buy market order.
     */
    @Test
    public void testCreateBuyMarketOrder() {

        // Arrange

        // Act
        BuyMarketOrder buyMarketOrder = trader.createBuyMarketOrder(cryptocoinHistory);

        //Assert
        assertNotNull(buyMarketOrder);

        assertEquals(22.20F, buyMarketOrder.getExchangeRate(), 0.01F);
        assertEquals(100F, buyMarketOrder.getCoins(), 0.01F);
        assertEquals(4.4594F, buyMarketOrder.getCryptoCoins(), 0.0001F);
        assertEquals(1F, buyMarketOrder.getFee(), 0.01F);
    }

    /**
     * Test creation of buy market order with only 10% of coins.
     */
    @Test
    public void testCreateBuyMarketOrderWithMaxCoinsPercentageOfTenPercent() {

        // Arrange
        this.trading.setMaxTradingCoinsPerc(10F);

        // Act
        BuyMarketOrder buyMarketOrder = trader.createBuyMarketOrder(cryptocoinHistory);

        //Assert
        assertNotNull(buyMarketOrder);

        assertEquals(22.20F, buyMarketOrder.getExchangeRate(), 0.01F);
        assertEquals(10F, buyMarketOrder.getCoins(), 0.01F);
        assertEquals(0.44594F, buyMarketOrder.getCryptoCoins(), 0.0001F);
        assertEquals(0.1F, buyMarketOrder.getFee(), 0.01F);
    }

    /**
     * Test creation of buy market order with no coins available.
     */
    @Test
    public void testCreateBuyMarketOrderWithNoCoins() {

        // Arrange
        this.trading.setMaxTradingCoinsPerc(0F);

        // Act
        BuyMarketOrder buyMarketOrder = trader.createBuyMarketOrder(cryptocoinHistory);

        //Assert
        assertNull(buyMarketOrder);
    }


    @Test
    public void testUpdateWallet() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse("2015-07-13 12:00", formatter);
        Timestamp timestamp = Timestamp.valueOf(date);

        SellMarketOrder sellOrder = new SellMarketOrder(100, "Out order", this.trading, timestamp, 20F, MarketOrderStatus.OPEN, 2, false, 78F);
        sellOrder.calculateFee();

        assertEquals(15.6F, sellOrder.getFee(), 0.01F);
        assertEquals(100F, wallet.getCoins(), 0.01F);
        assertEquals(100F, wallet.getCryptoCoins(), 0.01F);

        assertEquals(1000F, trader.getFunds().get(currency).getCoins(), 0.01F);
        assertEquals(1000F, trader.getFunds().get(cryptoCurrency).getCoins(), 0.01F);

        trader.updateWallet(sellOrder);

        // Profit must have been restored to funds.
        assertEquals(0F, wallet.getCoins(), 0.01F);
        assertEquals(22F, wallet.getCryptoCoins(), 0.01F);

        assertEquals(2544.4F, trader.getFunds().get(currency).getCoins(), 0.01F);
        assertEquals(1000F, trader.getFunds().get(cryptoCurrency).getCoins(), 0.01F);
    }
}
