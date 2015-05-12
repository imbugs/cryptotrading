package com.crypto.calculator.bulk;

import com.crypto.calculator.Calculator;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;

import java.util.List;

/**
 * Generic bulk calculator, calculates moving averages, smoothing moving averages, macdś etc. in bulk
 * for cryptocoin histories
 *
 * @param <D> Datatype of the bulkcalculator,maybe TrendValue or CryptocoinHistory
 *
 * Created by Jan Wicherink on 9-5-15.
 */
public class BulkCalculator <D extends DataIndexProvider, E> {

    private Calculator calculator;

    private BulkDataProvider dataProvider;

    private DataPersister dataPersister;

    private  TradePair tradePair;

    /**
     * Constructor
     *
     * @param calculator
     * @param dataProvider
     */
    public BulkCalculator(Calculator calculator, BulkDataProvider dataProvider, DataPersister dataPersister, TradePair tradePair) {
        this.calculator = calculator;
        this.dataProvider = dataProvider;
        this.dataPersister = dataPersister;
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

            dataPersister.storeValue(calculator.getCalculatedValue());
        }
    }

    /**
     * Calculate the last cryptocoin history
     */
    public void calculateLast() {
        final CryptocoinHistory cryptocoinHistory = (CryptocoinHistory) getDataProvider().getLast();

        getCalculator().setIndex(cryptocoinHistory.getIndex());
        getCalculator().calculate();
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
