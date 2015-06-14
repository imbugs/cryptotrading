package com.crypto.tradecondition;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradePair;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.MacdPositive;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Dispatches a condition evaluation
 * <p/>
 * Created by jan on 10-6-15.
 */
public class ConditionDispatcher {

    @EJB
    MacdPositive macdPositive;

    private TradeCondition tradeCondition;

    private Integer index;

    private TradePair tradePair;

    public ConditionDispatcher(final Integer index, final TradeCondition tradeCondition, final TradePair tradePair) {

        this.index = index;
        this.tradeCondition = tradeCondition;
        this.tradePair = tradePair;
    }

    public Boolean evaluate () {

        Boolean evaluation = false;

        switch (tradeCondition.getTradeConditionType().getName()) {
            case "MACD_POSITIVE":

                macdPositive.setIndex(index);
                macdPositive.setTradePair(tradePair);
                macdPositive.setTradeCondition(tradeCondition);

                evaluation =  macdPositive.evaluate();
                break;
        }

        return evaluation;
    }
}
