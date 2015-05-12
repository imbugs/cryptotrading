
package com.crypto.calculator;

import com.crypto.dataprovider.MovingAverageDataProvider;
import com.crypto.entities.TrendValue;

/**
 * Moving Average calculator, calculates a moving average calculatedValue of crypto coin data
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public class MovingAverageCalculator implements Calculator{

    private MovingAverageDataProvider dataProvider;

    private Integer index;

    private Float calculatedValue = null;

    private Float delta = null;

    /**
     * Constructor
     * @param dataProvider the data provider of this moving average
     * @param index the index of the moving average
     */
    public MovingAverageCalculator(final MovingAverageDataProvider dataProvider, final Integer index) {
        this.dataProvider = dataProvider;
        this.index = index;
    }

    /**
     * Default constructor
     */
    public MovingAverageCalculator() {

    }

    /**
     * Calculate the moving average calculatedValue and delta of a given trend on the crypto coin exchange rate data
     *
     * @return the moving average calculatedValue
     */
    public void calculate() {

        final Integer period = this.dataProvider.getTrend().getPeriod();
        final Float   sum    = this.dataProvider.getSumOverPeriod(this.index);
        this.calculatedValue = sum/period;

        final Integer previousIndex = this.index - 1;
        final TrendValue previousValue = this.dataProvider.getTrendValue(previousIndex);

        if (previousValue != null) {
            this.delta = this.calculatedValue - previousValue.getValue();
        }
        else {
            this.delta = null;
        }
    }

    public MovingAverageDataProvider getDataProvider() {
        return dataProvider;
    }

    public Integer getIndex() {
        return index;
    }

    public Float getCalculatedValue() {
        return calculatedValue;
    }

    public Float getDelta() {
        return delta;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setValue(Float value) {
        this.calculatedValue = value;
    }

    public void setDelta(Float delta) {
        this.delta = delta;
    }
}