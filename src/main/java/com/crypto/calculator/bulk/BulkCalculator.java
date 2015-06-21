package com.crypto.calculator.bulk;

import com.crypto.calculator.Calculator;
import com.crypto.calculator.TrendCalculator;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.entities.TradePair;

/**
 * Generic bulk calculator, calculates moving averages, smoothing moving averages, macdś etc. in bulk
 * for cryptocoin histories
 *
 * @param <D> Datatype of the bulkcalculator,maybe TrendValue or CryptocoinHistory
 *            <p/>
 *            Created by Jan Wicherink on 9-5-15.
 */
public class BulkCalculator<D extends DataIndexProvider, E> {

    private Calculator calculator;

    private BulkDataProvider dataProvider;

    private DataPersister dataPersister;

    private TradePair tradePair;

    /**
     * Constructor
     *
     * @param calculator
     * @param dataProvider
     */
    public BulkCalculator(Calculator calculator, BulkDataProvider dataProvider, DataPersister dataPersister, TradePair tradePair) {
        this.calculator = calculator;
        this.dataProvider = dataProvider;
        this.dataProvider.setTradePair(tradePair);
        this.dataPersister = dataPersister;
        this.tradePair = tradePair;
    }

    /**
     * Default constructor
     */
    public BulkCalculator() {

    }

    /**
     * Calculate in bulk all of the available cryptocoin history data.
     */
    public void calculate() {

        this.dataProvider.getAll().stream().forEach((data) -> {

            calculator.setIndex(((D) data).getIndex());
            calculator.calculate();

            if (calculator.getCalculatedValue() != null) {
                dataPersister.storeValue(calculator.getCalculatedValue());
            }
        });
    }

    /**
     * Calculate the last vast value
     */
    public void calculateLast() {
        final D data = (D) getDataProvider().getLast();

        getCalculator().setIndex(data.getIndex());
        getCalculator().calculate();

        dataPersister.storeValue(calculator.getCalculatedValue());
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

    public void setCalculator(TrendCalculator calculator) {
        this.calculator = calculator;
    }

    public void setDataProvider(BulkDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public void setTradePair(TradePair tradePair) {
        this.tradePair = tradePair;
    }
}