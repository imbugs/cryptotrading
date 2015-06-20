package com.crypto.calculator.tradecondition.evaluator.TrendPercentage;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.dao.TradePairDao;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.dao.impl.CryptocoinTrendDaoImpl;
import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.entities.*;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
import com.crypto.enums.LoggingLevel;
import com.crypto.enums.LogicalOperator;
import com.crypto.enums.TradeConditionType;
import com.crypto.enums.TrendType;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;
import com.crypto.tradecondition.evaluator.PercentageTrend.BTCGreaterThanPercentageTrend;
import com.crypto.tradecondition.evaluator.Trend.BTCGreaterThanTrend;
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
 * Crypto coin history closing greater than trend test
 * Created by Jan Wicherink on 19-6-15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class BTCGreaterThanTrendPercentageTest {

    @Inject
    private BTCGreaterThanPercentageTrend btcGreaterThanPercentageTrend;

    @Inject
    private TradePairDao tradePairDao;

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
                .addPackage(BTCGreaterThanPercentageTrend.class.getPackage())
                .addPackage(Evaluator.class.getPackage())
                .addPackage(ConditionEvaluator.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_27.xml")
    public void testBTCGreaterThanPercentageTrend() {

        //Arrange
        final TradingSite tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        final Currency currency = new Currency("EUR", "Euro", "&euro");
        final CryptoCurrency cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        final TradePair tradePair = tradePairDao.get(1);
        final Trading trading = new Trading();
        trading.setTradePair(tradePair);

        final TradeRule tradeRule = new TradeRule();
        final Trend trend = new Trend(1, TrendType.EMA, 50, null);

        final TradeCondition tradeCondition = new TradeCondition(1, tradeRule, TradeConditionType.BTC_GT_TREND, null, trend, null, null, 60F, 0F, 0F, 1, LogicalOperator.AND, true);

        btcGreaterThanPercentageTrend.setTrading(trading);
        btcGreaterThanPercentageTrend.setTradeCondition(tradeCondition);

        // Index = 1, period = 1
        btcGreaterThanPercentageTrend.setIndex(7);
        tradeCondition.setPeriod(1);
        tradeCondition.setPercentage(60F);

        // Act
        assertFalse(btcGreaterThanPercentageTrend.evaluate());

        // Index = 1, period = 1
        btcGreaterThanPercentageTrend.setIndex(7);
        tradeCondition.setPeriod(1);
        tradeCondition.setPercentage(59F);

        // Act
        assertTrue(btcGreaterThanPercentageTrend.evaluate());

        // Index = 1, period = 1
        btcGreaterThanPercentageTrend.setIndex(7);
        tradeCondition.setPeriod(1);
        tradeCondition.setPercentage(61F);

        // Act
        assertFalse(btcGreaterThanPercentageTrend.evaluate());

        // Index = 1, period = 1
        btcGreaterThanPercentageTrend.setIndex(7);
        tradeCondition.setPeriod(1);
        tradeCondition.setPercentage(60F);

        // Act
        assertFalse(btcGreaterThanPercentageTrend.evaluate());

        // Index = 8, period = 2
        btcGreaterThanPercentageTrend.setIndex(8);
        tradeCondition.setPeriod(2);
        tradeCondition.setPercentage(59F);

        // Act
        assertTrue(btcGreaterThanPercentageTrend.evaluate());

        // Index = 8, period = 2
        btcGreaterThanPercentageTrend.setIndex(9);
        tradeCondition.setPeriod(3);
        tradeCondition.setPercentage(60F);
        tradeCondition.setLogicalOperator(LogicalOperator.OR);

        // Act
        assertTrue(btcGreaterThanPercentageTrend.evaluate());
    }
}