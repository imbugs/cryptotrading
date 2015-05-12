package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.Trading;
import com.crypto.entities.Wallet;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


/**
 * Created by Jan Wicherink on 15-4-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase= TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class WalletDaoTest {

    @Inject
    private WalletDao walletDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test3.war")
                .addPackage((WalletDao.class).getPackage())
                .addPackage((Wallet.class).getPackage())
                .addPackage(MacdDao.class.getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(CryptocoinHistoryDaoImpl.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addPackage(WithdrawalPk.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_4.xml")
    public void testGet () {

        final Trading trading = new Trading ();
        trading.setId(1);

        final Wallet wallet = walletDao.get(trading);

        assertNotNull(wallet);

        assertEquals(100F, wallet.getCoins());
    }
}
