package com.crypto.dataprovider;

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


    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public void setDataProvider(final MacdDataProvider dataProvider, final Integer indx) {
        this.dataProvider = dataProvider;
        this.indx = indx;
    }

    /**
     * Default constructor
     */
    public MacdValueCalculator() {

    }

    /**
     * Calculates the macd value
     */
    public void calculate () {

        final TrendValue shortTrendvalue = dataProvider.getShortTrendValue(this.indx);
        final TrendValue longTrendvalue = dataProvider.getLongTrendValue(this.indx);

        final Float macdValue = shortTrendvalue.getValue() - longTrendvalue.getValue();

        this.setValue(macdValue);

        final com.crypto.entities.MacdValue previousMacdValue = this.dataProvider.getMacdValue(this.indx - 1);

        final Float delta = macdValue - previousMacdValue.getValue();

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
