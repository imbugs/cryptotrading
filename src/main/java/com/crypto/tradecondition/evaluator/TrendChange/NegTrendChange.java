package com.crypto.tradecondition.evaluator.TrendChange;

import com.crypto.entities.TrendValue;

import javax.ejb.Stateful;
import java.util.function.Predicate;

/**
 * Evaluates a negative trend change
 *
 * Created by Jan Wicherink on 19-6-15.
 */
@Stateful
public class NegTrendChange extends TrendChangeEvaluator{

    public NegTrendChange () {
        super();

        Predicate<TrendValue> expression = (p) -> p.getDelta() <= 0;

        this.setExpression(expression);
    }
}
