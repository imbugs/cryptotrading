package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Jan Wicherink on 13-4-2015.
 */
public interface CryptocoinHistoryDao extends Serializable {

    /**
     * Persist a crypto coin history
     *
     * @param cryptocoinHistory a crypto coin history
     */
    public void persist(final CryptocoinHistory cryptocoinHistory);

    /**
     * Get crypto coin history by index and tradepair
     *
     * @param tradePair the tradepair
     * @param indx      the index
     * @return
     */
    public CryptocoinHistory getCryptoCoinHistoryByIndex(final TradePair tradePair, final Integer indx);

    /**
     * Get the cryptocoin history with the lowest index of a given trade pair.
     *
     * @param tradePair the trade pair
     * @return the index
     */
    public Integer getStartIndex(final TradePair tradePair);

    /**
     * Get the cryptocoin history with the latest (highest) index of a given trade pair.
     *
     * @param tradePair the trade pair
     * @return the index
     */
    public Integer getLastIndex(final TradePair tradePair);


    /**
     * Get all crypto coin history data of a given tradepair
     *
     * @param tradePair the tradepair
     * @return the list of cryptocoin histories
     */
    public List<CryptocoinHistory> getAll(final TradePair tradePair);


    /**
     * Get the last crypto coin history data of a given trade pair
     *
     * @param tradePair the tradepair
     * @return the last crypto coin history
     */
    public CryptocoinHistory getLast(final TradePair tradePair);

    /**
     * Get the crypto coin history by timestamp
     *
     * @param tradePair the tradepair
     * @param timestamp the timestamp
     * @return the crypto coin history with the given timestamp
     */
    public CryptocoinHistory getCryptoCoinHistoryByTimestamp(final TradePair tradePair, final Timestamp timestamp);


    /**
     * Get all the crypto coin histories since a given date
     * @param tradePair the trade pair
     * @param sinceDate the since date
     * @return the list of crypto coin histories.
     */
    public List<CryptocoinHistory> getCryptoCoinHistorySinceDate (final TradePair tradePair, final Date sinceDate);


    /**
     * Get all the crypto coin histories since a given index
     * @param tradePair the trade pair
     * @param index  the index
     * @return the list of crypto coin histories.
     */
    public List<CryptocoinHistory> getCryptoCoinHistorySinceIndex (final TradePair tradePair, final Integer index);

    /**
     * Get all the crypto coin histories in a range of indexes
     * @param tradePair the trade pair
     * @param fromIndex  the lower index
     * @param toIndex the upper index
     * @return the list of crypto coin histories.
     */
    public List<CryptocoinHistory> getCryptoCoinHistoryRangeIndex (final TradePair tradePair, final Integer fromIndex, final Integer toIndex);

    /**
     * Get the earliest timestamp of all of the crypto coin histories in the database
     * @param tradePair the trade pair
     * @return the timestamp
     */
    public Timestamp getEarliestDate (final TradePair tradePair);

    /**
     * Get the latest timestamp of all of the crypto coin histories in the database
     * @param tradePair the trade pair
     * @return the timestamp
     */
    public Timestamp getLatestDate (final TradePair tradePair);


    /**
     * Get the sum of the closing crypto currency rate starting from a given index for a given period
     * @param index the start index
     * @param period the period in number of indexes
     * @param tradePair the trade pair
     * @return the total sum of the crypto curreny rates
     */
    public Float getSumCryptoCoinRate (final Integer index, final Integer period, final TradePair tradePair);

    /**
     * Delete all crypto currencies before a given date
     * @param date the date
     */
    public void deleteBeforeDate (Date date);


}
