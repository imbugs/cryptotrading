package com.crypto.calculator.bulk;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.*;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;
import com.crypto.enums.LoggingLevel;
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

import java.util.concurrent.ExecutionException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Test the bulk CryptoCoinHistoryTrendCalculator
 * <p/>
 * Using the following data table:
 * Example data taken from : http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
 * <p/>
 * <p/>
 * Index Date	        Price	10-day MA	10-day EMA  10=day SMA
 * 1     24-Mar--2010	22.27
 * 2     25-Mar--2010	22.19
 * 3     26-Mar--2010	22.08
 * 4     29-Mar--2010	22.17
 * 5     30-Mar--2010	22.18
 * 6     31-Mar--2010	22.13
 * 7     1-Apr--2010	22.23
 * 8     5-Apr--2010	22.43
 * 9     6-Apr--2010	22.24
 * 10    7-Apr--2010	22.29	22.22		22.22
 * 11    8-Apr--2010	22.15	22.21		22.21
 * 12    9-Apr--2010	22.39	22.23		22.24
 * 13    12-Apr--2010	22.38	22.26		22.27
 * 14    13-Apr--2010	22.61	22.31		22.33
 * 15    14-Apr--2010	23.36	22.42		22.52
 * 16    15-Apr--2010	24.05	22.61		22.80
 * 17    16-Apr--2010	23.75	22.77		22.97
 * 18    19-Apr--2010	23.83	22.91		23.13
 * 19    20-Apr--2010	23.95	23.08		23.28          22.597
 * 20    21-Apr--2010	23.63	23.21		23.34          22.709
 * 21    22-Apr--2010	23.82	23.38		23.43          22.831
 * 22    23-Apr--2010	23.87	23.53		23.51
 * 23    26-Apr--2010	23.65	23.65		23.54
 * 24    27-Apr--2010	23.19	23.71		23.47
 * 25    28-Apr--2010	23.10	23.69		23.40
 * 26    29-Apr--2010	23.33	23.61		23.39
 * 27    30-Apr--2010	22.68	23.51		23.26
 * 28    3-May--2010	23.10	23.43		23.23
 * 29    4-May--2010	22.40	23.28		23.08
 * 30    5-May--2010	22.17	23.13		22.92
 * <p/>
 * Created by Jan Wicherink on 2-6-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class CryptoCoinHistoryTrendCalculatorTest {

    @Inject
    private CryptoCoinHistoryTrendCalculator cryptoCoinHistoryTrendCalculator;

    @Inject
    private CryptocoinTrendDao cryptocoinTrendDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((CryptocoinHistoryDao.class).getPackage())
                .addPackage((CryptocoinHistoryDaoImpl.class).getPackage())
                .addPackage((CryptocoinHistory.class).getPackage())
                .addPackage((LoggingLevel.class).getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(CryptoCoinHistoryTrendCalculator.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(DataPersister.class.getPackage())
                .addPackage(CryptoCoinHistoryBulkDataHandler.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_23.xml")
    public void testCalculation() throws ExecutionException, InterruptedException {

        // Arrange
        final Trading trading = new Trading();
        trading.setId(1);
        final TradePair tradePair = new TradePair();
        tradePair.setId(1);
        trading.setTradePair(tradePair);

        final Trend maTrend = new Trend(1, TrendType.MA, 10, null);
        final Trend emaShortTrend = new Trend(2, TrendType.EMA, 10, null);
        final Trend emaLongTrend = new Trend(3, TrendType.EMA, 20, null);
        final Trend smaTrend = new Trend(4, TrendType.SMA, 10, emaShortTrend);

        final Macd macd = new Macd(1, emaShortTrend, emaLongTrend);

        tradePair.setId(1);
        trading.setTradePair(tradePair);

        // Act
        cryptoCoinHistoryTrendCalculator.init(trading);
        cryptoCoinHistoryTrendCalculator.recalculate();

        // Assert
        assertNotNull(cryptocoinTrendDao.getTrendValue(10, maTrend, tradePair));
        assertNotNull(cryptocoinTrendDao.getTrendValue(10, emaShortTrend, tradePair));

        assertEquals(22.22, cryptocoinTrendDao.getTrendValue(10, maTrend, tradePair).getValue(), 0.005);
        assertEquals(22.22, cryptocoinTrendDao.getTrendValue(10, emaShortTrend, tradePair).getValue(), 0.005);
        assertEquals(11.11, cryptocoinTrendDao.getTrendValue(10, emaLongTrend, tradePair).getValue(), 0.005);
        // Macd = short trend value - long trend value = 22,22 - 11, 11 = 11, 11
        assertEquals(11.11, cryptocoinTrendDao.getMacdValue(10, macd, tradePair).getValue(), 0.005);

        assertEquals(22.42, cryptocoinTrendDao.getTrendValue(15, maTrend, tradePair).getValue(), 0.005);
        assertEquals(22.52, cryptocoinTrendDao.getTrendValue(15, emaShortTrend, tradePair).getValue(), 0.005);
        assertEquals(16.755, cryptocoinTrendDao.getTrendValue(15, emaLongTrend, tradePair).getValue(), 0.005);
        // Macd = short trend value - long trend value = 22,52 - 16,755 = 5,77
        assertEquals(5.76, cryptocoinTrendDao.getMacdValue(15, macd, tradePair).getValue(), 0.005);

        // SMA value
        assertEquals(22.597, cryptocoinTrendDao.getTrendValue(19, smaTrend, tradePair).getValue(), 0.005);
        assertEquals(22.709, cryptocoinTrendDao.getTrendValue(20, smaTrend, tradePair).getValue(), 0.005);
        assertEquals(22.707, cryptocoinTrendDao.getTrendValue(20, smaTrend, tradePair).getValue(), 0.005);

        assertEquals(23.38, cryptocoinTrendDao.getTrendValue(21, maTrend, tradePair).getValue(), 0.005);
        assertEquals(23.43, cryptocoinTrendDao.getTrendValue(21, emaShortTrend, tradePair).getValue(), 0.005);
        assertEquals(22.79, cryptocoinTrendDao.getTrendValue(21, emaLongTrend, tradePair).getValue(), 0.005);
        // Macd = short trend value - long trend value = 23,43 - 22,79 = 5,77
        assertEquals(0.63, cryptocoinTrendDao.getMacdValue(21, macd, tradePair).getValue(), 0.005);

        assertEquals(23.61, cryptocoinTrendDao.getTrendValue(26, maTrend, tradePair).getValue(), 0.005);
        assertEquals(23.39, cryptocoinTrendDao.getTrendValue(26, emaShortTrend, tradePair).getValue(), 0.005);
        assertEquals(23.03, cryptocoinTrendDao.getTrendValue(26, emaLongTrend, tradePair).getValue(), 0.005);
        // Macd = short trend value - long trend value = 23,39 - 23,03 = 0,36
        assertEquals(0.36, cryptocoinTrendDao.getMacdValue(26, macd, tradePair).getValue(), 0.005);

        assertEquals(23.13, cryptocoinTrendDao.getTrendValue(30, maTrend, tradePair).getValue(), 0.005);
        assertEquals(22.92, cryptocoinTrendDao.getTrendValue(30, emaShortTrend, tradePair).getValue(), 0.005);
        assertEquals(22.875, cryptocoinTrendDao.getTrendValue(30, emaLongTrend, tradePair).getValue(), 0.005);
        // Macd = short trend value - long trend value = 22,92 - 22,875 = 0,045
        assertEquals(0.04, cryptocoinTrendDao.getMacdValue(30, macd, tradePair).getValue(), 0.005);
    }
}