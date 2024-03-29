package com.crypto.dao;

import com.crypto.entities.Trading;
import com.crypto.entities.TradingSite;

import java.io.Serializable;
import java.util.List;

/**
 * Trading Dao
 *
 * Created by Jan Wicherink on 25-4-15.
 */
public interface TradingDao extends Serializable {

    /**
     * Get a util
     * @param Id the id of the util
     * @return the util
     */
    public Trading get(final Integer Id);

    /**
     * Get all tradings
     * @return all tradings
     */
    public List<Trading> getAll ();

    /**
     * Get all active tradings
     * @return all active tradings
     */
    public List<Trading> getActiveTradings();

    /**
     * Get active tradings of a util site
     * @param tradingSite the util site
     * @return all active tradings of a util site
     */
    public List<Trading> getActiveTradingsOfTradingSite (final TradingSite tradingSite);

    /**
     * Update a util
     * @param trading the util to be updated.
     */
    public void update (final Trading trading);
}
