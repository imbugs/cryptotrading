package com.crypto.dao;

import com.crypto.entities.TradeConditionType;

import java.io.Serializable;

/**
 * Created by jan on 6-5-15.
 */
public interface TradeConditionTypeDao extends Serializable {

    /**
     * Get the trade condition by name
     * @param name the name of the trade condition
     * @return the trade condition
     */

    public TradeConditionType getByName (final String name);

}
