package com.crypto.tradecondition.evaluator.MacdGradualChange;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.TradeCondition;
import com.crypto.enums.TradeConditionType;

import java.util.function.BiPredicate;
public class MacdDecrease extends MacdGradualChangeEvaluator {

    /**
     * Constructor
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public MacdDecrease(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        BiPredicate<TradeCondition, Float> expression = (t, p) -> (- p) > t.getPercentage() && (- p) < t.getPercentage_limit() && p < 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.MACD_DECREASE;
    }
}
