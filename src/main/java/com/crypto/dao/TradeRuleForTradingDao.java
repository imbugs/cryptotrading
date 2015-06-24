package com.crypto.dao;

import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;

import java.io.Serializable;
import java.util.List;

/**
 * Trade rule for util DAO.
 *
 * Created by Jan Wicherink on 7-5-15.
 */
public interface TradeRuleForTradingDao extends Serializable{

    public List<TradeRule> getTradeRules(final Trading trading);
}
