package com.crypto.calculator;

import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

import java.util.logging.Logger;

/**
 * Moving Average calculator, calculates a moving average calculatedValue of crypto coin data
 * Created by Jan Wicherink on 1-5-15.
 */
public class MovingAverageCalculator implements TrendCalculator {

    private static final Logger LOG = Logger.getLogger(MovingAverageCalculator.class.getName());

    private MovingAverageDataProvider dataProvider;

    private Integer index;

    private Trend trend;

    private TrendValue calculatedValue = null;

    /**
     * Constructor
     *
     * @param dataProvider the data provider of this moving average
     * @param index        the index of the moving average
     * @param trend        the trend of this calculator
     */
    public MovingAverageCalculator(final MovingAverageDataProvider dataProvider, final Integer index, final Trend trend) {
        this.dataProvider = dataProvider;
        this.index = index;
        this.trend = trend;
    }

    /**
     * Constructor
     *
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
     * Calculate the moving average calculated value and delta of a given trend on the crypto coin exchange rate data
     *
     * @return the moving average calculated TrendValue
     */
    public void calculate() {

        final Integer period = getTrend().getPeriod();
        final Float sum = this.dataProvider.getSumOverPeriod(this.index, period);
        final Float value = sum / period;
        Float delta = null;

        final Integer previousIndex = this.index - 1;
        final TrendValue previousValue = this.dataProvider.getTrendValue(previousIndex);

        if (previousValue != null) {
            delta = value - previousValue.getValue();
        }

        this.calculatedValue = new TrendValue(this.getDataProvider().getTradePair(), this.index, this.trend, null, value, delta);

        LOG.info("Calculated index : " + this.index.toString());
    }

    public MovingAverageDataProvider getDataProvider() {
        return dataProvider;
    }

    public Integer getIndex() {
        return index;
    }

    public TrendValue getCalculatedValue() {
        return this.calculatedValue;
    }

    public Float getDelta() {
        return calculatedValue.getDelta();
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

    public void setValue(final TrendValue value) {

        this.calculatedValue = value;
    }

    public void setDelta(final Float delta) {
        this.calculatedValue.setDelta(delta);
    }
}