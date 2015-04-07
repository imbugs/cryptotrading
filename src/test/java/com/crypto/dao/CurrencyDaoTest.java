package com.crypto.dao;

import com.crypto.dao.CurrencyDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import javax.inject.Inject;

/**
 * Created by Jan Wicherink on 7-4-2015.
 */
@RunWith(Arquillian.class)
public class CurrencyDaoTest {

    @Inject
    private CurrencyDao currencyDao;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CurrencyDao.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() {

        assertNotNull(currencyDao);
        currencyDao.addCurrency("EUR", "Euro", "€");
    }

    @Test
    public void testGetCurrency() {

        assertEquals("EUR", currencyDao.getCurrency("EUR").getCode());
        assertEquals("Euro",currencyDao.getCurrency("EUR").getDescription());
        assertEquals("€", currencyDao.getCurrency("EUR").getSymbol());
    }
}
