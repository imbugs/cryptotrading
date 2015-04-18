package com.crypto.dao;

import com.crypto.entities.Fund;
import com.crypto.entities.FundHistory;
import com.crypto.entities.Trading;
import junit.framework.Assert;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Jan Wicherink on 18-4-15.
 */


@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class FundHistoryDaoTest {

    @Inject
    private FundHistoryDao fundHistoryDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((FundDaoImpl.class).getPackage())
                .addPackage((Fund.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_6.xml")
    public void testGetAll () {

     final Trading trading = new Trading();
     trading.setId(new Integer(1));

     final List<FundHistory> fundHistories = fundHistoryDao.getAll(trading);

     assertNotNull (fundHistories);
     assertEquals(3, fundHistories.size());

     assertEquals(101, fundHistories.get(0).getCoins(),0.1F);
     assertEquals(102, fundHistories.get(1).getCoins(),0.1F);
     assertEquals(103, fundHistories.get(2).getCoins(),0.1F);
    }
}
