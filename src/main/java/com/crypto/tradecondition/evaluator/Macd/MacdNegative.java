package com.crypto.tradecondition.evaluator.Macd;

import com.crypto.entities.MacdValue;
import com.crypto.enums.TradeConditionType;

import javax.ejb.Stateful;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is negative a given index
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
@Stateful
public class MacdNegative extends MacdEvaluator {

    /**
     * Default constructor.
     */
    public MacdNegative() {
        super();

        Predicate<MacdValue> expression = (m) -> m.getValue() < 0;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.MACD_NEGATIVE;
    }
}
