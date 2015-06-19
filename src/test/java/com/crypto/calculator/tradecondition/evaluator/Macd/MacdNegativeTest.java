package com.crypto.calculator.tradecondition.evaluator.Macd;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.dao.impl.CryptocoinTrendDaoImpl;
import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.entities.*;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
import com.crypto.enums.LoggingLevel;
import com.crypto.enums.LogicalOperator;
import com.crypto.enums.TradeConditionType;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;
import com.crypto.tradecondition.evaluator.Macd.MacdNegative;
import com.crypto.tradecondition.evaluator.Macd.MacdPositive;
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
 * Test the MacdPositive evaluator if a macd value is positive
 * Created by Jan Wicherink on 16-6-15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class MacdNegativeTest {

    @Inject
    private MacdNegative macdNegative;

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
                .addPackage(MacdPositive.class.getPackage())
                .addPackage(Evaluator.class.getPackage())
                .addPackage(ConditionEvaluator.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_24.xml")
    public void testMacdPositive() {

        //Arrange
        final TradingSite tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        final Currency currency = new Currency("EUR", "Euro", "&euro");
        final CryptoCurrency cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        final TradePair tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 0.01F);
        final Trading trading = new Trading();
        trading.setTradePair(tradePair);

        final TradeRule tradeRule = new TradeRule();
        final Macd macd = new Macd(1, new Trend(), new Trend());

        final TradeCondition tradeCondition = new TradeCondition(1, tradeRule, TradeConditionType.POS_MACD_CHANGE, macd, null, null, null, 0F, 0F, 0F, 1, LogicalOperator.AND, true);

        macdNegative.setTrading(trading);
        macdNegative.setTradeCondition(tradeCondition);

        // Index = 1, period = 1
        macdNegative.setIndex(1);
        tradeCondition.setPeriod(1);

        // Act
        assertFalse(macdNegative.evaluate());

        // Index = 4, period = 1
        macdNegative.setIndex(4);
        tradeCondition.setPeriod(1);

        // Act
        assertTrue(macdNegative.evaluate());

        // Index = 3, period = 1
        macdNegative.setIndex(3);
        tradeCondition.setPeriod(1);

        // Act
        assertFalse(macdNegative.evaluate());

        // Index = 4, period = 1
        macdNegative.setIndex(4);
        tradeCondition.setPeriod(1);

        // Act
        assertTrue(macdNegative.evaluate());

        // Index = 6, period = 3
        macdNegative.setIndex(6);
        tradeCondition.setPeriod(3);

        // Act
        assertTrue(macdNegative.evaluate());

        // Index = 7 period = 2
        macdNegative.setIndex(7);
        tradeCondition.setLogicalOperator(LogicalOperator.OR);
        tradeCondition.setPeriod(2);

        // Act
        assertTrue(macdNegative.evaluate());
    }
}
