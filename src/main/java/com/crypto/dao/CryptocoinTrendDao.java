package com.crypto.dao;

import com.crypto.entities.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Jan Wicherink on 1-5-15.
 */
public interface CryptocoinTrendDao extends Serializable{

    /**
     * Get the trend value of a cryptocointrend value
     * @param index the index
     * @param trend the trend to fetch
     * @param tradePair the tradepair of the trend
     * @return the trend value
     */
    public TrendValue getTrendValue(final Integer index, final Trend trend, final TradePair tradePair);


    /**
     * Get all the cryptocointrend values with a given range.
     * @param fromIndex the start index
     * @param toIndex end index
     * @param trend the trend to fetch
     * @param tradePair the tradepair of the trend
     * @return the trend value
     */
    public List<TrendValue> getAllTrendValues(final Integer fromIndex, final Integer toIndex, final Trend trend, final TradePair tradePair);

    /**
     * Get the sum of a series of previous trend values over a period up to a given index
     * @param index the index
     * @param trend the trend
     * @param period the period
     * @param tradePair the trade pair
     * @return
     */
    public Float getSumTrend (final Integer index, final Trend trend, final Integer period, final TradePair tradePair);

    /**
     * Get the Macd value of a cryptcointrend value
     *
     * @param index the index of the macd value
     * @param macd the macd
     * @param tradePair the tradepari of the macd
     * @return the macd value
     */
    public MacdValue getMacdValue (final Integer index, final Macd macd, final TradePair tradePair);


    /**
     * Get al the Macd values from start index to end index of a given tradepair and macd
     * @param startIndex the start index
     * @param endIndex the end index
     * @param macd the macd
     * @param tradePair the trade pair
     * @return all of the macd values within the given range for a given trade pair and macd.
     */
    public List<MacdValue> getAllMacdValues (final Integer startIndex, final Integer endIndex, final Macd macd, final TradePair tradePair);

   /**
     * Stores a trend value
     * @param trendValue the trend value
     */
   public void storeTrendValue(final TrendValue trendValue);

    /**
     * Store the moving average macd value
     * @param macdValue the macd value to be stored
     */
    public void storeMacdValue(final MacdValue macdValue);


    /**
     * Delete all crypt coin trend data before a given date
     *
     * @param beforeDate the date before which all data is removed.
     */
    public void deleteBeforeDate(final Date beforeDate);


    /**
     * Truncate table data
     */
    public void truncateTable();

    /**
     *  Commit all open transactions
     *
     */
    public void commit();
}
