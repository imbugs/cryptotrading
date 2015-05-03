package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.entities.BuyLimitOrder;
import com.crypto.entities.LimitOrder;
import com.crypto.entities.SellLimitOrder;
import com.crypto.entities.Trading;
import com.crypto.entities.pkey.OrderPk;
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
 * Created by Jan Wicherink on 3-5-15.
 */

@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class LimitOrderDaoTest {

    @Inject
    private LimitOrderDao limitOrderDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((LimitOrderDao.class).getPackage())
                .addPackage((LimitOrder.class).getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(OrderPk.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_16.xml")
    public void testGetOpenLimitOrders() {

        final Trading trading = new Trading();
        trading.setId(1);

        final List <LimitOrder> limitOrders = limitOrderDao.getOpenLimitOrders(trading);

        assertEquals(3, limitOrders.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_16.xml")
    public void testNumberOfOpenLimitOrders() {

        final Trading trading = new Trading();
        trading.setId(1);

        final Long nrOrders = limitOrderDao.getNumberOfOpenLimitOrders(trading);

        assertEquals(new Long(3), nrOrders);
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_16.xml")
    public void testGetLastSellLimitOrder() {

        final Trading trading = new Trading();
        trading.setId(1);

        final SellLimitOrder limitOrder = (SellLimitOrder) limitOrderDao.getLastSell(new Integer(4), trading);

        assertEquals(new Integer(2), limitOrder.getIndex());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_16.xml")
    public void testGetLastBuyLimitOrder() {

        final Trading trading = new Trading();
        trading.setId(1);

        final BuyLimitOrder limitOrder = (BuyLimitOrder) limitOrderDao.getLastBuy(new Integer(4), trading);

        assertEquals(new Integer(3), limitOrder.getIndex());
    }


}
