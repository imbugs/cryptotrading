package com.crypto.calculator.bulk;

import com.crypto.calculator.*;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.datahandler.impl.MacdBulkDataHandler;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.SignalDataProvider;
import com.crypto.entities.*;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.logging.Logger;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 * <p/>
 * Created by Jan Wicherink on 8-5-15.
 */
@Stateful
public class CryptoCoinHistoryTrendCalculator {

    private static final Logger LOG = Logger.getLogger(CryptoCoinHistoryTrendCalculator.class.getName());

    @EJB
    private CryptoCoinHistoryBulkDataHandler dataProvider;

    @EJB
    private MacdBulkDataHandler macdDataProvider;

    @EJB
    private SignalBulkDataHandler signalDataProvider;

    private BulkCalculator<CryptocoinHistory, TrendValue> maCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> emaCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> smaCalculator;

    private BulkCalculator<CryptocoinHistory, MacdValue> macdCalculator;

    private BulkCalculator<CryptocoinHistory, Signal> signalCalculator;

    /**
     * Default constructor
     */
    public CryptoCoinHistoryTrendCalculator() {

    }

    /**
     * Initialises the trend calculator.
     *
     * @param trading the trading
     */
    public void init(final Trading trading) {
        this.dataProvider.setTradePair(trading.getTradePair());
        this.macdDataProvider.setTradePair(trading.getTradePair());

        final Integer startIndex = dataProvider.getStartIndex();

        final MovingAverageCalculator maCalculator = new MovingAverageCalculator(this.dataProvider);
        final ExponentialMovingAverageCalculator emaCalculator = new ExponentialMovingAverageCalculator(this.dataProvider, startIndex);
        final SmoothingMovingAverageCalculator smaCalculator = new SmoothingMovingAverageCalculator(this.dataProvider);
        final MacdValueCalculator macdValueCalculator = new MacdValueCalculator(this.macdDataProvider);
        final SignalCalculator signalCalculator = new SignalCalculator(this.signalDataProvider, trading);

        this.maCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(maCalculator, this.dataProvider, this.dataProvider, trading.getTradePair());
        this.emaCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(emaCalculator, this.dataProvider, this.dataProvider, trading.getTradePair());
        this.smaCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(smaCalculator, this.dataProvider, this.dataProvider, trading.getTradePair());
        this.macdCalculator = new BulkCalculator<CryptocoinHistory, MacdValue>(macdValueCalculator, this.macdDataProvider, this.macdDataProvider, trading.getTradePair());
        this.signalCalculator = new BulkCalculator<CryptocoinHistory, Signal>(signalCalculator, this.signalDataProvider, this.signalDataProvider, trading.getTradePair());

        LOG.info("Initialise for tradepair : " + trading.getTradePair().getId());
    }

    /**
     * Calculate all moving average trend data.
     */
    private void calculateMovingAverageTrends() {

        this.dataProvider.getAllMovingAverageTrends().stream().forEach((trend) -> {

            LOG.info("Calculator for MA trend : " + trend.getName());
            dataProvider.setTrend(trend);
            ((TrendCalculator) maCalculator.getCalculator()).setTrend(trend);
            maCalculator.calculate();
        });
    }

    /**
     * Calculate all exponential moving average trend data.
     */
    private void calculateExponentialMovingAverageTrends() {

        this.dataProvider.getAllExponentialMovingAverageTrends().stream().forEach((trend) -> {

            LOG.info("Calculator for EMA trend : " + trend.getName());

            dataProvider.setTrend(trend);
            ((TrendCalculator) emaCalculator.getCalculator()).setTrend(trend);
            emaCalculator.calculate();
        });
    }


    /**
     * Calculate all smoothing moving average trend data.
     */
    private void calculateSmoothingMovingAverageTrends() {

        this.dataProvider.getAllSmoothingMovingAverageTrends().stream().forEach((trend) -> {

            LOG.info("Calculator for SMA trend : " + trend.getName());

            dataProvider.setTrend(trend);
            ((TrendCalculator) smaCalculator.getCalculator()).setTrend(trend);
            smaCalculator.calculate();
        });
    }


    /**
     * Calculate all Macd trends.
     */
    private void calculateMacdTrends() {

        this.macdDataProvider.getAllMacds().stream().forEach((macd) -> {

            // Calculate for every availble macd the macd value
            LOG.info("Calculator for MACD  : " + macd.getId());

            this.macdDataProvider.setMacd(macd);
            ((MacdCalculator) macdCalculator.getCalculator()).setMacd(macd);
            macdCalculator.calculate();
        });
    }


    private void  calculateSignals () {

       signalCalculator.calculate();
   }


    /**
     * Calculate all the trend values for all available trends: MA, EMA and SMA trends
     */
    public void recalculate() {

        // Truncate all data before recalculating
        this.dataProvider.truncateTrendValueData();
/*
        calculateMovingAverageTrends();
        calculateExponentialMovingAverageTrends();
        calculateSmoothingMovingAverageTrends();
        calculateMacdTrends();
  */
        calculateSignals ();
    }
}
