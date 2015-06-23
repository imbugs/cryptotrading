package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.dao.impl.CurrencyDaoImpl;
import com.crypto.dao.impl.MacdDaoImpl;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.Currency;
import com.crypto.entities.pkey.WithdrawalPk;
import com.crypto.enums.LoggingLevel;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test Currency Dao with Arquillian
 *
 * Created by Jan Wicherink on 7-4-2015.
 */
@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.ROLLBACK)
@Cleanup(phase = TestExecutionPhase.NONE)
@CleanupUsingScript("sql/cleanup.sql")
public class CurrencyDaoTest {

    @Inject
    private CurrencyDao currencyDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((CurrencyDaoImpl.class).getPackage())
                .addPackage((Currency.class).getPackage())
                .addPackage((MacdDao.class).getPackage())
                .addPackage(MacdDaoImpl.class.getPackage())
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
    public void testAddCurrency() {

        final Currency currency = new Currency("EUR", "Euro", "€");
        currencyDao.persist(currency);

        final Currency retrievedCurrency = currencyDao.get("EUR");

        assertNotNull(currency);
        assertEquals("Euro", retrievedCurrency.getDescription());
        assertEquals("€", retrievedCurrency.getSymbol());
   }

    @Test
    public void testUpdateCurrency () {

        final Currency currency = new Currency("DLR", "Euro", "€");

        currencyDao.persist(currency);

        Currency updatedCurrency = new Currency("DLR", "Dollars", "$");

        updatedCurrency = currencyDao.update(updatedCurrency);

        assertNotNull(updatedCurrency);
        assertEquals("Dollars", updatedCurrency.getDescription());
        assertEquals("$", updatedCurrency.getSymbol());
    }
}
