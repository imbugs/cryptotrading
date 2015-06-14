package com.crypto.tradecondition.evaluator;

import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradePair;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Evaluates if a Macd is positive at a given index
 *
 * Created by Jan WIcherink on 12-6-15.
 */
@Stateless
public class MacdPositive implements ConditionEvaluator {

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    private Integer index;

    private TradeCondition tradeCondition;

    private TradePair tradePair;

    /**
     * Default constructor
     */
    public MacdPositive () {
    }


    /**
     * Checks the condition if a Macd is positive at a given index
     * @return true when the condition is true
     */
    public Boolean evaluate () {

        Integer offset;
        Integer period;


        if (this.tradeCondition.getPrevious()) {
            offset = 0;
            period = this.tradeCondition.getPeriod() + 1;
        }
        else {
            offset = 1;
            period = this.tradeCondition.getPeriod();
        }

        for (Integer indx = index+1; index< index + offset; indx++) {

            MacdValue macdValue = cryptocoinTrendDao.getMacdValue(index,tradeCondition.getMacd(), tradePair);
        }

        return false;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public TradeCondition getTradeCondition() {
        return tradeCondition;
    }

    public void setTradeCondition(TradeCondition tradeCondition) {
        this.tradeCondition = tradeCondition;
    }

    public TradePair getTradePair() {
        return tradePair;
    }

    public void setTradePair(TradePair tradePair) {
        this.tradePair = tradePair;
    }
}

