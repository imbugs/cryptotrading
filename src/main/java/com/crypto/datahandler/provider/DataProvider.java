package com.crypto.datahandler.provider;

import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;

/**
 * Provider of crypto coin exchange data for the calculation of a moving average trend
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public interface DataProvider {

    /**
     * Get the trade pair
     * @return the trade pair
     */
    public TradePair getTradePair();

    /**
     * Set the trading
     */
    public void setTrading(final Trading trading
    );
}
