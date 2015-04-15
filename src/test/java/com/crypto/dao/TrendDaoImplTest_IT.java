package com.crypto.dao;


import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.Trend;
import junit.framework.Assert;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Jan Wicherink on 15-4-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase= TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TrendDaoImplTest_IT {

    @Inject
    private TrendDao trendDoa;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test2.war")
                .addPackage((TrendDaoImpl.class).getPackage())
                .addPackage((Trend.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_3.xml")
    public void testGet() {

        final Integer id = new Integer(1);
        final Trend trend = trendDoa.get(id);

        assertNotNull (trend);

        assertEquals(new Integer(50), trend.getPeriod());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_3.xml")
    public void testGetAllMovingAverageTrends() {

        final List<Trend> trends = trendDoa.getAllMovingAverageTrends();

        assertNotNull(trends);
        assertEquals(2, trends.size());

        assertEquals(new Integer(1), trends.get(0).getId());
        assertEquals(new Integer(2), trends.get(1).getId());
    }


    @Test
    @UsingDataSet("datasets/it_test_dataset_3.xml")
    public void testGetAllExponentialMovingAverageTrends() {

        final List<Trend> trends = trendDoa.getAllExponentialMovingAverageTrends();

        assertNotNull(trends);
        assertEquals(2, trends.size());

        assertEquals(new Integer(3), trends.get(0).getId());
        assertEquals(new Integer(4), trends.get(1).getId());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_3.xml")
    public void testGetAllSmoothingMovingAverageTrends() {

        final List<Trend> trends = trendDoa.getAllSmoothingMovingAverageTrends();

        assertNotNull(trends);
        assertEquals(2, trends.size());

        assertEquals(new Integer(5), trends.get(0).getId());
        assertEquals(new Integer(6), trends.get(1).getId());
    }
}
