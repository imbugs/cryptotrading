package com.crypto.dao;

import com.crypto.entities.Currency;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by Jan Wicherink on 7-4-2015.
 */
@RunWith(Arquillian.class)
public class CurrencyDaoTest {

    @Inject
    private CurrencyDao currencyDao;

    @Deployment
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                        .addPackage((CurrencyDaoImpl.class).getPackage())
                        .addPackage((Currency.class).getPackage())
        // .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
         .addAsResource("/src/test/resources/META-INF/test-persistence.xml");

    }

    @Test
    public void helloWorld() {
        return;
    }

/*
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
*/
}
