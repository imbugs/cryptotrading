package com.crypto.calculator.tradecondition.evaluator.TrendGradualChange;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.dao.impl.CryptocoinTrendDaoImpl;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.entities.*;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
import com.crypto.enums.LoggingLevel;
import com.crypto.enums.LogicalOperator;
import com.crypto.enums.TradeConditionType;
import com.crypto.enums.TrendType;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;
import com.crypto.tradecondition.evaluator.TrendGradualChange.TrendDecrease;
import com.crypto.tradecondition.evaluator.TrendGradualChange.TrendIncrease;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Test the Trend increase evaluator.
 * Created by Jan Wicherink on 16-6-15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TrendDecreaseTest {

    @Inject
    private SignalBulkDataHandler signalBulkDataHandler;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((CryptocoinHistoryDao.class).getPackage())
                .addPackage((CryptocoinHistoryDaoImpl.class).getPackage())
                .addPackage((CryptocoinTrendDaoImpl.class).getPackage())
                .addPackage((TradeConditionLogDao.class).getPackage())
                .addPackage(CryptocoinHistory.class.getPackage())
                .addPackage((LoggingLevel.class).getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(DataIndexProvider.class.getPackage())
                .addPackage(TrendIncrease.class.getPackage())
                .addPackage(Evaluator.class.getPackage())
                .addPackage(ConditionEvaluator.class.getPackage())
                .addPackage(SignalBulkDataHandler.class.getPackage())
                .addPackage(DataPersister.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_31.xml")
    public void testTrendIncrease() {

        //Arrange
        final TradingSite tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        final Currency currency = new Currency("EUR", "Euro", "&euro");
        final CryptoCurrency cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        final TradePair tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 0.01F);
        final Trading trading = new Trading();
        trading.setTradePair(tradePair);

        final TradeRule tradeRule = new TradeRule();
        final Trend  trend = new Trend(1, TrendType.EMA, 50, null);

        final TradeCondition tradeCondition = new TradeCondition(1, tradeRule, TradeConditionType.TREND_DECREASE, null, trend, null, null, 34F, 101F, 0F, 1, LogicalOperator.AND, true);

        signalBulkDataHandler.setTrend(trend);
        signalBulkDataHandler.setTradePair(tradePair);

        final TrendDecrease trendDecrease = new TrendDecrease(signalBulkDataHandler);

        trendDecrease.setTrading(trading);
        trendDecrease.setTradeCondition(tradeCondition);

        // Index = 1, period = 1
        trendDecrease.setIndex(1);
        tradeCondition.setPeriod(1);

        // Act
        assertFalse(trendDecrease.evaluate());

        // Index = 3, period = 1
        trendDecrease.setIndex(3);
        tradeCondition.setPeriod(1);

        // Act
        assertTrue(trendDecrease.evaluate());

        // Index = 4, period = 1
        trendDecrease.setIndex(4);
        tradeCondition.setPeriod(1);

        // Act
        assertTrue(trendDecrease.evaluate());


        // Index = 3, period = 3
        trendDecrease.setIndex(3);
        tradeCondition.setPeriod(3);

        // Act
        assertTrue(trendDecrease.evaluate());
    }
}
