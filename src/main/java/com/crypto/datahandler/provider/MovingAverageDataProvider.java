package com.crypto.datahandler.provider;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

/**
 * Provider of crypto coin exchange data for the calculation of a moving average trend
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public interface MovingAverageDataProvider extends DataProvider<CryptocoinHistory> {

    /**
     * Get the total sum of exchange rates of the crypto coin over a given period upto an index.
     *
     * @param index the index
     * @param period the period of which the sum is taken
     * @return the total sum of exchange rates
     */
    public Float getSumOverPeriod(final Integer index, final Integer period);

    /**
     * Get a trend value at a given index
     *
     * @param index the index of the value
     * @param trend the trend of the value
     * @param tradePair the tradepair
     * @return the trend value
     */
    public TrendValue getTrendValue (final Integer index, final Trend trend, final TradePair tradePair);
}
