package com.crypto.tradecondition.evaluator;

import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.entities.*;
import com.crypto.enums.LogicalOperator;

import javax.ejb.EJB;

import static com.crypto.enums.LogicalOperator.*;

/**
 * Evaluator, evaluates a condition.
 *
 * Created by Jan Wicherink on 14-6-15.
 */
public class Evaluator {
    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TradeConditionLogDao tradeConditionLogDao;

    private Integer index;

    private TradeCondition tradeCondition;

    private Trading trading;

    private Boolean log;


    /**
     * Default constructor
     */
    public Evaluator () {

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
        return trading.getTradePair();
    }

    public Boolean getLog() {
        return log;
    }

    public void setLog(Boolean log) {
        this.log = log;
    }

    /**
     * Get a Macd value
     *
     * @return the macd value
     */
    public MacdValue getMacdValue () {
        return cryptocoinTrendDao.getMacdValue(index,tradeCondition.getMacd(), this.getTradePair());
    }


    /**
     * Evaluate an expression with multiple logical operators/
     *
     * @param expression the expression
     * @param logicalOperator the logical operator
     * @return the evaluation
     */
    public Boolean evaluateExpression (final Boolean expression, final LogicalOperator logicalOperator) {
        switch (logicalOperator) {
            case AND : return (expression);
            case OR :  return (! expression);
        }
        return false;
    }

    public Trading getTrading() {
        return trading;
    }

    public TradeConditionLogDao getTradeConditionLogDao() {
        return tradeConditionLogDao;
    }
}
