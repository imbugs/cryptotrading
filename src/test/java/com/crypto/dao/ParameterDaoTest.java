package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.Parameter;
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

/**
 * Test the parameter Dao
 *
 * Created by Jan Wicherink on 28-4-2015.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class ParameterDaoTest {

    @Inject
    private ParameterDao parameterDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((ParameterDao.class).getPackage())
                .addPackage((Parameter.class).getPackage())
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
    @UsingDataSet("datasets/it_test_dataset_8.xml")
    public void testGetParameterStringValue() {

        final Parameter parameter = parameterDao.get("Parameter1");

        assertEquals("Text1", parameter.getStringValue());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_8.xml")
    public void testGetParameterFloatValue() {

        final Parameter parameter = parameterDao.get("Parameter2");

        assertEquals(2F, parameter.getFloatValue());
    }

}
