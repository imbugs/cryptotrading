package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.Currency;
import com.crypto.entities.Trading;
import com.crypto.entities.Withdrawal;
import com.crypto.entities.pkey.WithdrawalPk;
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
 * Test withdrawal Dao
 *
 * Created by Jan Wicherink on 7-5-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class WithdrawalDaoTest {

    @Inject
    private WithdrawalDao withdrawalDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test3.war")
                .addPackage((WithdrawalDao.class).getPackage())
                .addPackage((Withdrawal.class).getPackage())
                .addPackage(MacdDao.class.getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(CryptocoinHistoryDaoImpl.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(WithdrawalPk.class.getPackage())
                .addPackage(SignalBulkDataHandler.class.getPackage())
                .addPackage(DataPersister.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_20.xml")
    public void testGet() {

        final Trading trading = new Trading();
        trading.setId(1);

        final Currency currency = new Currency("DLR", "Dollar", "$");

        final Withdrawal withdrawal = withdrawalDao.get(trading, currency);

        assertEquals(new Float(100F), withdrawal.getCoins());
        assertEquals(new Integer(1), withdrawal.getTrading().getId());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_20.xml")
    public void testGetAll() {

        final List<Withdrawal> withdrawals = withdrawalDao.getAll();

        assertEquals(2, withdrawals.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_20.xml")
    public void testGetWithdrawalsOfTrading() {

        final Trading trading = new Trading();
        trading.setId(1);

        final List<Withdrawal> withdrawals = withdrawalDao.getWithdrawalsOfTrading(trading);

        assertEquals(1, withdrawals.size());
    }
}