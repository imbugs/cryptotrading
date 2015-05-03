package com.crypto.calculator;

import com.crypto.dataprovider.MacdDataProvider;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TrendValue;

/**
 * The Calculated Macd Value
 *
 * Created by Jan Wicherink on 3-5-15.
 */
public class MacdValueCalculator {

    private Integer indx;

    private Float value;

    private Float delta;

    private MacdDataProvider dataProvider;

    /**
     * Constructor
     *
     * @param dataProvider the Macd data provider
     * @param indx the index of the macd value
     */
    public MacdValueCalculator(final MacdDataProvider dataProvider, final Integer indx) {
        this.dataProvider = dataProvider;
        this.indx = indx;
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
     * Calculates the macd value, the macd value is the difference between the short term trend value and
     * the long term trend value at a given index.
     * In addition the delta value is calculated with respect to the previous Macd value at index = indx - 1
     */
    public void calculate () {

        final TrendValue shortTrendvalue = dataProvider.getShortTrendValue(this.indx);
        final TrendValue longTrendvalue = dataProvider.getLongTrendValue(this.indx);

        this.value = shortTrendvalue.getValue() - longTrendvalue.getValue();

        final MacdValue previousMacdValue = this.dataProvider.getMacdValue(this.indx - 1);

        Float delta = null;

        if (previousMacdValue != null) {

            delta = this.value - previousMacdValue.getValue();
        }
        this.setDelta(delta);
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getDelta() {
        return delta;
    }

    public void setDelta(Float delta) {
        this.delta = delta;
    }
}
