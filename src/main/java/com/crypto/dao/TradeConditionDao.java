package com.crypto.dao;

import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradeRule;

import java.io.Serializable;
import java.util.List;

/**
 * Trade condition Dao
 * Created by Jan Wicherink on 6-5-15.
 */
public interface TradeConditionDao extends Serializable{

    /**
     * Get a tradecondition by Id
     * @param Id the id of the trade conditione
     * @return the trade condition
     */
    public TradeCondition get(final Integer Id);

    /**
     * Get all active tradeconditions of a trade rul
     * @param tradeRule the trade rule
     * @return the active tradeconditions of the trade rule
     */
    public List<TradeCondition> getAllActiveTradeConditionsOfTradeRule (final TradeRule tradeRule);
}
