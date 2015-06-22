package com.crypto.tradecondition.evaluator.Trend;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradeConditionLog;
import com.crypto.entities.TrendValue;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import java.io.Serializable;
import java.util.function.BiPredicate;

/**
 * Evaluates the crypto coin exchange rate against a trend
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
public abstract class TrendEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 1;

    private BiPredicate<CryptocoinHistory, TrendValue> expression;

    /**
     * Constructor
     * @param signalBulkDataHandler the signal data provider.
     */
    public TrendEvaluator(final SignalBulkDataHandler signalBulkDataHandler) {
        super (signalBulkDataHandler);
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
            final CryptocoinHistory currentCryptocoinHistory = getCryptoCoinHistory(indx);

            if (currentTrendValue == null) {
                return false;
            }

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setTrendValue(currentTrendValue.getValue());
                tradeConditionLog.setExchangeRate(currentCryptocoinHistory.getClose());

                saveLog(tradeConditionLog);
            }

            evaluation = expression.test(currentCryptocoinHistory, currentTrendValue);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }

        return evaluation;
    }

    public void setExpression(BiPredicate<CryptocoinHistory, TrendValue> expression) {
        this.expression = expression;
    }
}