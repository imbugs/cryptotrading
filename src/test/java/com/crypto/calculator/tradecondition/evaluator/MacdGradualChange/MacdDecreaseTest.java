package com.crypto.calculator.tradecondition.evaluator.MacdGradualChange;

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
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;
import com.crypto.tradecondition.evaluator.MacdGradualChange.MacdDecrease;
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
 * Test the Macd increase evaluator.
 * Created by Jan Wicherink on 16-6-15.
 */
@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class MacdDecreaseTest {

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
                .addPackage(MacdDecrease.class.getPackage())
                .addPackage(Evaluator.class.getPackage())
                .addPackage(ConditionEvaluator.class.getPackage())
                .addPackage(ConditionEvaluator.class.getPackage())
                .addPackage(SignalBulkDataHandler.class.getPackage())
                .addPackage(DataPersister.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_29.xml")
    public void testMacdDecrease() {

        //Arrange
        final TradingSite tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
        final Currency currency = new Currency("EUR", "Euro", "&euro");
        final CryptoCurrency cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        final TradePair tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 0.01F);
        final Trading trading = new Trading();
        trading.setTradePair(tradePair);

        final TradeRule tradeRule = new TradeRule();
        final Macd macd = new Macd(1, new Trend(), new Trend());

        final TradeCondition tradeCondition = new TradeCondition(1, tradeRule, TradeConditionType.MACD_DECREASE, macd, null, null, null, 34F, 101F, 0F, 1, LogicalOperator.AND, true);

        signalBulkDataHandler.setMacd(macd);
        signalBulkDataHandler.setTrading(trading);

        final MacdDecrease macdDecrease = new MacdDecrease(signalBulkDataHandler);

        macdDecrease.setTrading(trading);
        macdDecrease.setTradeCondition(tradeCondition);

        // Index = 1, period = 1
        macdDecrease.setIndex(1);
        tradeCondition.setPeriod(1);

        // Act
        assertFalse(macdDecrease.evaluate());

        // Index = 3, period = 1
        macdDecrease.setIndex(3);
        tradeCondition.setPeriod(1);

        // Act
        assertTrue(macdDecrease.evaluate());

        // Index = 4, period = 1
        macdDecrease.setIndex(4);
        tradeCondition.setPeriod(1);

        // Act
        assertTrue(macdDecrease.evaluate());


        // Index = 3, period = 3
        macdDecrease.setIndex(3);
        tradeCondition.setPeriod(3);

        // Act
        assertTrue(macdDecrease.evaluate());
    }
}
