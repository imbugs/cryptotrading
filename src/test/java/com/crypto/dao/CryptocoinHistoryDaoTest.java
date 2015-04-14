package com.crypto.dao;

import com.crypto.entities.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.*;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jan Wicherink on 13-4-2015.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase=TestExecutionPhase.NONE)
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
                .addPackage((CryptocoinHistory.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @CleanupUsingScript("sql/cleanup.sql")
    @UsingDataSet("datasets/it_test_dataset_1.xml")
    @Transactional(TransactionMode.ROLLBACK)
    public void testPersistCryptocoinHistory() {

        final Currency dollar = currencyDao.get("DLR");
        assertNotNull(dollar);

        final CryptoCurrency bitcoin = (CryptoCurrency) currencyDao.get("BTC");
        assertNotNull(bitcoin);

        final TradingSite tradingSite = tradingSiteDao.get("BTCE");
        assertNotNull(tradingSite);

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        final CryptocoinHistory cryptocoinHistory = new CryptocoinHistory(tradePair,230F, 240F, 200F, 250F, 1000L);

        cryptocoinHistoryDao.persist(cryptocoinHistory);

        final CryptocoinHistory persistedCriptocoinHistory = cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(tradePair, 1);

        assertNotNull(persistedCriptocoinHistory);
        assertEquals(tradePair.getId(), persistedCriptocoinHistory.getTradePair().getId());
        assertEquals(230F , persistedCriptocoinHistory.getOpen(), 0.1F);
        assertEquals(240F , persistedCriptocoinHistory.getLow(), 0.1F);
        assertEquals(200F , persistedCriptocoinHistory.getHigh(), 0.1F);
        assertEquals(250F , persistedCriptocoinHistory.getClose(), 0.1F);
        assertEquals(1000L , persistedCriptocoinHistory.getVolume(), 0.1F);
    }

    @Test
    @CleanupUsingScript("sql/cleanup.sql")
    @UsingDataSet("datasets/it_test_dataset_2.xml")
    @Transactional(TransactionMode.ROLLBACK)
     public void testGetAll() {

        final TradePair tradePair = tradePairDao.get(1);
        assertNotNull(tradePair);

        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getAll(tradePair);

        assertNotNull(cryptocoinHistories);
        assertEquals(2, cryptocoinHistories.size());

        assertEquals(new Integer(3), cryptocoinHistories.get(0).getIndx());
        assertEquals(new Integer(4), cryptocoinHistories.get(1).getIndx());
    }





    }
