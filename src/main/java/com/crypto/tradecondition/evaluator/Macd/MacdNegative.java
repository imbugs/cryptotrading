package com.crypto.tradecondition.evaluator.Macd;

import com.crypto.entities.MacdValue;

import javax.ejb.Stateful;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is negative a given index
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
@Stateful
public class MacdNegative extends MacdEvaluator {

    /**
     * Default constructor.
     */
    public MacdNegative() {
        super();

        Predicate<MacdValue> expression = (p) -> p.getValue() < 0;

        this.setExpression(expression);
    }
}
