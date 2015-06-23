package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.Signal;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;
import com.crypto.entities.pkey.WithdrawalPk;
import com.crypto.enums.LoggingLevel;
import com.crypto.enums.MarketTrend;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jan Wicherink on 29-4-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class SignalDaoTest {

    @Inject
    private SignalDao signalDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Signal.class.getPackage())
                .addPackage((SignalDao.class).getPackage())
                .addPackage((MarketTrend.class).getPackage())
                .addPackage(MacdDao.class.getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(CryptocoinHistoryDaoImpl.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(WithdrawalPk.class.getPackage())
                .addPackage(SignalBulkDataHandler.class.getPackage())
                .addPackage(DataPersister.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_19.xml")
    public void testPersist() {

        final Trading trading = new Trading();
        trading.setId(1);

        final TradeRule tradeRule = new TradeRule(1, MarketTrend.BEAR, MarketTrend.BEAR, "Bear market", true);

        final Signal signal = new Signal(MarketTrend.BEAR, 1, tradeRule, trading);

        signalDao.persist(signal);

        final Signal persistedSignal = signalDao.get(1, tradeRule,trading);

        assertNotNull(persistedSignal);

        assertEquals(persistedSignal, signal);
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_12.xml")
    public void testGet() {

        assertNotNull (signalDao);

        final TradeRule tradeRule = new TradeRule();
        tradeRule.setId(2);

        final Trading trading = new Trading();
        trading.setId(1);

        final Signal signal = signalDao.get(100,tradeRule, trading);

        assertEquals(new Integer(100), signal.getIndx());
    }


    @Test
    @UsingDataSet("datasets/it_test_dataset_12.xml")
    public void testGetLast() {

        final TradeRule tradeRule = new TradeRule();
        tradeRule.setId(2);

        final Trading trading = new Trading();
        trading.setId(1);

        final Signal signal = signalDao.getLast(trading);

        assertEquals(new Integer(102), signal.getIndx());
    }
}
