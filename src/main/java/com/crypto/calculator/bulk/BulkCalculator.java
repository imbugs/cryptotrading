package com.crypto.calculator.bulk;

import com.crypto.calculator.Calculator;
import com.crypto.dataprovider.BulkDataProvider;
import com.crypto.dataprovider.DataProvider;
import com.crypto.entities.TradePair;

import java.util.List;

/**
 * Generic bulk calculator, calculates moving averages, smoothing moving averages, macdś etc. in bulk
 * for cryptocoin histories
 *
 * Created by Jan Wicherink on 9-5-15.
 */
public class BulkCalculator <D extends DataProvider> {

    private Calculator calculator;

    private BulkDataProvider dataProvider;

    private  TradePair tradePair;

    /**
     * Constructor
     *
     * @param calculator
     * @param dataProvider
     */
    public BulkCalculator(Calculator calculator, BulkDataProvider dataProvider, TradePair tradePair) {
        this.calculator = calculator;
        this.dataProvider = dataProvider;
        this.tradePair = tradePair;
    }

    /**
     * Default constructor
     */
    public BulkCalculator () {

    }

    /**
     * Calculate in bulk all of the available cryptocoin history data.
     */
    public void calculate () {

        final List<D> bulkData = dataProvider.getAll(this.tradePair);

        for (final D data : bulkData) {
            calculator.setIndex(data.getIndex());
            calculator.calculate();
        }
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public BulkDataProvider getDataProvider() {
        return dataProvider;
    }

    public TradePair getTradePair() {
        return tradePair;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public void setDataProvider(BulkDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public void setTradePair(TradePair tradePair) {
        this.tradePair = tradePair;
    }
}
