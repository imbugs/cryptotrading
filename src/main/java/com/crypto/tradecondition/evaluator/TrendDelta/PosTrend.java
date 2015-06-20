package com.crypto.tradecondition.evaluator.TrendDelta;

import com.crypto.entities.TrendValue;
import com.crypto.enums.TradeConditionType;

import javax.ejb.Stateful;
import java.util.function.Predicate;

/**
 * Evaluates a positive trend, the trend is positive when the delta values of previous values are positive.
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
@Stateful
public class PosTrend extends TrendDeltaEvaluator {

    public PosTrend() {
        super();

        Predicate<TrendValue> expression = (p) -> p.getDelta() >= 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.POS_TREND;
    }

}
