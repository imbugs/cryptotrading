package com.crypto.tradecondition;

import com.crypto.entities.TradeCondition;
import com.crypto.entities.Trading;
import com.crypto.tradecondition.evaluator.Macd.MacdNegative;
import com.crypto.tradecondition.evaluator.Macd.MacdPositive;

import javax.ejb.EJB;

/**
 * Dispatches a condition evaluation
 * <p/>
 * Created by jan on 10-6-15.
 */
public class ConditionDispatcher {

    @EJB
    private MacdPositive macdPositive;

    @EJB
    private MacdNegative macdNegative;

    private TradeCondition tradeCondition;

    private Integer index;

    private Trading trading;

    private Boolean log = false;

    public ConditionDispatcher(final Integer index, final Trading trading, final TradeCondition tradeCondition, final Boolean log) {

        this.index = index;
        this.trading = trading;
        this.tradeCondition = tradeCondition;
        this.log = log;
    }

    /**
     * Evaluate a trade condition
     *
      * @return true when the condition is true at a given index, false otherwise
     */
    public Boolean evaluate () {

        Boolean evaluation = false;

        macdPositive.setIndex(index);
        macdPositive.setTrading(trading);
        macdPositive.setTradeCondition(tradeCondition);

        switch (this.tradeCondition.getTradeConditionType().getName()) {
            case "MACD_POSITIVE":

                evaluation =  macdPositive.evaluate();
                break;

            case "MACD_NEGATIVE":

                evaluation =  macdNegative.evaluate();
                break;
        }
        return evaluation;
    }
}
