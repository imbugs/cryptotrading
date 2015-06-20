package com.crypto.tradecondition.evaluator;

import com.crypto.enums.TradeConditionType;

/**
 * Condition evaluator
 * <p/>
 * Created by Jan Wicherink on 10-6-15.
 */
public interface ConditionEvaluator {

    /**
     * Evaluate a trade condition
     *
     * @return true when the trade condition is true, false otherwise
     */
    public Boolean evaluate();

    /**
     * Get the implemented trade condition type by the evaluator
     *
     * @return the trade condition type implemented.
     */
    public TradeConditionType getImplementedTradeConditionType();
}
