package com.crypto.calculator.bulk;

import com.crypto.calculator.ExponentialMovingAverageCalculator;
import com.crypto.calculator.MacdValueCalculator;
import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;
import com.crypto.enums.TrendType;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 *
 * Created by Jan Wicherink on 8-5-15.
 */
@Stateful
public class CryptoCoinHistoryTrendCalculator {

    private static final Logger LOG = Logger.getLogger(CryptoCoinHistoryTrendCalculator.class.getName());

    @Inject
    private CryptoCoinHistoryBulkDataHandler dataProvider;

    private BulkCalculator<CryptocoinHistory, TrendValue> maCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> emaCalculator;

    private TradePair tradePair;

    /**
     * Default constructor
     */
    public CryptoCoinHistoryTrendCalculator () {

    }

    /**
     * Initialises the trend calculator
     *
     * @param tradePair the tradePair of the cryptocoin data
     */
    public void init (final TradePair tradePair) {

        final MovingAverageCalculator maCalculator = new MovingAverageCalculator(this.dataProvider);
        final ExponentialMovingAverageCalculator emaCalculator = new ExponentialMovingAverageCalculator(this.dataProvider);

        this.maCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(maCalculator, dataProvider, dataProvider, tradePair);
        this.emaCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(emaCalculator, dataProvider, dataProvider, tradePair);
        this.tradePair = tradePair;

        LOG.info("Initialise for tradepair : " + tradePair.getId());
    }


    /**
     * Calculate all the trend values for all available trends
     */
    public void calculate() {

        final List<Trend> trends = this.dataProvider.getAllMovingAverageTrends();

        // Calculate for every availble trend the trend value
        for (final Trend trend : trends) {

            LOG.info("Calculator for trend : " + trend.getName());

            maCalculator.getCalculator().setTrend(trend);
            maCalculator.calculate();
        }
    }
}
