package com.crypto.dataprovider;

import com.crypto.entities.TradePair;

import java.util.List;

/**
 * Bulk data provider provides cryptocoin history data in bulk
 *
 * Created by Jan Wicherink on 9-5-15.
 */
public interface BulkDataProvider<D> extends DataProvider<D> {

    /**
     * Get all values stored of a given tradepair
     * @param tradePair the tradepair
     *
     * @return all values stored in the database
     */
    public List<D> getAll(final TradePair tradePair);

    /**
     * Get the last value stored (with highest index)
     * @return the last value stored in the database
     */
    public D getLast();
}
