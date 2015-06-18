package com.crypto.tradecondition.evaluator;

import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TradeConditionLogDao;
import com.crypto.entities.*;
import com.crypto.enums.LogicalOperator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.io.Serializable;

import static com.crypto.enums.LogicalOperator.*;

/**
 * Evaluator, evaluates a condition.
 *
 * Created by Jan Wicherink on 14-6-15.
 */
public class Evaluator{

    private Integer index;

    private Trading trading;

    private TradeCondition tradeCondition;

    private Boolean log = false;

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
        return this.trading;
    }

    public void setTrading(Trading trading) {
        this.trading = trading;
    }
}
