package com.crypto.tradecondition.evaluator.TrendChange;

import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeConditionLog;
import com.crypto.entities.TrendValue;
import com.crypto.enums.LogicalOperator;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Evaluates a trend change
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
@Stateful
public class TrendChangeEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 1;

    private Predicate<TrendValue> expression;

    /**
     * Default constructor
     */
    public TrendChangeEvaluator() {
    }

    @Override
    public Boolean evaluate() {
        Boolean evaluation = false;

        // Force AND logical operator, all values must comply with expression
        this.getTradeCondition().setLogicalOperator(LogicalOperator.AND);

        // Check if the expression is true for values in range   indx-period-1 to indx
        Integer start = getIndex() - getTradeCondition().getPeriod() + 1;
        Integer end = getIndex();

        for (Integer indx = start; indx <= end; indx++) {

            TrendValue currentTrendValue = getTrendValue(indx);

            if (currentTrendValue == null) {
                return false;
            }

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setTrendValue(currentTrendValue.getValue());

                getTradeConditionLogDao().persist(tradeConditionLog);
            }

            evaluation = expression.test(currentTrendValue);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }

        // Check if the expression is false for values in range indx- (2 * period) - 1 to indx - period
        start = getIndex() - (2 * getTradeCondition().getPeriod()) + 1;
        end = getIndex() - getTradeCondition().getPeriod();

        for (Integer indx = start; indx <= end; indx++) {

            TrendValue currentTrendValue = getTrendValue(indx);

            if (currentTrendValue == null) {
                return false;
            }

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setTrendValue(currentTrendValue.getValue());

                getTradeConditionLogDao().persist(tradeConditionLog);
            }

            // Negate the expression
            evaluation = expression.negate().test(currentTrendValue);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }
        return evaluation;
    }

    public void setExpression(Predicate<TrendValue> expression) {
        this.expression = expression;
    }
}
