package com.crypto.tradecondition.evaluator.TrendDelta;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.TrendValue;
import com.crypto.enums.TradeConditionType;

import java.util.function.Predicate;

/**
 * Evaluates a positive trend, the trend is positive when the delta values of previous values are positive.
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
public class PosTrend extends TrendDeltaEvaluator {

    /**
     * Constructor
     *
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public PosTrend(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        final Predicate<TrendValue> expression = (p) -> p.getDelta() >= 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.POS_TREND;
    }

}
