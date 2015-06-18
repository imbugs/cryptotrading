package com.crypto.tradecondition.evaluator;

import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeConditionLog;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is positive at a given index
 * <p/>
 * Created by Jan Wicherink on 12-6-15.
 */
@Stateful
public class MacdPositive extends Evaluator implements Serializable {

    private static final long serialVersionUID = 3523938793517562905L;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TradeConditionLogDao tradeConditionLogDao;

    /**
     * Defauult constructor.
     */
    public MacdPositive() {

    }

    private MacdValue getMacdValue(final Integer index) {
        return cryptocoinTrendDao.getMacdValue(index, getTradeCondition().getMacd(), this.getTrading().getTradePair());
    }

    /**
     * Checks the condition if a Macd is positive at a given index
     *
     * @return true when the condition is true
     */
    public Boolean evaluate() {

        Integer offset;
        Integer period;
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

            // Macd must be positive
            Predicate<MacdValue> macdMustBePositive = (p) -> {
                return p.getValue() > 0;
            };

            evaluation = macdMustBePositive.test(currentMacdValue);

            if (!evaluateExpression(evaluation, getTradeCondition().getLogicalOperator())) {
                return evaluation;
            }
        }
        return evaluation;
    }
}
