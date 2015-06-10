package com.crypto.tradecondition;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.TradeCondition;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Creates a ConditionEvaluator
 * <p/>
 * Created by jan on 10-6-15.
 */
@Stateless
public class ConditionEvaluatorFactory {

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;


    private TradeCondition tradeCondition;

    public ConditionEvaluatorFactory(final TradeCondition tradeCondition) {

        this.tradeCondition = tradeCondition;
    }


    /**
     * Checks the condition if a Macd is positive at a given index
     * @param index the index
     * @param tradeCondition the tradecondition
     * @return true when the condition is true
     */
    private static Boolean MacdPositive(final Integer index, final TradeCondition tradeCondition) {

        return false;
    }

    public ConditionEvaluator getConditionEvaluator() {

        ConditionEvaluator evaluator = null;

        switch (tradeCondition.getTradeConditionType().getName()) {
            case "MACD_POSITIVE":
                evaluator = (index, tradeCondition1) -> ConditionEvaluatorFactory.MacdPositive(index, tradeCondition);
                break;
        }
        return evaluator;
    }
}
