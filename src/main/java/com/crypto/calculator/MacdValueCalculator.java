package com.crypto.calculator;

import com.crypto.datahandler.impl.MacdBulkDataHandler;
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

    private MacdValue calculatedValue;

    private MacdBulkDataHandler dataProvider;

    /**
     * Constructor
     *
     * @param dataProvider the Macd data provider
     */
    public MacdValueCalculator(final MacdBulkDataHandler dataProvider) {

        this.dataProvider = dataProvider;
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

        if (shortTrendvalue != null && longTrendvalue != null) {
            final Float value = shortTrendvalue.getValue() - longTrendvalue.getValue();

            final MacdValue previousMacdValue = this.dataProvider.getMacdValue(this.indx - 1);

            Float delta = null;

            if (previousMacdValue != null) {
                delta = value - previousMacdValue.getValue();
            }

            final MacdValue macdValue = new MacdValue(getIndex(), getMacd(), this.dataProvider.getTradePair(),  value, delta);

            this.calculatedValue = macdValue;
        }
    }

    @Override
    public MacdValue getCalculatedValue() {
        return this.calculatedValue;
    }

       public void setDelta(Float delta) {
        this.calculatedValue.setDelta(delta);
    }

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
