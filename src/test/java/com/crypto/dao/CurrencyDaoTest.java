package com.crypto.dao;

import com.crypto.entities.Currency;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jan Wicherink on 7-4-2015.
 */
@RunWith(Arquillian.class)
public class CurrencyDaoTest {

    @Inject
    private CurrencyDao currencyDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((CurrencyDaoImpl.class).getPackage())
                .addPackage((Currency.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    public void testAddCurrency() {

        currencyDao.addCurrency("EUR", "Euro", "€");

        Currency currency = currencyDao.getCurrency("EUR");

        assertNotNull(currency);
        assertEquals("Euro", currency.getDescription());
        assertEquals("€", currency.getSymbol());
   }

    @Ignore
    public void testUpdateCurrency () {

        Currency currency = currencyDao.addCurrency("EUR", "Euro", "€");

        currency.setDescription("Dollars");
        currency.setSymbol("$");

        currency = currencyDao.updateCurrency(currency);

        assertNotNull(currency);
        assertEquals("Dollars", currency.getDescription());
        assertEquals("$", currency.getSymbol());
    }
}
