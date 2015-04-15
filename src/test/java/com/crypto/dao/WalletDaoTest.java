package com.crypto.dao;

import com.crypto.entities.Trend;
import com.crypto.entities.Wallet;
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
                .addPackage((WalletDaoImpl.class).getPackage())
                .addPackage((Wallet.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
 //   @UsingDataSet("datasets/it_test_dataset_4.xml")
    public void testGet () {



    }

}
