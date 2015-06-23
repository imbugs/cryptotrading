package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.Trading;
import com.crypto.entities.TradingSite;
import com.crypto.entities.pkey.WithdrawalPk;
import com.crypto.enums.LoggingLevel;
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
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Jan Wicherink on 26-4-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TradingDaoTest {

    @Inject
    private TradingDao tradingDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((Trading.class).getPackage())
                .addPackage((TradingDao.class).getPackage())
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
    @UsingDataSet("datasets/it_test_dataset_11.xml")
    public void testGet() {
        final Trading trading = tradingDao.get(1);

        assertEquals(new Integer(1), trading.getId());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_11.xml")
    public void testGetAll() {
        final List<Trading> tradings = tradingDao.getAll();

        assertEquals(3, tradings.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_11.xml")
    public void testGetActiveTradings() {
        final List<Trading> tradings = tradingDao.getActiveTradings();

        assertEquals(2, tradings.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_11.xml")
    public void testGetActiveTradingsOfTradingSite() {

        final TradingSite tradingSite = new TradingSite();
        tradingSite.setCode("BTCE");

        final List<Trading> tradings = tradingDao.getActiveTradingsOfTradingSite(tradingSite);

        assertEquals(2, tradings.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_11.xml")
    public void testUpdate() {

        final Trading trading = tradingDao.get(1);

        trading.setMaxTradingCoinsPerc(99F);
        trading.setMinProfitPercentage(1F);
        trading.setRefundPercentage(23F);

        tradingDao.update(trading);

        tradingDao.get(1);

        assertEquals(new Float(99), trading.getMaxTradingCoinsPerc());
        assertEquals(new Float(1), trading.getMinProfitPercentage());
        assertEquals(new Float(23), trading.getRefundPercentage());
    }
}
