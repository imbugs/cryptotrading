package com.crypto.dao;

import com.crypto.entities.Trend;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.fail;

/**
 * Created by Jan Wicherink on 7-4-2015.
 */
@RunWith(Arquillian.class)
public class CurrencyDaoTest {

  //  @Inject
  //  private CurrencyDao currencyDao;

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class, "testDao.jar")
                .addClass(Trend.class);
               // .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
               // .addAsResource("resources/META-INF/persistence.xml");

    }

    @Test
    public void helloWorld() {
        fail("Hello World");
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
