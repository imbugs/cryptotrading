
package com.crypto.entities;

import com.crypto.dataprovider.MovingAverageDataProvider;
import com.crypto.entities.TrendValue;

/**
 * Moving Average, a calculated moving average value
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public class MovingAverage {

    private MovingAverageDataProvider dataProvider;

    private Integer index;

    private Float value = null;

    private Float delta = null;


    /**
     * Constructor
     * @param dataProvider the data provider of this moving average
     * @param index the index of the moving average
     * @param value the value of the moving average
     * @param delta the difference with the previous moving average value at index= indx-1
     */
    public MovingAverage(MovingAverageDataProvider dataProvider, Integer index, Float value, Float delta) {
        this.dataProvider = dataProvider;
        this.index = index;
        this.value = value;
        this.delta = delta;
    }

    /**
     * Default constructor
     */
    public MovingAverage () {

    }

    /**
     * Calculate the moving average value and delta of this moving average
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