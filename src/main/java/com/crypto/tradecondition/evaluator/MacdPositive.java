package com.crypto.tradecondition.evaluator;

import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeConditionLog;

import javax.ejb.Stateless;
import java.util.function.Predicate;

/**
 * Evaluates if a Macd is positive at a given index
 * <p/>
 * Created by Jan WIcherink on 12-6-15.
 */
@Stateless
public class MacdPositive extends Evaluator implements ConditionEvaluator {

    /**
     * Default constructor
     */
    public MacdPositive() {
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


        if (getTradeCondition().getPrevious()) {
            offset = 0;
            period = getTradeCondition().getPeriod() + 1;
        } else {
            offset = 1;
            period = getTradeCondition().getPeriod();
        }

        for (Integer indx = getIndex() - period + 1; getIndex() < getIndex() + offset; indx++) {

            MacdValue currentMacdValue = getMacdValue();

            if (currentMacdValue == null) {
                return false;
            }

            if (getLog()) {
                // TODO:  change getIndex in getIndx()
                TradeConditionLog tradeConditionLog = new TradeConditionLog(getIndex(), getIndex(), getTradeCondition(), getTrading());
                tradeConditionLog.setMacdValue(currentMacdValue.getValue());

                getTradeConditionLogDao().persist(tradeConditionLog);
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

