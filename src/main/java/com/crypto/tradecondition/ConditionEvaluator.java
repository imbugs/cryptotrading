package com.crypto.tradecondition;

import com.crypto.entities.TradeCondition;

/**
 * Created by jan on 10-6-15.
 */
@FunctionalInterface
public interface ConditionEvaluator {

    /**
     * Evaluate a trade condition
     * @param index the index of the cryptocoin currency in the trading
     * @param tradeCondition the trade condition
     * @return true when the trade condition is true, false otherwise
     */
    public abstract Boolean evaluate(final Integer index, final TradeCondition tradeCondition);
}
