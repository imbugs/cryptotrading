package com.crypto.dao;

import com.crypto.entities.MarketOrder;
import com.crypto.entities.Trading;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Test the MarketOrderDao
 *
 * Created by Jan Wicherink on 21-4-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class MarketOrderDaoTest {

    @Inject
    private MarketOrderDao marketOrderDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((MarketOrderDaoImpl.class).getPackage())
                .addPackage((MarketOrder.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_7.xml")
    public void testGetByOrderReference() {

        final MarketOrder marketOrder = marketOrderDao.getByOrderReference("OUR_ORDER_1");

        assertNotNull(marketOrder);
        assertEquals(new Integer(1), marketOrder.getId());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_7.xml")
    public void testGetLastSell (){

        final Trading trading = new Trading ();
        trading.setId(1);

        final MarketOrder marketOrder = marketOrderDao.getLastSell(new Integer(5), trading);

        assertNotNull(marketOrder);
        assertEquals(new Integer(4), marketOrder.getId());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_7.xml")
    public void testGetLastBuy (){

        final Trading trading = new Trading ();
        trading.setId(1);

        final MarketOrder marketOrder = marketOrderDao.getLastBuy(new Integer(5), trading);

        assertNotNull(marketOrder);
        assertEquals(new Integer(3), marketOrder.getId());
    }
}
