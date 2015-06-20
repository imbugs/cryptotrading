package com.crypto.tradecondition.evaluator.MacdChange;

import com.crypto.entities.MacdValue;

import javax.ejb.Stateful;
import java.util.function.BiPredicate;

/**
 * Evaluates if a macd value changes from negative to positive.
 * Created by Jan Wicherink  on 19-6-15.
 */
@Stateful
public class MacdNegChange extends MacdChangeEvaluator {

    /**
     * Constructor
     */
    public MacdNegChange() {
        super();

        BiPredicate<MacdValue, MacdValue> expression = (p, c) -> p.getValue() > 0 && c.getValue() <= 0;

        this.setExpression(expression);
    }
}


