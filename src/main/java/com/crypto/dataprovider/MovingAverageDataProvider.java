package com.crypto.dataprovider;

import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

/**
 * Provider of crypto coin exchange data for the calculation of a moving average trend
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public interface MovingAverageDataProvider {

    /**
     * Get the total sum of exchange rates of the crypto coin over a given period upto an index.
     *
     * @param index the index
     * @return the total sum of exchange rates
     */
    public Float getSumOverPeriod(final Integer index);

    /**
     * Get a crypto coin value at a given index
     *
     * @param index the index
     * @return the crypto coin value
     */
    public Float getValue (final Integer index);

    /**
     * Get a trend value at a given index
     *
     * @param index
     * @return the trend value
     */
    public TrendValue getTrendValue (final Integer index);

    /**
     * Get the trend of this moving average provider.
     *
     * @return the trend
     */
    public Trend getTrend ();

    /**
     * Get the trade pair
     * @return the trade pair
     */
    public TradePair getTradePair();
}