package com.crypto.tradecondition.evaluator.MacdChange;

import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeConditionLog;
import com.crypto.enums.LogicalOperator;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is from positive to negative or visa versa, ie goes through zero.
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
@Stateful
public class MacdChangeEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 3523938793517562905L;

    private BiPredicate<MacdValue, MacdValue> expression;

    /**
     * Default constructor
     */
    public MacdChangeEvaluator() {
    }


    /**
     * Checks the condition of a Macd at a given index is trye for all values (AND condition), or just for any one of the values (OR condition)
     *
     * @return true when the condition is true
     */
    public Boolean evaluate() {

        Boolean evaluation = false;

        // Check if the expression is true for values in range indx-period-1 to indx
        final Integer start = getIndex() - getTradeCondition().getPeriod() + 1;
        final Integer end = getIndex();

        // Force OR condition, expression must be true for at least one macd value
        getTradeCondition().setLogicalOperator(LogicalOperator.OR);

        for (Integer indx = start; indx <= end; indx++) {

            MacdValue previousMacdValue = getMacdValue(indx-1);
            MacdValue currentMacdValue = getMacdValue(indx);

            if (previousMacdValue == null || currentMacdValue == null) {
                return false;
            }

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setMacdValue(currentMacdValue.getValue());

                getTradeConditionLogDao().persist(tradeConditionLog);
            }

            evaluation = expression.test(previousMacdValue, currentMacdValue);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }
        return evaluation;
    }

    public void setExpression(BiPredicate<MacdValue, MacdValue> expression) {
        this.expression = expression;
    }
}
