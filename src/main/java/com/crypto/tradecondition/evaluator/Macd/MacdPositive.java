package com.crypto.tradecondition.evaluator.Macd;

import com.crypto.entities.MacdValue;

import javax.ejb.Stateful;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is positive at a given index
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
@Stateful
public class MacdPositive extends MacdEvaluator {

    /**
     * Default constructor.
     */
    public MacdPositive() {
        super();

        Predicate<MacdValue> expression = (m) -> m.getValue() > 0;

        this.setExpression(expression);
    }
}
