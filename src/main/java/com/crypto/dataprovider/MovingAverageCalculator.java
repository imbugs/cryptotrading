
package com.crypto.dataprovider;

import com.crypto.entities.TrendValue;

/**
 * Moving Average calculator, calculates a moving average value of crypto coin data
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public class MovingAverageCalculator {

    private MovingAverageDataProvider dataProvider;

    private Integer index;

    private Float value = null;

    private Float delta = null;

    /**
     * Constructor
     * @param dataProvider the data provider of this moving average
     * @param index the index of the moving average
     */
    public MovingAverageCalculator(MovingAverageDataProvider dataProvider, Integer index) {
        this.dataProvider = dataProvider;
        this.index = index;
    }

    /**
     * Default constructor
     */
    public MovingAverageCalculator() {

    }

    /**
     * Calculate the moving average value and delta of a given trend on the crypto coin exchange rate data
     *
     * @return the moving average value
     */
    public void calculate() {

        final Integer period = this.dataProvider.getTrend().getPeriod();
        final Float   sum    = this.dataProvider.getSumOverPeriod(this.index);
        final Float   movingAverage = sum/period;

        final Integer previousIndex = this.index - 1;
        final TrendValue previousValue = this.dataProvider.getTrendValue(previousIndex);

        if (previousValue != null) {
            this.delta = movingAverage - previousValue.getValue();
        }
    }

    public MovingAverageDataProvider getDataProvider() {
        return dataProvider;
    }

    public Integer getIndex() {
        return index;
    }

    public Float getValue() {
        return value;
    }

    public Float getDelta() {
        return delta;
    }
}