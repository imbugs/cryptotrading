package com.crypto.calculator;

import com.crypto.datahandler.provider.MacdDataProvider;
import com.crypto.entities.Macd;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TrendValue;

/**
 * The Macd calculator
 * <p/>
 * Created by Jan Wicherink on 3-5-15.
 */
public class MacdValueCalculator implements MacdCalculator {

    private Integer indx;

    private Macd macd;

    private TrendValue calculatedValue;

    private MacdDataProvider dataProvider;

    /**
     * Constructor
     *
     * @param dataProvider the Macd data provider
     * @param indx         the index of the macd calculatedValue
     */
    public MacdValueCalculator(final MacdDataProvider dataProvider, final Integer indx, final Macd macd) {
        this.dataProvider = dataProvider;
        this.indx = indx;
        this.macd = macd;
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
    public void calculate() {

        final TrendValue shortTrendvalue = this.dataProvider.getShortTrendValue(this.indx);
        final TrendValue longTrendvalue = this.dataProvider.getLongTrendValue(this.indx);

        final Float value = shortTrendvalue.getValue() - longTrendvalue.getValue();

        final MacdValue previousMacdValue = this.dataProvider.getValue(this.indx - 1);

        Float delta = null;

        if (previousMacdValue != null) {

            delta = value - previousMacdValue.getValue();
        }

        final TrendValue trendValue = new TrendValue(this.dataProvider.getTradePair(), getIndex(), null, this.macd, value, delta);

        this.calculatedValue = trendValue;
    }

    public TrendValue getCalculatedValue() {

        return calculatedValue;
    }

    @Override
    public void setDelta(Float delta) {
        this.calculatedValue.setDelta(delta);
    }

    @Override
    public Float getDelta() {
        return this.calculatedValue.getDelta();
    }

    public Integer getIndex() {

        return indx;
    }

    public void setIndex(Integer index) {

        this.indx = index;
    }

    @Override
    public Macd getMacd() {
        return this.macd;
    }

    @Override
    public void setMacd(Macd macd) {
        this.macd = macd;
    }
}
