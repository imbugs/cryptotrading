package com.crypto.tradecondition.evaluator.TrendChange;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.TrendValue;
import com.crypto.enums.TradeConditionType;

import java.util.function.Predicate;

/**
 * Evaluates a negative trend change
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
public class NegTrendChange extends TrendChangeEvaluator {

    /**
     * Constructor
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public NegTrendChange(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        final Predicate<TrendValue> expression = (p) -> p.getDelta() <= 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.NEG_TREND_CHANGE;
    }
}
