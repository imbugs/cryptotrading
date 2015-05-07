package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.entities.TradeRule;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Trade rule Dao test
 *
 * Created by Jan Wicherink on 7-5-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TradeRuleDaoTest {

    @Inject
    private TradeRuleDao tradeRuleDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((TradeRuleDao.class).getPackage())
                .addPackage((TradeRule.class).getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_15.xml")
    public void testGet() {
        final TradeRule tradeRule = tradeRuleDao.get(1);

        assertEquals(new Integer(1), tradeRule.getId());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_15.xml")
    public void testGetActiveTradeRules() {

        final List<TradeRule> tradeRules = tradeRuleDao.getActiveRules();

        assertEquals(2, tradeRules.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_15.xml")
    public void testGetActiveBullTradeRules() {

        final List<TradeRule> tradeRules = tradeRuleDao.getActiveBullRules();
        assertEquals(1, tradeRules.size());

        assertEquals(new Integer(1), tradeRules.get(0).getId());
        assertEquals(MarketTrend.BULL, tradeRules.get(0).getMarketTrend());
   }

    @Test
    @UsingDataSet("datasets/it_test_dataset_15.xml")
    public void testGetActiveBearTradeRules() {

        final List<TradeRule> tradeRules = tradeRuleDao.getActiveBearRules();
        assertEquals(1, tradeRules.size());

        assertEquals(new Integer(2), tradeRules.get(0).getId());
        assertEquals(MarketTrend.BEAR, tradeRules.get(0).getMarketTrend());
    }
}