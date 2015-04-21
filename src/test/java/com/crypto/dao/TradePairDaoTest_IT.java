package com.crypto.dao;

import com.crypto.entities.CryptoCurrency;
import com.crypto.entities.Currency;
import com.crypto.entities.TradePair;
import com.crypto.entities.TradingSite;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

/**
 * Trade pair Dao test
 * Created by Jan Wicherink on 13-4-2015.
 */
@RunWith(Arquillian.class)
public class TradePairDaoTest_IT {

    @Inject
    private TradePairDao tradePairDao;

    @Inject
    private CurrencyDao currencyDao;

    @Inject
    private TradingSiteDao tradingSiteDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((TradePairDaoImpl.class).getPackage())
                .addPackage((TradePair.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    public void testAddTradePair() {

        final Currency euro = new Currency("EUR", "Euro", "€");
        final CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        final TradingSite tradingSite = new TradingSite("KRK", "Kraken", "www.kraken.com");

        Integer id = new Integer(1);

        final TradePair tradePair = new TradePair(id, tradingSite, euro, bitcoin, 0.1F);

        tradePairDao.persist(tradePair);

        TradePair persistedTradePair = tradePairDao.get(id);

        assertNotNull(persistedTradePair);

        assertEquals("KRK", persistedTradePair.getTradingSite().getCode());
        assertEquals("BTC", persistedTradePair.getCryptoCurrency().getCode());
        assertEquals("EUR", persistedTradePair.getCurrency().getCode());
    }


    @Test
    public void testGetTradePairOfTradingSite() {

        final Currency dollar = new Currency("DLR", "Dollar", "$");
        final CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin", "LTC");
        final TradingSite tradingSite = new TradingSite("BTCE", "BTC-e", "www.btc-e.com");

        final Integer id = new Integer(2);
        final TradePair tradePair = new TradePair(id, tradingSite, dollar, litecoin, 0.1F);

        tradePairDao.persist(tradePair);

        TradePair persistedTradePair = tradePairDao.getTradePairOfTradingSite(tradingSite);

        assertNotNull(persistedTradePair);

        assertEquals("BTCE", persistedTradePair.getTradingSite().getCode());
        assertEquals("LTC", persistedTradePair.getCryptoCurrency().getCode());
        assertEquals("DLR", persistedTradePair.getCurrency().getCode());
    }
}
