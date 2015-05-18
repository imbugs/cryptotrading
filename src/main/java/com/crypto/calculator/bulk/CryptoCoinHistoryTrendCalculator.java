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

import java.util.List;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 * <p/>
 * Created by Jan Wicherink on 8-5-15.
 */
public class CryptoCoinHistoryTrendCalculator {

    private CryptoCoinHistoryBulkDataHandler dataProvider;

    private BulkCalculator<CryptocoinHistory, TrendValue> maCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> emaCalculator;

    private TradePair tradePair;

    /**
     * Constructor
     *
     * @param tradePair the tradePair of the cryptocoin data
     */
    public CryptoCoinHistoryTrendCalculator(TradePair tradePair) {

        super();

        this.dataProvider = new CryptoCoinHistoryBulkDataHandler();
        final MovingAverageCalculator maCalculator = new MovingAverageCalculator(this.dataProvider);
        final ExponentialMovingAverageCalculator emaCalculator = new ExponentialMovingAverageCalculator(this.dataProvider);

        this.maCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(maCalculator, dataProvider, dataProvider, tradePair);
        this.emaCalculator = new BulkCalculator<CryptocoinHistory, TrendValue>(emaCalculator, dataProvider, dataProvider, tradePair);
        this.tradePair = tradePair;
    }

    /**
     * Calculate all the trend values for all available trends
     */
    public void calculate() {

        final List<Trend> trends = this.dataProvider.getAllMovingAverageTrends();

        // Calculate for every availble trend the trend value
        for (final Trend trend : trends) {

            maCalculator.calculate();
        }
    }
}
