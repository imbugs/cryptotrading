package com.crypto.tradecondition.evaluator.Macd;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeConditionLog;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is positive at a given index
 *
 * Created by Jan Wicherink on 12-6-15.
 */
public abstract class MacdEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 3523938793517562905L;

    private Predicate<MacdValue> expression;

    /**
     * Constructor
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public MacdEvaluator(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);
    }

    /**
     * Checks the condition of a Macd at a given index is trye for all values (AND condition), or just for any one of the values (OR condition)
     *
     * @return true when the condition is true
     */
    public Boolean evaluate() {

        verifyImplementation();

        Boolean evaluation = false;

        // Check if the expression is true for values in range indx-period-1 to indx
        final Integer start = getIndex() - getTradeCondition().getPeriod() + 1;
        final Integer end = getIndex();

        for (Integer indx = start; indx <= end; indx++) {

            MacdValue currentMacdValue = getMacdValue(indx);

            if (currentMacdValue == null) {
                return false;
            }

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setMacdValue(currentMacdValue.getValue());

                saveLog(tradeConditionLog);
            }

            evaluation = expression.test(currentMacdValue);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }
        return evaluation;
    }

    public void setExpression(Predicate<MacdValue> expression) {
        this.expression = expression;
    }
}
