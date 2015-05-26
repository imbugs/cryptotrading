package com.crypto.datahandler.impl;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TrendDao;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Bulk data provider of cryptocoin history data
 *
 * Created by Jan Wicherink on 9-5-15.
 */
@ApplicationScoped
public class CryptoCoinHistoryBulkDataHandler implements BulkDataProvider<CryptocoinHistory>, MovingAverageDataProvider, DataPersister<TrendValue> {

    @Inject
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @Inject
    private CryptocoinTrendDao cryptocoinTrendDao;

    @Inject
    private TrendDao trendDao;

    private Trend trend;

    private TradePair tradePair;

    /**
     * Default constructor
     */
    public CryptoCoinHistoryBulkDataHandler() {
    }

    /**
     * Constructor
     * @param tradePair tradepair of the bulk data handler
     * @param trend  the trend for the moving average data provider
     */
    public CryptoCoinHistoryBulkDataHandler(final TradePair tradePair, final Trend trend){

        this.tradePair = tradePair;
        this.trend = trend;
    }

    @Override
    public List<CryptocoinHistory> getAll() {
        return  cryptocoinHistoryDao.getAll(this.tradePair);
    }

    @Override
    public CryptocoinHistory getValue(Integer index) {
        return cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(this.tradePair, index);
    }

    @Override
    public void storeValue(TrendValue value) {
        cryptocoinTrendDao.storeTrendValue(value);
    }


    @Override
    public Float getSumOverPeriod(final Integer index, final Integer period) {
        return cryptocoinHistoryDao.getSumCryptoCoinRate(index, period, this.tradePair);
    }

    @Override
    public Trend getTrend() {
        return this.trend;
    }

    @Override
    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    @Override
    public TrendValue getTrendValue(final Integer index) {
        return cryptocoinTrendDao.getTrendValue(index, this.trend, this.tradePair);
    }
    @Override
    public CryptocoinHistory getLast() {
        return cryptocoinHistoryDao.getLast(this.tradePair);
    }

    @Override
    public List<Trend> getAllMovingAverageTrends() {
        return trendDao.getAllMovingAverageTrends();
    }

    @Override
    public List<Trend> getAllExponentialMovingAverageTrends() {
        return trendDao.getAllExponentialMovingAverageTrends();
    }

    @Override
    public List<Trend> getAllSmoothingMovingAverageTrends() {
        return trendDao.getAllSmoothingMovingAverageTrends();
    }

    @Override
    public TradePair getTradePair() {
        return this.tradePair;
    }

    @Override
    public void setTradePair(TradePair tradePair) {
        this.tradePair = tradePair;
    }
}
