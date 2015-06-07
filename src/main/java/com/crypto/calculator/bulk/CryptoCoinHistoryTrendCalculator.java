package com.crypto.calculator.bulk;

import com.crypto.calculator.*;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.datahandler.impl.MacdBulkDataHandler;
import com.crypto.entities.*;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.logging.Logger;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 * Created by Jan Wicherink on 8-5-15.
 */
@Stateful
public class CryptoCoinHistoryTrendCalculator {

    private static final Logger LOG = Logger.getLogger(CryptoCoinHistoryTrendCalculator.class.getName());

    @EJB
    private CryptoCoinHistoryBulkDataHandler dataProvider;

    @EJB
    private MacdBulkDataHandler macdDataProvider;

    private BulkCalculator<CryptocoinHistory, TrendValue> maCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> emaCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> smaCalculator;

    private BulkCalculator<CryptocoinHistory, MacdValue> macdCalculator;

    /**
     * Default constructor
     */
    public CryptoCoinHistoryTrendCalculator() {

    }

    /**
     * Initialises the trend calculator
     *
     * @param tradePair the tradePair of the cryptocoin data
     */
    public void init(final TradePair tradePair) {
        this.dataProvider.setTradePair(tradePair);
        this.macdDataProvider.setTradePair(tradePair);

        final Integer startIndex = dataProvider.getStartIndex();

        final MovingAverageCalculator maCalculator = new MovingAverageCalculator(this.dataProvider);
        final ExponentialMovingAverageCalculator emaCalculator = new ExponentialMovingAverageCalculator(this.dataProvider, startIndex);
        final MacdValueCalculator macdValueCalculator = new MacdValueCalculator(this.macdDataProvider);

        this.maCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(maCalculator, this.dataProvider, this.dataProvider, tradePair);
        this.emaCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(emaCalculator, this.dataProvider, this.dataProvider, tradePair);
        this.macdCalculator = new BulkCalculator<CryptocoinHistory, MacdValue>(macdValueCalculator, this.macdDataProvider, this.macdDataProvider, tradePair);

        LOG.info("Initialise for tradepair : " + tradePair.getId());
    }

    /**
     * Calculate all moving average trend data
     */
    private void calculateMovingAverageTrends() {
        final List<Trend> trends = this.dataProvider.getAllMovingAverageTrends();

        // Calculate for every availble trend the trend value
        for (final Trend trend : trends) {

            LOG.info("Calculator for MA trend : " + trend.getName());

            dataProvider.setTrend(trend);
            ((TrendCalculator) maCalculator.getCalculator()).setTrend(trend);
            maCalculator.calculate();
        }
    }

    /**
     * Calculate all exponential moving average trend data
     */
    private void calculateExponentialMovingAverageTrends() {
        final List<Trend> trends = this.dataProvider.getAllExponentialMovingAverageTrends();

        // Calculate for every availble trend the trend value
        for (final Trend trend : trends) {

            LOG.info("Calculator for EMA trend : " + trend.getName());

            dataProvider.setTrend(trend);
            ((TrendCalculator) emaCalculator.getCalculator()).setTrend(trend);
            emaCalculator.calculate();
        }
    }

    private void calculateMacdTrends() {
        final List<Macd> macds = this.macdDataProvider.getAllMacds();

        // Calculate for every availble macd the macd value
        for (final Macd macd : macds) {

            LOG.info("Calculator for MACD  : " + macd.getId());

            this.macdDataProvider.setMacd(macd);
            ((MacdCalculator) macdCalculator.getCalculator()).setMacd(macd);
            macdCalculator.calculate();
        }
    }


    /**
     * Calculate all the trend values for all available trends: MA, EMA and SMA trends
     */
    public void recalculate() {

        // Truncate all data before recalculating
        this.dataProvider.truncateTrendValueData();

        calculateMovingAverageTrends();
        calculateExponentialMovingAverageTrends();
        calculateMacdTrends();
    }
}
