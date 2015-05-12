package com.crypto.dao;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dao.impl.CryptocoinHistoryDaoImpl;
import com.crypto.dao.impl.TradingSiteDaoImpl;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.TradingSite;
import com.crypto.entities.pkey.WithdrawalPk;
import com.crypto.enums.LoggingLevel;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by nl04990 on 13-4-2015.
 */
@RunWith(Arquillian.class)
public class TradingSiteDaoTest {

    @Inject
    private TradingSiteDao tradingSiteDao;

    @Deployment
    public static Archive<?> createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage((TradingSiteDaoImpl.class).getPackage())
                .addPackage((TradingSite.class).getPackage())
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
    public void testAddTradingSite() {

        final TradingSite tradingSite = new TradingSite("KRK", "Kraken", "www.kraken.com");

        tradingSiteDao.persist(tradingSite);

        TradingSite retrievedTradingSite = tradingSiteDao.get("KRK");

        assertEquals("Kraken", retrievedTradingSite.getDescription());
        assertEquals("www.kraken.com", retrievedTradingSite.getUrl());
    }

}
