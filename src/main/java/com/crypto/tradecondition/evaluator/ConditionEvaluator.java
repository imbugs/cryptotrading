package com.crypto.tradecondition.evaluator;

import com.crypto.entities.TradeCondition;

import java.io.Serializable;

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
}
