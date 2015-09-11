package com.crypto.services.rest.wrapper;

import java.util.List;

/**
 * Wrapper class for trade rule data.
 * Created by Jan Wicherink on 11-9-2015.
 */
public class TradeRuleWrapper {

    private String tradeRule;

    private List<String> tradeConditions;

    /**
     * Constructor
     * @param tradeRule the tradeRule
     * @param tradeConditions the list of trade conditions
     */
    public TradeRuleWrapper(String tradeRule, List<String> tradeConditions) {
        this.tradeRule = tradeRule;
        this.tradeConditions = tradeConditions;
    }

    public String getTradeRule() {
        return tradeRule;
    }

    public List<String> getTradeConditions() {
        return tradeConditions;
    }
}
