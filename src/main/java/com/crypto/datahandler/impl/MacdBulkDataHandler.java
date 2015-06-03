package com.crypto.datahandler.impl;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.MacdDao;
import com.crypto.dao.TrendDao;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.MacdDataProvider;
import com.crypto.entities.*;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.List;

/**
 * Macd bulk data handler
 *
 * Created by Jan Wicherink on 2-6-15.
 */
@Stateful
@LocalBean
public class MacdBulkDataHandler extends BulkDataHandler implements BulkDataProvider<CryptocoinHistory>, MacdDataProvider, DataPersister<MacdValue> {

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private MacdDao macdDao;

    @EJB
    private TrendDao trendDao;

    private Macd macd;

    private TradePair tradePair;

    /**
     * Default constructor
     */
    public MacdBulkDataHandler() {

    }

    /**
     * Get all Macd's
     * @return all of the macds
     */
    public List<Macd> getAllMacds() {
        return macdDao.getAll();
    }


    @Override
    public TrendValue getShortTrendValue(final Integer index) {

       return cryptocoinTrendDao.getTrendValue(index, macd.getShortTrend(), this.tradePair);
    }

    @Override
    public TrendValue getLongTrendValue(final Integer index) {
        return cryptocoinTrendDao.getTrendValue(index, macd.getLongTrend(), this.tradePair);
    }

    @Override
    public MacdValue getMacdValue(final Integer index) {
        return cryptocoinTrendDao.getMacdValue(index, this.macd, this.tradePair);
    }

    @Override
    public TradePair getTradePair() {

        return this.tradePair;
    }

    @Override
    public void setTradePair(final TradePair tradePair) {
        this.tradePair = tradePair;
    }

    @Override
    public void storeValue(final MacdValue value) {
        cryptocoinTrendDao.storeMacdValue(value);
    }

    public Macd getMacd() {
        return macd;
    }

    public void setMacd(Macd macd) {
        this.macd = macd;
    }
}
