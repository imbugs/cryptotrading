package com.crypto.dao;

import com.crypto.entities.TradeRule;

import java.io.Serializable;
import java.util.List;

/**
 * Trade rule Dao
 *
 * Created by Jan Wicherink on 7-5-15.
 */
public interface TradeRuleDao extends Serializable{

    /**
     * Get a trade rule by Id
      * @param Id the id
     * @return the trade rule
     */
   public TradeRule get(final Integer Id);

    /**
     * Get all active trade rules
     *
     * @return all active trade rules
     */
   public List<TradeRule> getActiveRules ();


    /**
     * Get all active bull trade rules
     *
     * @return all active bull trade rules
     */

    public List<TradeRule> getActiveBullRules();


    /**
     * Get all active beaer trade rules
     *
     * @return all active bull trade rules
     */

    public List<TradeRule> getActiveBearRules();
}
