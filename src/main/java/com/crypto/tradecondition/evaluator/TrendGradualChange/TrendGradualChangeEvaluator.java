package com.crypto.tradecondition.evaluator.TrendGradualChange;

import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradeConditionLog;
import com.crypto.entities.TrendValue;
import com.crypto.enums.LogicalOperator;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.function.BiPredicate;

/**
 * Evaluates if a trend grows or shrinks at a given percentage rate
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
@Stateful
public abstract class TrendGradualChangeEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 3523938793517562905L;

    private BiPredicate<TradeCondition, Float> expression;

    /**
     * Default constructor
     */
    public TrendGradualChangeEvaluator() {
    }


    /**
     * Checks the condition of a trend value  at a given index is trye for all values (AND condition), or just for any one of the values (OR condition)
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

            TrendValue previousValue = getTrendValue(indx - 1);
            TrendValue currentValue = getTrendValue(indx);

            if (previousValue == null || currentValue == null) {
                return false;
            }

            if (previousValue.getValue().equals(0F)) {
                return true;
            }

            final Float growthPercentage = ((currentValue.getValue() - previousValue.getValue()) / Math.abs(previousValue.getValue())) * 100;

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setTrendValue(currentValue.getValue());
                tradeConditionLog.setPercentage(growthPercentage);

                getTradeConditionLogDao().persist(tradeConditionLog);
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
