package com.crypto.tradecondition.evaluator.MacdGradualChange;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradeConditionLog;
import com.crypto.enums.LogicalOperator;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import java.io.Serializable;
import java.util.function.BiPredicate;

/**
 * Evaluates if a Macd grows or shrinks at a given percentage rate
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
public abstract class MacdGradualChangeEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 3523938793517562905L;

    private BiPredicate<TradeCondition, Float> expression;

    /**
     * Constructor
     * @param signalBulkDataHandler the signal data provider
     */
    public MacdGradualChangeEvaluator(final SignalBulkDataHandler signalBulkDataHandler) {
        super (signalBulkDataHandler);
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

        // Force OR condition, expression must be true for at least one macd value
        getTradeCondition().setLogicalOperator(LogicalOperator.OR);

        for (Integer indx = start; indx <= end; indx++) {

            MacdValue previousMacdValue = getMacdValue(indx - 1);
            MacdValue currentMacdValue = getMacdValue(indx);


            if (previousMacdValue == null || currentMacdValue == null) {
                return false;
            }
            if (previousMacdValue.getValue().equals(0F)) {
               return true;
            }

            final Float growthPercentage = ((currentMacdValue.getValue() - previousMacdValue.getValue()) / Math.abs (previousMacdValue.getValue())) * 100;

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setMacdValue(currentMacdValue.getValue());
                tradeConditionLog.setPercentage(growthPercentage);

                saveLog(tradeConditionLog);
            }

            evaluation = expression.test(getTradeCondition(), growthPercentage);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }
        return evaluation;
    }

    public void setExpression(BiPredicate<TradeCondition, Float> expression) {
        this.expression = expression;
    }
}
