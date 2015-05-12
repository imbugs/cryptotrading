package com.crypto.dataprovider;

import com.crypto.entities.TradePair;

/**
 * Provider of crypto coin exchange data for the calculation of a moving average trend
 *
 * Created by Jan Wicherink on 1-5-15.
 *
 * @param <D> the data type to be persisted
 */
public interface DataPersister<D>{

    /**
     * Store the value to the data store
     * @param value the value to be stored
     */
    public void storeValue(D value);

    /**
     * Get the index of a given value
     * @return the index of a value
     */
    public Integer getIndex();

    /**
     * Get the trade pair
     * @return the trade pair
     */
    public TradePair getTradePair();
}
