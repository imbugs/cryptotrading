package com.crypto.calculator.bulk;

import com.crypto.calculator.TrendCalculator;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import org.jboss.arquillian.transaction.api.annotation.Transactional;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

    private TrendCalculator calculator;

    private BulkDataProvider dataProvider;

    private DataPersister dataPersister;

    private TradePair tradePair;

    /**
     * Constructor
     *
     * @param calculator
     * @param dataProvider
     */
    public BulkCalculator(TrendCalculator calculator, BulkDataProvider dataProvider, DataPersister dataPersister, TradePair tradePair) {
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

        final List<D> bulkData = dataProvider.getAll();

        for (final D data : bulkData) {
              calculator.setIndex(data.getIndex());
              calculator.calculate();

              dataPersister.storeValue(calculator.getCalculatedValue());
        }
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

    public TrendCalculator getCalculator() {
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