package com.crypto.datahandler.provider;

import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.Macd;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TrendValue;

/**
 * Provider of macd and trend data for the calculation of a macd
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public interface MacdDataProvider extends DataProvider {

    /**
     * Get the short trend value
     * @param index the index of the short trend value
     * @return the short trend value
     */
    public TrendValue getShortTrendValue(final Integer index);

    /**
     * Get the long trend value
     * @param index the index of the long trend value
     * @return the long trend value
     */
    public TrendValue getLongTrendValue(final Integer index);

    /**
     * Get the macd of this moving average provider.
     * @param index the index of the Macd value;
     * @return the macd
     */
    public MacdValue getMacdValue(final Integer index);
}
