package com.crypto.tradecondition.evaluator.MacdChange;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.MacdValue;
import com.crypto.enums.TradeConditionType;

import java.util.function.BiPredicate;

/**
 * Evaluates if a macd value changes from negative to positive.
 * Created by Jan Wicherink  on 19-6-15.
 */
public class MacdPosChange extends MacdChangeEvaluator {

    /**
     * Constructor
     *
     * @param signalBulkDataHandler the signal evaluator data provider
     */

    public MacdPosChange(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        BiPredicate<MacdValue, MacdValue> expression = (p, c) -> p.getValue() < 0 && c.getValue() >= 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.POS_MACD_CHANGE;
    }
}


