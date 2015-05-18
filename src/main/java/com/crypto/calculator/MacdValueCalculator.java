package com.crypto.calculator;

import com.crypto.datahandler.provider.MacdDataProvider;
import com.crypto.entities.MacdValue;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

/**
 * The Calculated Macd Value
 *
 * Created by Jan Wicherink on 3-5-15.
 */
public class MacdValueCalculator implements TrendCalculator {

    private Integer indx;

    private Trend shortTrend;

    private Trend longTrend;

    private Float calculatedValue;

    private Float delta;

    private MacdDataProvider dataProvider;

    /**
     * Constructor
     *
     * @param dataProvider the Macd data provider
     * @param indx the index of the macd calculatedValue
     */
    public MacdValueCalculator(final MacdDataProvider dataProvider, final Integer indx, final Trend shortTrend, final Trend longTrend) {
        this.dataProvider = dataProvider;
        this.indx = indx;
        this.shortTrend = shortTrend;
        this.longTrend = longTrend;
    }

    /**
     * Default constructor
     */
    public MacdValueCalculator() {

    }

    public void setIndx(Integer indx) {

        this.indx = indx;
    }

    /**
     * Calculates the macd calculatedValue, the macd calculatedValue is the difference between the short term trend calculatedValue and
     * the long term trend calculatedValue at a given index.
     * In addition the delta calculatedValue is calculated with respect to the previous Macd calculatedValue at index = indx - 1
     */
    public void calculate () {

        final TrendValue shortTrendvalue = dataProvider.getShortTrendValue(this.indx);
        final TrendValue longTrendvalue = dataProvider.getLongTrendValue(this.indx);

        this.calculatedValue = shortTrendvalue.getValue() - longTrendvalue.getValue();

        final MacdValue previousMacdValue = this.dataProvider.getValue(this.indx - 1);

        Float delta = null;

        if (previousMacdValue != null) {

            delta = this.calculatedValue - previousMacdValue.getValue();
        }
        this.setDelta(delta);
    }

    public Float getCalculatedValue() {
        return calculatedValue;
    }

    public Float getDelta() {
        return delta;
    }

    public Integer getIndex() {
        return indx;
    }

    public void setIndex(Integer index) {
      this.indx = index;
    }

    public void setDelta(Float delta) {

        this.delta = delta;
    }

    public Trend getShortTrend() {
        return shortTrend;
    }

    public void setShortTrend(Trend shortTrend) {
        this.shortTrend = shortTrend;
    }

    public Trend getLongTrend() {
        return longTrend;
    }

    public void setLongTrend(Trend longTrend) {
        this.longTrend = longTrend;
    }
}
