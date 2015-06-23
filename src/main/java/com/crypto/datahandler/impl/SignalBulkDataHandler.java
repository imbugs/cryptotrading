package com.crypto.datahandler.impl;

import com.crypto.dao.SignalDao;
import com.crypto.dao.TradeConditionDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.SignalDataProvider;
import com.crypto.entities.*;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.List;

/**
 * Bulk data provider of data for signal calculations
 * <p/>
 * Created by Jan Wicherink on 9-5-15.
 */
@Stateful
@LocalBean
public class SignalBulkDataHandler extends BulkDataHandler implements BulkDataProvider<CryptocoinHistory>, SignalDataProvider, DataPersister<Signal> {

    private static final Logger LOG = Logger.getLogger(SignalBulkDataHandler.class.getName());

    @EJB
    private SignalDao signalDao;

    @EJB
    private TradeConditionDao tradeConditionDao;

    @EJB
    private TradeConditionLogDao tradeConditionLogDao;

    private Trend trend;

    private Macd macd;


    /**
     * Default constructor
     */
    public SignalBulkDataHandler() {
    }

    @Override
    public CryptocoinHistory getValue(Integer index) {

        if (getTrading() == null) {
            throw new RuntimeException("Trading undefined");
        }

        return this.getCryptocoinHistoryDao().getCryptoCoinHistoryByIndex(getTradePair(), index);
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
    public List<TradeCondition> getAllTradeConditions(final TradeRule tradeRule) {

        return this.tradeConditionDao.getAllActiveTradeConditionsOfTradeRule(tradeRule);
    }

    @Override
    public Signal getLastSignal(Trading trading) {
        return signalDao.getLast(trading);
    }

    @Override
    public void saveLog(TradeConditionLog conditionLog) {
        tradeConditionLogDao.persist(conditionLog);
    }

    @Override
    public TrendValue getTrendValue(final Integer index) {

        if (getTradePair() == null) {
            throw new RuntimeException("TradePair undefined");
        }

        TrendValue trendValue;

        try {
            trendValue = this.getCryptocoinTrendDao().getTrendValue(index, this.trend, getTradePair());
        } catch (Exception e) {

            LOG.warn(e.getCause().getMessage());
            return null;
        }

        return trendValue;
    }

    @Override
    public MacdValue getMacdValue(final Integer index) {

        if (getTradePair() == null) {
            throw new RuntimeException("TradePair undefined");
        }

        MacdValue macdValue;

        try {
            macdValue = this.getCryptocoinTrendDao().getMacdValue(index, this.macd, getTradePair());
        } catch (Exception e) {

            LOG.warn(e.getCause().getMessage());
            return null;
        }

        return macdValue;
    }

    @Override
    public Macd getMacd() {
        return this.macd;
    }

    @Override
    public void setMacd(Macd macd) {
        this.macd = macd;
    }

    /**
     * Truncate all signal data
     */
    public void truncateSignalData() {
        signalDao.truncateSignalData(getTrading());

    }

    /**
     * Store trend value
     *
     * @param value the value to be stored
     */
    @Override
    public void storeValue(final Signal value) {

        if (value != null) {
            this.signalDao.persist(value);
        }
    }
}
