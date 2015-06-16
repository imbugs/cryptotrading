package com.crypto.tradecondition;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
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

    private Trading trading;

    private Boolean log = false;

    public ConditionDispatcher(final Integer index, final Trading trading, final TradeCondition tradeCondition, final Boolean log) {

        this.index = index;
        this.trading = trading;
        this.tradeCondition = tradeCondition;
        this.log = log;
    }

    public Boolean evaluate () {

        Boolean evaluation = false;

        switch (this.tradeCondition.getTradeConditionType().getName()) {
            case "MACD_POSITIVE":

                macdPositive.setIndex(index);
                macdPositive.setTrading(trading);
                macdPositive.setTradeCondition(tradeCondition);

                evaluation =  macdPositive.evaluate();
                break;
        }
        return evaluation;
    }
}
