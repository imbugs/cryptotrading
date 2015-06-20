package com.crypto.tradecondition.evaluator.TrendGradualChange;

import com.crypto.entities.TradeCondition;
import com.crypto.enums.TradeConditionType;

import javax.ejb.Stateful;
import java.util.function.BiPredicate;

@Stateful
public class TrendDecrease extends TrendGradualChangeEvaluator {

    public TrendDecrease() {
        super();

        BiPredicate<TradeCondition, Float> expression = (t, p) -> (- p) > t.getPercentage() && (- p) < t.getPercentage_limit() && p < 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.TREND_DECREASE;
    }
}
