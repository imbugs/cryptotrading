package com.crypto.dao;

import com.crypto.entities.TradePair;
import com.crypto.entities.TradingSite;

/**
 * Created by Jan Wicherink on 7-4-2015.
 */
public interface TradePairDao {

    /**
     * Get the tradepair with the given trade pair id
     * @param id the trade pair identification
     * @return the trade pair.
     */
    public TradePair getTradePairById(final Integer id);

    /**
     * Update an existing trade pair
      * @param tradePair the tradepair to be updated
     */
    public void update (final TradePair tradePair);

    public TradePair getTradePairOfTradingSite (final TradingSite tradingSite);
}
