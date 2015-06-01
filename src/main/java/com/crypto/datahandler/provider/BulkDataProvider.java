package com.crypto.datahandler.provider;

import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;

import java.util.List;

/**
 * Bulk data provider provides cryptocoin history data in bulk
 *
 * Created by Jan Wicherink on 9-5-15.
 *
 * @param <D> datatype of the dataprovider
 */
public interface BulkDataProvider<D extends DataIndexProvider> extends DataProvider<D> {

    /**
     * Get all values stored of a given tradepair
     *
     * @return all values stored in the database
     */
    public List<D> getAll();


    /**
     * Get the lowest possible index of a value
     * @return
     */
    public Integer getStartIndex();

    /**
     * Get the last value stored (with highest index)
     * @return the last value stored in the database
     */
    public D getLast();

    /**
     * Get all available moving average trends
     * @return the moving average trends that are available
     */
    public List<Trend> getAllMovingAverageTrends();

    /**
     * Get all exponential moving average bulk
     *
     * @return the moving average bulk
     */
    public List<Trend> getAllExponentialMovingAverageTrends();


    /**
     * Get all smoothing moving average bulk
     *
     * @return the moving average bulk
     */
    public List<Trend> getAllSmoothingMovingAverageTrends();
}
