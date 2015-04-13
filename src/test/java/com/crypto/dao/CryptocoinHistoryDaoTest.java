package com.crypto.dao;

import com.crypto.entities.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.DataSeedStrategy;
import org.jboss.arquillian.persistence.SeedDataUsing;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jan Wicherink on 13-4-2015.
 */
@RunWith(Arquillian.class)
public class CryptocoinHistoryDaoTest {

    @Inject
    CryptocoinHistoryDao cryptocoinHistoryDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((CryptocoinHistoryDao.class).getPackage())
                .addPackage((CryptocoinHistory.class).getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    @SeedDataUsing(DataSeedStrategy.REFRESH)
    public void testPersistCryptocoinHistory() {

        final Currency dollar = new Currency("DLR", "Dollar", "$");
        final CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        final TradingSite tradingSite = new TradingSite("BTCE", "BTC-e", "www.btc-e.com");

        final Integer id = new Integer(1);
        final TradePair tradePair = new TradePair(id, tradingSite, dollar, bitcoin, 0.1F);

        final CryptocoinHistory cryptocoinHistory1 = new CryptocoinHistory(tradePair,230F, 240F, 200F, 250F, 1000L);
        final CryptocoinHistory cryptocoinHistory2 = new CryptocoinHistory(tradePair,210F, 220F, 210F, 260F, 2000L);

        cryptocoinHistoryDao.persist(cryptocoinHistory1);
        cryptocoinHistoryDao.persist(cryptocoinHistory2);

        final Integer startIndex = cryptocoinHistoryDao.getStartIndex(tradePair);
        final Integer lastIndex = cryptocoinHistoryDao.getLastIndex(tradePair);

        assertNotNull(startIndex);
        assertEquals(new Integer(1), startIndex);
        assertEquals(new Integer(2), lastIndex);

        //final CryptocoinHistory persistedCryptocoinHistory;

    }


}
