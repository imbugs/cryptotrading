package com.crypto.calculator.bulk;

import com.crypto.calculator.*;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.datahandler.impl.MacdBulkDataHandler;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradePair;
import com.crypto.entities.TrendValue;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.logging.Logger;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 *
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
     * Initialises the trend calculator.
     *
     * @param tradePair the tradePair of the cryptocoin data
     */
    public void init(final TradePair tradePair) {
        this.dataProvider.setTradePair(tradePair);
        this.macdDataProvider.setTradePair(tradePair);

        final Integer startIndex = dataProvider.getStartIndex();

        final MovingAverageCalculator maCalculator = new MovingAverageCalculator(this.dataProvider);
        final ExponentialMovingAverageCalculator emaCalculator = new ExponentialMovingAverageCalculator(this.dataProvider, startIndex);
        final SmoothingMovingAverageCalculator smaCalculator = new SmoothingMovingAverageCalculator(this.dataProvider);
        final MacdValueCalculator macdValueCalculator = new MacdValueCalculator(this.macdDataProvider);

        this.maCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(maCalculator, this.dataProvider, this.dataProvider, tradePair);
        this.emaCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(emaCalculator, this.dataProvider, this.dataProvider, tradePair);
        this.smaCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(smaCalculator, this.dataProvider, this.dataProvider, tradePair);
        this.macdCalculator = new BulkCalculator<CryptocoinHistory, MacdValue>(macdValueCalculator, this.macdDataProvider, this.macdDataProvider, tradePair);

        LOG.info("Initialise for tradepair : " + tradePair.getId());
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

    /**
     * Calculate all the trend values for all available trends: MA, EMA and SMA trends
     */
    public void recalculate() {

        // Truncate all data before recalculating
        this.dataProvider.truncateTrendValueData();

        calculateMovingAverageTrends();
        calculateExponentialMovingAverageTrends();
        calculateSmoothingMovingAverageTrends();
        calculateMacdTrends();
    }
}
