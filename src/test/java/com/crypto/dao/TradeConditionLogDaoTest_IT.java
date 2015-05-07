package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradeConditionLog;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Trade condition log Dao test
 *
 * Created by Jan Wicherink on 7-5-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class TradeConditionLogDaoTest_IT {

    @Inject
    private TradeConditionLogDao tradeConditionLogDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(TradeConditionLog.class.getPackage())
                .addPackage(TradeConditionLogDao.class.getPackage())
                .addPackage(LoggingLevel.class.getPackage())
                .addPackage(CrytptocoinHistoryPk.class.getPackage())
                .addPackage(MovingAverageCalculator.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_19.xml")
    public void testGetAll() {

        final List<TradeConditionLog> tradeConditionLogs = tradeConditionLogDao.getAll();

        assertEquals(2, tradeConditionLogs.size());
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_19.xml")
    public void testDeleteBeforeDate() throws ParseException {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
        final Date beforeDate = dateFormat.parse("2015-05-07 12:30:00");

        tradeConditionLogDao.deleteBeforeDate(beforeDate);

        final List<TradeConditionLog> tradeConditionLogs = tradeConditionLogDao.getAll();

        assertEquals(1, tradeConditionLogs.size());
    }

}
