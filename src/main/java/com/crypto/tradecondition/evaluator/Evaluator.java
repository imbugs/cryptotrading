package com.crypto.tradecondition.evaluator;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.entities.*;
import com.crypto.enums.LogicalOperator;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.io.Serializable;

import static com.crypto.enums.LogicalOperator.*;

/**
 * Evaluator, evaluates a condition.
 * <p/>
 * Created by Jan Wicherink on 14-6-15.
 */
@Stateful
public class Evaluator {

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TradeConditionLogDao tradeConditionLogDao;


    private Integer index;

    private Trading trading;

    private TradeCondition tradeCondition;

    private Boolean log = false;

    /**
     * Default constructor
     */
    public Evaluator() {

    }

    /**
     * Get cryptocoin history at given index.
     *
     * @param index the index
     * @return the cryptocoin history at a given index.
     */
    public CryptocoinHistory getCryptoCoinHistory(final Integer index) {
        return cryptocoinHistoryDao.getCryptoCoinHistoryByIndex(getTrading().getTradePair(), index);
    }

    /**
     * Get a Macd value.
     *
     * @param index the index of the macd value.
     * @return the macd value.
     */
    public MacdValue getMacdValue(final Integer index) {
        return cryptocoinTrendDao.getMacdValue(index, getTradeCondition().getMacd(), this.getTrading().getTradePair());
    }

    /**
     * Get a trend value.
     *
     * @param index the index of the trend value.
     * @return the trend value.
     */
    public TrendValue getTrendValue(final Integer index) {
        return cryptocoinTrendDao.getTrendValue(index, getTradeCondition().getTrend(), this.getTrading().getTradePair());
    }

    /**
     * Evaluate an expression with multiple logical operators, in case and AND operator is used, all values in the expression
     * must comply to the expression to make the expression true.
     * <p/>
     * In case an OR operator is used, only one value in the range of values must comply to the
     * expression to make the expression true.
     *
     * @param expression      the expression
     * @param logicalOperator the logical operator
     * @return the evaluation
     */
    public Boolean evaluateExpression(final Boolean expression, final LogicalOperator logicalOperator) {
        switch (logicalOperator) {
            case AND:
                return (expression);
            case OR:
                return (!expression);
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

        return this.tradeCondition;
    }

    public void setTradeCondition(final TradeCondition tradeCondition) {
        this.tradeCondition = tradeCondition;
    }

    public Boolean getLog() {
        return log;
    }

    public void setLog(Boolean log) {
        this.log = log;
    }

    public Trading getTrading() {
        return this.trading;
    }


    public void setTrading(Trading trading) {
        this.trading = trading;
    }

    public TradeConditionLogDao getTradeConditionLogDao() {
        return tradeConditionLogDao;
    }
}
