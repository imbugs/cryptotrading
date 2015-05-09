package com.crypto.dataprovider.impl;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dataprovider.BulkDataProvider;
import com.crypto.dataprovider.MovingAverageDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

import javax.ejb.EJB;
import java.util.List;

/**
 * Bulk data provider of cryptocoin history data
 *
 * Created by Jan Wicherink on 9-5-15.
 */
public class CryptoCoinHistoryBulkDataProvider implements BulkDataProvider<CryptocoinHistory>, MovingAverageDataProvider {

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    /**
     * Default constructor
     */
    public CryptoCoinHistoryBulkDataProvider() {
    }

    @Override
    public List<CryptocoinHistory> getAll(TradePair tradePair) {
        return  cryptocoinHistoryDao.getAll(tradePair);
    }

    @Override
    public CryptocoinHistory getValue(Integer index) {
        return cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(getTradePair(), index);
    }

    @Override
    public Integer getIndex() {
        return getIndex();
    }

    @Override
    public TradePair getTradePair() {
        return getTradePair();
    }

    @Override
    public Float getSumOverPeriod(Integer index) {
        return cryptocoinHistoryDao.getSumCryptoCoinRate(getIndex(), getTrend().getPeriod(), getTradePair());
    }

    @Override
    public TrendValue getTrendValue(Integer index) {
        return cryptocoinTrendDao.getTrendValue(getIndex(), getTrend(), getTradePair());
    }

    @Override
    public Trend getTrend() {
        return getTrend();
    }

    @Override
    public CryptocoinHistory getLast() {
        return cryptocoinHistoryDao.getLast(getTradePair());
    }
}
