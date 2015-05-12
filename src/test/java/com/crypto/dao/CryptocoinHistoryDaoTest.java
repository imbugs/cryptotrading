package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.*;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jan Wicherink on 13-4-2015.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase=TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class CryptocoinHistoryDaoTest {

    @Inject
    CryptocoinHistoryDao cryptocoinHistoryDao;

    @Inject
    CurrencyDao currencyDao;

    @Inject
    TradingSiteDao tradingSiteDao;

    @Inject
    TradePairDao tradePairDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((CryptocoinHistoryDao.class).getPackage())
                .addPackage((CryptocoinHistoryDaoImpl.class).getPackage())
                .addPackage((CryptocoinHistory.class).getPackage())
                .addPackage((LoggingLevel.class).getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_1.xml")
    public void testPersistCryptocoinHistory() throws ParseException {

        final Currency dollar = currencyDao.get("DLR");
        assertNotNull(dollar);

        final CryptoCurrency bitcoin = (CryptoCurrency) currencyDao.get("BTC");
        assertNotNull(bitcoin);

        final TradingSite tradingSite = tradingSiteDao.get("BTCE");
        assertNotNull(tradingSite);

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        final Calendar calendar = Calendar.getInstance();
        final Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

        final CryptocoinHistory cryptocoinHistory = new CryptocoinHistory(100, timestamp, tradePair, 230F, 240F, 200F, 250F, 1000L);

        cryptocoinHistoryDao.persist(cryptocoinHistory);

        final CryptocoinHistory persistedCriptocoinHistory = cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(tradePair, 100);

        assertNotNull(persistedCriptocoinHistory);
        assertEquals(tradePair.getId(), persistedCriptocoinHistory.getTradePair().getId());
        assertEquals(230F, persistedCriptocoinHistory.getOpen(), 0.1F);
        assertEquals(240F, persistedCriptocoinHistory.getLow(), 0.1F);
        assertEquals(200F, persistedCriptocoinHistory.getHigh(), 0.1F);
        assertEquals(250F, persistedCriptocoinHistory.getClose(), 0.1F);
        assertEquals(1000L, persistedCriptocoinHistory.getVolume(), 0.1F);
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetAll() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getAll(tradePair);

        assertNotNull(cryptocoinHistories);
        assertEquals(2, cryptocoinHistories.size());

        assertEquals(new Integer(3), cryptocoinHistories.get(0).getIndex());
        assertEquals(new Integer(4), cryptocoinHistories.get(1).getIndex());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetStartAndLastIndex() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        assertEquals(new Integer(3), cryptocoinHistoryDao.getStartIndex(tradePair));
        assertEquals(new Integer(4), cryptocoinHistoryDao.getLastIndex(tradePair));
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetLast() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        assertEquals(new Integer(4), cryptocoinHistoryDao.getLast(tradePair).getIndex());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetCryptoCoinHistoryByTimestamp() throws ParseException {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
        Date date = dateFormat.parse("2014-04-14 12:01:00");
        Long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);

        assertEquals(new Integer(4), cryptocoinHistoryDao.getCryptoCoinHistoryByTimestamp(tradePair, timestamp).getIndex());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetCryptoCoinHistorySinceDate() throws ParseException {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE);
        Date date = dateFormat.parse("2014-04-14");

        List<CryptocoinHistory> retrievedCryptocoinHistories = cryptocoinHistoryDao.getCryptoCoinHistorySinceDate(tradePair, date);
        assertEquals(2, retrievedCryptocoinHistories.size());

        assertEquals(new Integer(3), cryptocoinHistoryDao.getStartIndex(tradePair));
        assertEquals(new Integer(4), cryptocoinHistoryDao.getLastIndex(tradePair));
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetCryptoCoinHistorySinceIndex() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        List<CryptocoinHistory> retrievedCryptocoinHistories = cryptocoinHistoryDao.getCryptoCoinHistorySinceIndex(tradePair, 4);
        assertEquals(1, retrievedCryptocoinHistories.size());

        assertEquals(new Integer(4), retrievedCryptocoinHistories.get(0).getIndex());
    }


    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetCryptoCoinHistoryRangeIndex() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        List<CryptocoinHistory> retrievedCryptocoinHistories = cryptocoinHistoryDao.getCryptoCoinHistoryRangeIndex(tradePair, 3, 4);
        assertEquals(2, retrievedCryptocoinHistories.size());

        assertEquals(new Integer(3), retrievedCryptocoinHistories.get(0).getIndex());
        assertEquals(new Integer(4), retrievedCryptocoinHistories.get(1).getIndex());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetEarliestDate() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        Timestamp timestamp = cryptocoinHistoryDao.getEarliestDate(tradePair);

        assertNotNull(timestamp);

        assertEquals("2014-04-14 12:00:00.0", timestamp.toString());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetLatestDate() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        Timestamp timestamp = cryptocoinHistoryDao.getLatestDate(tradePair);

        assertNotNull(timestamp);

        assertEquals("2014-04-14 12:01:00.0", timestamp.toString());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testGetSumCryptoCoinRate() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        Float total = cryptocoinHistoryDao.getSumCryptoCoinRate(4, 2, tradePair);

        assertEquals(610F, total, 0.001F);
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    public void testDeleleteBeforeDate() throws ParseException {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE);
        final Date date = dateFormat.parse("2014-04-15");

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        cryptocoinHistoryDao.deleteBeforeDate(date);
        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getAll(tradePair);

        assertNotNull(cryptocoinHistories);
        assertTrue(cryptocoinHistories.isEmpty());
    }
}