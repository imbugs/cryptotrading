package com.crypto.dao;

import com.crypto.entities.Logging;
import com.crypto.entities.LoggingLevel;
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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


/**
 * Created by Jan Wicherink on 23-4-15.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class LoggingDaoTest {

    @Inject
    private LoggingDao loggingDao;

    @Inject
    private TradingDao tradingDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((LoggingDaoImpl.class).getPackage())
                .addPackage((Logging.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @UsingDataSet("datasets/it_test_dataset_9.xml")
    public void testPersist () {

        final Trading trading = tradingDao.get(1);

        assertEquals(new Integer(1),trading.getId());

        final Calendar calendar = Calendar.getInstance();
        final Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

        final Logging logging = new Logging(timestamp,trading, 1, LoggingLevel.INFO.toString(), "LOGGING1");

        loggingDao.persist(logging);

      //  final List<Logging> loggings = loggingDao.getAll(trading, LoggingLevel.INFO);

         final Logging loggingPersisted= loggingDao.get(1);

         assertEquals(new Integer(1), loggingPersisted.getIndex());

 //       assertEquals ("LOGGING1", loggings.get(0).getMesssage());
    }
}
