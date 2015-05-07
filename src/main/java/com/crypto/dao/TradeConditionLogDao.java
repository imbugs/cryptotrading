package com.crypto.dao;

import com.crypto.entities.TradeConditionLog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Trade condition log Dao
 *
 * Created by Jan Wicherink on 7-5-15.
 */
public interface TradeConditionLogDao extends Serializable {

    /**
     * Trade condition log to be persisted
     * @param tradeConditionLog trade condition log
     */
    public void persist(final TradeConditionLog tradeConditionLog);

    /**
     * Get all trade condition logging
     * @return the trade condition logging
     */
    public List<TradeConditionLog> getAll();

    /**
     * Remove trade condition log before a given date
     *
     * @param beforeDate the date before which trade condition log is removed
     */
    public void deleteBeforeDate (final Date beforeDate);
}
