package com.crypto.tradecondition.evaluator.TrendChange;

import com.crypto.entities.TrendValue;
import com.crypto.enums.TradeConditionType;

import javax.ejb.Stateful;
import java.util.function.Predicate;

/**
 * Evaluates a negative trend change
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
@Stateful
public class NegTrendChange extends TrendChangeEvaluator {

    public NegTrendChange() {
        super();

        Predicate<TrendValue> expression = (p) -> p.getDelta() <= 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.NEG_TREND_CHANGE;
    }
}