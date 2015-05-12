package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.WalletHistoryDaoImpl;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.Trading;
import com.crypto.entities.WalletHistory;
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
 * Wallet history dao test
 * <p/>
 * Created by Jan Wicherink on 7-5-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class WalletHistoryDaoTest {

    @Inject
    private WalletHistoryDao walletHistoryDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test3.war")
                .addPackage((WalletHistoryDao.class).getPackage())
                .addPackage((WalletHistoryDaoImpl.class).getPackage())
                .addPackage((WalletHistory.class).getPackage())
                .addPackage((LoggingLevel.class).getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_21.xml")
    public void testRetrieveAll() {

        final List<WalletHistory> walletHistories = walletHistoryDao.retrieveAll();

        assertEquals(2, walletHistories.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_21.xml")
    public void testGetLast() {

        final Trading trading = new Trading();
        trading.setId(1);

        final WalletHistory walletHistory = walletHistoryDao.getLast(trading);

        assertEquals(new Float(101F), walletHistory.getCoins());
    }


}