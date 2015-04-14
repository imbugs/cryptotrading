package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jan Wicherink on 13-4-2015.
 */
public interface CryptocoinHistoryDao extends Serializable {

    /**
     * Persist a crypto coin history
     * @param cryptocoinHistory a crypto coin history
     */
    public void persist (CryptocoinHistory cryptocoinHistory);

    /**
     * Get crypto coin history by index and tradepair
     * @param tradePair the tradepair
     * @param indx the index
     * @return
     */
    public CryptocoinHistory getCryptoCoinHistoryByIndex (TradePair tradePair, Integer indx);

    /**
     * Get the cryptocoin history with the lowest index of a given trade pair.
     * @param tradePair the trade pair
     * @return the index
     */
    public Integer getStartIndex (TradePair tradePair);

    /**
     * Get the cryptocoin history with the latest (highest) index of a given trade pair.
     * @param tradePair the trade pair
     * @return the index
     */
    public Integer getLastIndex (TradePair tradePair);

    /**
     * Get all crypto coin history data of a given tradepair
     * @param tradePair the tradepair
     * @return the list of cryptocoin histories
     */
    public List<CryptocoinHistory> getAll (TradePair tradePair);

}
