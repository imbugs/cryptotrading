package com.crypto.calculator;

import com.crypto.dataprovider.MovingAverageDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TrendValue;

/**
 * Exponential Moving Average calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public class ExponentialMovingAverageCalculator extends MovingAverageCalculator {

    public ExponentialMovingAverageCalculator(MovingAverageDataProvider dataProvider, Integer index) {
        super(dataProvider, index);
    }

    /**
     * Calculate the exponential moving average
     */
    public void calculate() {

        final Float multiplier = (2F / (this.getDataProvider().getTrend().getPeriod() + 1));
        final Integer previousIndex = this.getIndex() - 1;

        final TrendValue previousValue = this.getDataProvider().getTrendValue(previousIndex);
        final CryptocoinHistory currentRate = this.getDataProvider().getValue(this.getIndex());

        if (previousValue != null) {

            final Float ema = currentRate.getClose() - previousValue.getValue() * multiplier + previousValue.getValue();
            this.setValue(ema);
            this.setDelta(ema -previousValue.getValue());
        }
    }
}
