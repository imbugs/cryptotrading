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

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jan Wicherink on 29-6-15.
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TraderTest {

    @Inject
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
    CryptocoinHistory cryptocoinHistory;

    @Before
    public void setup() {

        tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        currency = new Currency("EUR", "Euro", "&euro");
        cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 1F);
        trading = new Trading(1, 10F, 100F, 100F, 100F, false, false, false, 10F, true, true, 2, tradePair);

        wallet = new Wallet(trading, 100F, 100F, currency, cryptoCurrency, 100F);

        trader.setWallet(wallet);
        trader.setTrading(trading);

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
        assertEquals(4.4594F, buyMarketOrder.getCryptoCoins(), 0.01F);
        assertEquals(1F, buyMarketOrder.getFee(), 0.01F);
    }
}


