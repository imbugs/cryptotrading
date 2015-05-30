package com.crypto.calculator.bulk;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
import com.crypto.enums.LoggingLevel;
import com.crypto.enums.TrendType;
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

import static junit.framework.TestCase.assertNotNull;

/**
 * Test the bulk CryptoCoinHistoryTrendCalculator
 * Created by Jan Wicherink on 18-5-2015.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class CryptoCoinHistoryTrendCalculatorTest {

    @Inject
    private CryptoCoinHistoryTrendCalculator cryptoCoinHistoryTrendCalculator;

    @Inject
    private CryptocoinTrendDao cryptocoinTrendDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((CryptocoinHistoryDao.class).getPackage())
                .addPackage((CryptocoinHistoryDaoImpl.class).getPackage())
                .addPackage((CryptocoinHistory.class).getPackage())
                .addPackage((LoggingLevel.class).getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(CryptoCoinHistoryTrendCalculator.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(DataPersister.class.getPackage())
                .addPackage(CryptoCoinHistoryBulkDataHandler.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_23.xml")
    public void testCalculation() {

        final TradePair tradePair = new TradePair();
        final Trend trend = new Trend(1, TrendType.MA, 50, null);
        tradePair.setId(new Integer(1));

        cryptoCoinHistoryTrendCalculator.init(tradePair);
        cryptoCoinHistoryTrendCalculator.calculate();

   //     assertNotNull(cryptocoinTrendDao.getTrendValue(new Integer(1), trend, tradePair));
    }
}