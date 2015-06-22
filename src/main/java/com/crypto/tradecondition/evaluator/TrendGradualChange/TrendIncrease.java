package com.crypto.tradecondition.evaluator.TrendGradualChange;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.TradeCondition;
import com.crypto.enums.TradeConditionType;

import java.util.function.BiPredicate;

public class TrendIncrease extends TrendGradualChangeEvaluator {

    /**
     * Constructor
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public TrendIncrease(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        final BiPredicate<TradeCondition, Float> expression = (t, p) -> p > t.getPercentage() && p < t.getPercentage_limit() && p > 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.TREND_INCREASE;
    }
}
