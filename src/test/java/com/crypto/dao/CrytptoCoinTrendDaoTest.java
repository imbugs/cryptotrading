package com.crypto.dao;

import com.crypto.entities.MovingAverage;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
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

import static org.junit.Assert.assertEquals;

/**
 * Test the CryptocoinTrendDao
 * <p/>
 * Created by Jan Wicherink on 1-5-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class CrytptoCoinTrendDaoTest {

    @Inject
    private CryptocoinTrendDao cryptocoinTrendDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(CryptocoinTrendDaoImpl.class.getPackage())
                .addPackage(MovingAverage.class.getPackage())
                .addPackage(TrendType.class.getPackage())
                .addPackage(Trend.class.getPackage())
                .addPackage((TrendValue.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }


    @Test
    @UsingDataSet("datasets/it_test_dataset_13.xml")
    public void testGetTrendValue() {

        final Trend trend = new Trend(1, TrendType.EMA, 50, null);
        final TradePair tradePair = new TradePair();
        tradePair.setId(new Integer(1));

        final TrendValue trendValue = cryptocoinTrendDao.getTrendValue(100, trend, tradePair);

        assertEquals(new Float(100.1F), trendValue.getValue());
    }

}