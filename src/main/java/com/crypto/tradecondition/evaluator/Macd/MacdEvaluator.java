package com.crypto.tradecondition.evaluator.Macd;

import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeConditionLog;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Evaluator;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is positive at a given index
 *
 * Created by Jan Wicherink on 12-6-15.
 */
@Stateful
public class MacdEvaluator extends Evaluator implements ConditionEvaluator, Serializable {

    private static final long serialVersionUID = 3523938793517562905L;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TradeConditionLogDao tradeConditionLogDao;

    private Predicate<MacdValue> expression;

    /**
     * Default constructor
     */
    public MacdEvaluator() {
    }

    private MacdValue getMacdValue(final Integer index) {
        return cryptocoinTrendDao.getMacdValue(index, getTradeCondition().getMacd(), this.getTrading().getTradePair());
    }

    /**
     * Checks the condition of a Macd at a given index is trye for all values (AND condition), or just for any one of the values (OR condition)
     *
     * @return true when the condition is true
     */
    public Boolean evaluate() {

        Boolean evaluation = false;

        for (Integer indx = getIndex() - getTradeCondition().getPeriod() + 1; indx <= getIndex(); indx++) {

            MacdValue currentMacdValue = getMacdValue(indx);

            if (currentMacdValue == null) {
                return false;
            }

            if (getLog()) {
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), indx, getTradeCondition(), getTrading());
                tradeConditionLog.setMacdValue(currentMacdValue.getValue());

                this.tradeConditionLogDao.persist(tradeConditionLog);
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
