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

    // the index of the first cyrpto coin history (chronologically)
    private Integer startIndex;

     /**
     * Constructor
     * @param dataProvider the data provider for the calculation
     */
    public ExponentialMovingAverageCalculator(final MovingAverageDataProvider dataProvider, final Integer startIndex){
        super (dataProvider);
        this.startIndex = startIndex;
    }

    /**
     * Calculate the exponential moving average. Calculating an exponential moving average depends on previously calculated
     * exponential moving averages. For the first series of exponential moving averages (for the length of the period of the trend)
     * an moving average in stead of an exponential moving average is calculated. This is to give a head start to the calulcation of
     * exponential moving average trends.
     */
    public void calculate() {

        if (this.getIndex() <= getTrend().getPeriod() + this.startIndex) {
            // Perform a moving average calculation
            super.calculate();
        }
        else {
            // Perform an exponential moving average calculation

            final Float multiplier = (2F / (getTrend().getPeriod() + 1));
            final Integer previousIndex = this.getIndex() - 1;

            final TrendValue previousValue = this.getDataProvider().getTrendValue(previousIndex);
            final CryptocoinHistory currentRate = this.getDataProvider().getValue(this.getIndex());

            if (previousValue != null) {

                final Float ema = (currentRate.getClose() - previousValue.getValue()) * multiplier + previousValue.getValue();
                final TrendValue trendValue = new TrendValue(this.getDataProvider().getTradePair(), getIndex(), getTrend(), null, ema, ema - previousValue.getValue());
                this.setValue(trendValue);
            }
        }
    }
}
