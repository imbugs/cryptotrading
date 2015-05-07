package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradeRule;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
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
 * Test Trade condition Dao
 * Created by Jan Wicherink on 6-5-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TradeConditionDaoTest_IT {

    @Inject
    private TradeConditionDao tradeConditionDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(TradeCondition.class.getPackage())
                .addPackage(TradeConditionDao.class.getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_18.xml")
    public void testGet() {

        final TradeCondition tradeCondition = tradeConditionDao.get(1);

        assertEquals(new Integer(1), tradeCondition.getId());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_18.xml")
    public void testGetAllActiveTradeConditionsOfTradeRule() {

       final TradeRule tradeRule= new TradeRule();
        tradeRule.setId(1);

       final List<TradeCondition> tradeConditions = tradeConditionDao.getAllActiveTradeConditionsOfTradeRule(tradeRule);

       assertEquals(1, tradeConditions.size());
    }
}