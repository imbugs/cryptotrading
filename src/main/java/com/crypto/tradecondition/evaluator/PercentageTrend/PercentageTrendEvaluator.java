package com.crypto.tradecondition.evaluator.PercentageTrend;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradeConditionLog;
import com.crypto.entities.TrendValue;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.function.BiPredicate;

/**
 * Evaluates the crypto coin exchange rate against a trend
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
@Stateful
public abstract class PercentageTrendEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 1;

    private BiPredicate<CryptocoinHistory, Float> expression;

    /**
     * Default constructor
     */
    public PercentageTrendEvaluator() {
    }

    @Override
    public Boolean evaluate() {

        verifyImplementation();

        Boolean evaluation = false;

        // Check if the expression is true for values in range indx-period-1 to indx
        final Integer start = getIndex() - getTradeCondition().getPeriod() + 1;
        final Integer end = getIndex();

        for (Integer indx = start; indx <= end; indx++) {

            final TrendValue currentTrendValue = getTrendValue(indx);
            final Float trendLimit = currentTrendValue.getValue() * getTradeCondition().getPercentage() / 100;

            final CryptocoinHistory currentCryptocoinHistory = getCryptoCoinHistory(indx);

            if (currentTrendValue == null) {
                return false;
            }

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setTrendValue(currentTrendValue.getValue());
                tradeConditionLog.setExchangeRate(currentCryptocoinHistory.getClose());

                getTradeConditionLogDao().persist(tradeConditionLog);
            }

            evaluation = expression.test(currentCryptocoinHistory, trendLimit);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }

        return evaluation;
    }

    public void setExpression(BiPredicate<CryptocoinHistory, Float> expression) {
        this.expression = expression;
    }
}