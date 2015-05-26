package com.crypto.datahandler.provider;

import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.entities.TradePair;

/**
 * Provider of crypto coin exchange data for the calculation of a moving average trend
 *
 * Created by Jan Wicherink on 1-5-15.
 * @param <D> datatype of dataprovider
 */
public interface DataProvider <D extends DataIndexProvider> {

    /**
     * Get a value at a given index
     *
     * @param index the index

     * @return the crypto coin value
     */
    public D getValue(final Integer index);

    /**
     * Get the trade pair
     * @return the trade pair
     */
    public TradePair getTradePair();

    /**
     * Set the trade pair
     */
    public void setTradePair(final TradePair tradePair);
}
