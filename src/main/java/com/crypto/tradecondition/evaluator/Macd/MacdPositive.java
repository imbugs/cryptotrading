package com.crypto.tradecondition.evaluator.Macd;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.MacdValue;
import com.crypto.enums.TradeConditionType;

import java.util.function.Predicate;

/**
 * Evaluates if a Macd is positive at a given index
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
public class MacdPositive extends MacdEvaluator {

    /**
     * Constructor
     *
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public MacdPositive(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        Predicate<MacdValue> expression = (m) -> m.getValue() > 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.MACD_POSITIVE;
    }
}
