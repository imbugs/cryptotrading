package com.crypto.calculator;

import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

/**
 * Exponential Moving Average calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public class ExponentialMovingAverageCalculator extends MovingAverageCalculator {

    /**
     * Constructor
     * @param dataProvider data provider for the calculator
     * @param index the index of the calculated value
     * @param trend the trend of the calculation
     */
    public ExponentialMovingAverageCalculator(final MovingAverageDataProvider dataProvider, final Integer index, final Trend trend) {
        super(dataProvider, index, trend);
    }

    /**
     * Constructor
     * @param dataProvider the data provider for the calculation
     */
    public ExponentialMovingAverageCalculator(final MovingAverageDataProvider dataProvider){
        super (dataProvider);
    }

    /**
     * Calculate the exponential moving average
     */
    public void calculate() {

        final Float multiplier = (2F / (getTrend().getPeriod() + 1));
        final Integer previousIndex = this.getIndex() - 1;

        final TrendValue previousValue = this.getDataProvider().getTrendValue(previousIndex);
        final CryptocoinHistory currentRate = this.getDataProvider().getValue(this.getIndex());

        if (previousValue != null) {

            final Float ema = (currentRate.getClose() - previousValue.getValue()) * multiplier + previousValue.getValue();
            final TrendValue trendValue = new TrendValue(this.getDataProvider().getTradePair(), getIndex(), getTrend(), null, ema, ema -previousValue.getValue());
            this.setValue(trendValue);
        }
    }
}
