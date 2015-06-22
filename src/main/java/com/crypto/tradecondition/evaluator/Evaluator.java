package com.crypto.tradecondition.evaluator;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.*;
import com.crypto.enums.LogicalOperator;

/**
 * Evaluator, evaluates a condition.
 * <p/>
 * Created by Jan Wicherink on 14-6-15.
 */
public abstract class Evaluator implements ConditionEvaluator {

    private SignalBulkDataHandler signalBulkDataHandler;

    private Integer index;

    private Trading trading;

    private TradeCondition tradeCondition;

    private Boolean log = false;

    /**
     * Default constructor.
     */
    public Evaluator() {

    }

    /**
     * Default constructor
     *
     * @param signalBulkDataHandler the signal calculator data provider/handler
     */
    public Evaluator(final SignalBulkDataHandler signalBulkDataHandler) {
        this.signalBulkDataHandler = signalBulkDataHandler;
    }

    /**
     * Verifies if the correct trade condition is used with the correct trade condition evaluator.
     */
    public void verifyImplementation() {
        if (!getTradeCondition().getTradeConditionType().equals(getImplementedTradeConditionType())) {

            throw new RuntimeException("Mismatch between tradecondition and implementation of evaluator:" + getTradeCondition().getTradeConditionType().toString());
        }
    }

    /**
     * Get cryptocoin history at given index.
     *
     * @param index the index
     * @return the cryptocoin history at a given index.
     */
    public CryptocoinHistory getCryptoCoinHistory(final Integer index) {

        return signalBulkDataHandler.getValue(index);
    }

    /**
     * Get a Macd value.
     *
     * @param index the index of the macd value.
     * @return the macd value.
     */
    public MacdValue getMacdValue(final Integer index) {

        return signalBulkDataHandler.getMacdValue(index);
    }

    /**
     * Get a trend value.
     *
     * @param index the index of the trend value.
     * @return the trend value.
     */
    public TrendValue getTrendValue(final Integer index) {
        return signalBulkDataHandler.getTrendValue(index);
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

    /**
     * Saves a trade condition log
     *
     * @param tradeConditionLog the trade condition log.
     */
    public void saveLog(TradeConditionLog tradeConditionLog) {
        signalBulkDataHandler.saveLog(tradeConditionLog);
    }
}
