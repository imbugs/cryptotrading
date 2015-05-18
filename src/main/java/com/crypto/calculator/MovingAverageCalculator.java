
package com.crypto.calculator;

import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

/**
 * Moving Average calculator, calculates a moving average calculatedValue of crypto coin data
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public class MovingAverageCalculator implements TrendCalculator {

    private MovingAverageDataProvider dataProvider;

    private Integer index;

    private Float calculatedValue = null;

    private Float delta = null;

    private Trend trend;

    /**
     * Constructor
     * @param dataProvider the data provider of this moving average
     * @param index the index of the moving average
     * @param trend the trend of this calculator
     */
    public MovingAverageCalculator(final MovingAverageDataProvider dataProvider, final Integer index, final Trend trend) {
        this.dataProvider = dataProvider;
        this.index = index;
        this.trend = trend;
    }

    /**
     * Constructor
     * @param dataProvider data provider
     */
    public MovingAverageCalculator(final MovingAverageDataProvider dataProvider) {
        this.dataProvider = dataProvider;
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

        final Integer period = getTrend().getPeriod();
        final Float   sum    = this.dataProvider.getSumOverPeriod(this.index, period);
        this.calculatedValue = sum/period;

        final Integer previousIndex = this.index - 1;
        final TrendValue previousValue = this.dataProvider.getTrendValue(previousIndex, getTrend());

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

    public Trend getTrend() {
        return this.trend;
    }

    public void setTrend(Trend trend) {
         this.trend = trend;
    }

    public void setValue(Float value) {
        this.calculatedValue = value;
    }

    public void setDelta(Float delta) {
        this.delta = delta;
    }
}