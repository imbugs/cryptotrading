package com.crypto.calculator;

import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

import java.util.logging.Logger;

/**
 * Smoothing moving average calculator, calculates a smoothing moving average of a trend
 *
 * Created by Jan Wicherink on 9-6-15.
 */
public class SmoothingMovingAverageCalculator implements TrendCalculator {

    private static final Logger LOG = Logger.getLogger(SmoothingMovingAverageCalculator.class.getName());

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
    public SmoothingMovingAverageCalculator(final MovingAverageDataProvider dataProvider, final Integer index, final Trend trend) {
        this.dataProvider = dataProvider;
        this.index = index;
        this.trend = trend;
    }

    /**
     * Constructor
     *
     * @param dataProvider data provider
     */
    public SmoothingMovingAverageCalculator(final MovingAverageDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * Default constructor
     */
    public SmoothingMovingAverageCalculator() {
    }

    /**
     * Calculate the moving average calculated value and delta of a given trend on the crypto coin exchange rate data
     *
     * @return the moving average calculated TrendValue
     */
    public void calculate() {

        // Get the period of the SMA trend
        final Integer period = getTrend().getPeriod();
        // Get the trend that is being smoothed
        final Trend smoothingTrend = getTrend().getSmoothingTrend();

        if (period != null && smoothingTrend != null) {

            final Float sum = this.dataProvider.getSumOverPeriod(this.index, smoothingTrend, period);

            if (sum != null) {
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
        }
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