package com.crypto.dao;

import com.crypto.entities.TradePair;
import com.crypto.entities.TradingSite;

import java.io.Serializable;

/**
 * Created by Jan Wicherink on 7-4-2015.
 */
public interface TradePairDao extends Serializable {

    /**
     * Add a trade pair to the databasae
     * @param tradePair the trade pair to be persited
     */
     public void persist (TradePair tradePair);

     /**
     * Get the tradepair with the given trade pair id
     * @param id the trade pair identification
     * @return the trade pair.
     */
    public TradePair get(final Integer id);

    /**
     * Update an existing trade pair
      * @param tradePair the tradepair to be updated
     */
    public void update(final TradePair tradePair);

    public TradePair getTradePairOfTradingSite (final TradingSite tradingSite);
}
