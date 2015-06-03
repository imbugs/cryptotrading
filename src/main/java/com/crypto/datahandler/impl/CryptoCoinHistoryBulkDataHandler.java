package com.crypto.datahandler.impl;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TrendDao;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;
import org.apache.log4j.Logger;

import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Bulk data provider of crypto coin history data
 *
 * Created by Jan Wicherink on 9-5-15.
 */
@Stateful
@LocalBean
public class CryptoCoinHistoryBulkDataHandler extends BulkDataHandler implements  BulkDataProvider<CryptocoinHistory>, MovingAverageDataProvider, DataPersister<TrendValue> {

    private static final Logger LOG = Logger.getLogger(CryptoCoinHistoryBulkDataHandler.class.getName());

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TrendDao trendDao;

    private Trend trend;

    /**
     * Default constructor
     */
    public CryptoCoinHistoryBulkDataHandler() {
    }

    @Override
    public CryptocoinHistory getValue(Integer index) {
        return cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(getTradePair(), index);
    }

    @Override
    public Float getSumOverPeriod(final Integer index, final Integer period) {
        return cryptocoinHistoryDao.getSumCryptoCoinRate(index, period, getTradePair());
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

        TrendValue trendValue = null;

        try {
            trendValue = cryptocoinTrendDao.getTrendValue(index, this.trend, getTradePair());
        }
        catch (Exception e) {

            LOG.warn (e.getCause().getMessage());
            return null;
        }

        return trendValue;
    }

    public List<Trend> getAllMovingAverageTrends() {
        return trendDao.getAllMovingAverageTrends();
    }

    public List<Trend> getAllExponentialMovingAverageTrends() {
        return trendDao.getAllExponentialMovingAverageTrends();
    }

    public List<Trend> getAllSmoothingMovingAverageTrends() {
        return trendDao.getAllSmoothingMovingAverageTrends();
    }


    /**
     * Truncate all crypto coin trend data
     */
    public void truncateTrendValueData() {
        cryptocoinTrendDao.truncateTable();
    }

    /**
     * Store trend value
     * @param value the value to be stored
     */
    @Override
    public void storeValue(TrendValue value) {

        if (value != null) {
            cryptocoinTrendDao.storeTrendValue(value);
        }
    }

}
