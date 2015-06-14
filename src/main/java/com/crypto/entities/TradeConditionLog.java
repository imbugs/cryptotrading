package com.crypto.entities;

import com.crypto.enums.TradeConditionExpression;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Trade condition log, contains the evaluation of a trade condition at a given index for a given trading.
 * The expression property determines if the trade condition has become TRUE or is FALSE
 *
 * Created by Jan Wicherink on 7-5-15.
 */
@Entity
@Table(name="TRADE_CONDITION_LOG")
public class TradeConditionLog {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID", nullable = false)
    private Trading trading;

    @Column (name="INDX1")
    private Integer index;

    @Column(name="INDX2")
    private Integer indx;

    @Column(name="TIMESTAMP")
    private Timestamp timestamp;

    @Column(name="EXPRESSION")
    @Enumerated(EnumType.STRING)
    private TradeConditionExpression expression;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADE_CONDITION_ID", nullable = false)
    private TradeCondition tradeCondition;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TYPE", nullable = false)
    private TradeConditionType tradeConditionType;

    @Column(name="MACD_VALUE")
    private Float macdValue;

    @Column(name="TREND_VALUE")
    private Float trendValue;

    @Column(name="DELTA_VALUE")
    private Float deltavalue;

    @Column(name="EXCHANGE_RATE")
    private Float exchangeRate;

    @Column(name="REFERENCE_MACD_VALUE")
    private Float referenceMacdValue;

    @Column(name="REFERENCE_TREND_VALUE")
    private Float referenceTrendValue;

    @Column(name="PERCENTAGE")
    private Float percentage;

    @Column(name="THRESHOLD")
    private Float threshold;

    /**
     * Constructor
     * @param trading the tradling of this log
     * @param index the primary index of this log
     * @param indx the secondary index of this log
     * @param timestamp the timestamp op this log
     * @param expression the expression evaluation
     * @param tradeCondition tradecondition
     * @param tradeConditionType tradeconidtion type of this log
     * @param macdValue the macd value
     * @param trendValue the trend value
     * @param deltavalue delta value
     * @param exchangeRate exchange rate
     * @param referenceMacdValue reference macd value
     * @param referenceTrendValue refenrecen trend value
     * @param percentage percentage used
     * @param threshold threshold used
     */
    public TradeConditionLog(Trading trading, Integer index, Integer indx, Timestamp timestamp, TradeConditionExpression expression, TradeCondition tradeCondition, TradeConditionType tradeConditionType, Float macdValue, Float trendValue, Float deltavalue, Float exchangeRate, Float referenceMacdValue, Float referenceTrendValue, Float percentage, Float threshold) {
        this.trading = trading;
        this.index = index;
        this.indx = indx;
        this.timestamp = timestamp;
        this.expression = expression;
        this.tradeCondition = tradeCondition;
        this.tradeConditionType = tradeConditionType;
        this.macdValue = macdValue;
        this.trendValue = trendValue;
        this.deltavalue = deltavalue;
        this.exchangeRate = exchangeRate;
        this.referenceMacdValue = referenceMacdValue;
        this.referenceTrendValue = referenceTrendValue;
        this.percentage = percentage;
        this.threshold = threshold;
    }


    /**
     * Constructor
     * @param index the primary index of this log
     * @param indx the secondary index of this log
     * @param  tradeCondition the trade condition
     * @param trading the tradling of this log
     */
    public TradeConditionLog(Integer index, Integer indx,TradeCondition tradeCondition, Trading trading) {
        this.trading = trading;
        this.index = index;
        this.indx = indx;
        this.tradeCondition = tradeCondition;
    }


    /**
     * Default constructor
     */
    public TradeConditionLog () {

    }

    public Trading getTrading() {
        return trading;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getIndx() {
        return indx;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public TradeConditionExpression getExpression() {
        return expression;
    }

    public Float getMacdValue() {
        return macdValue;
    }

    public Float getTrendValue() {
        return trendValue;
    }

    public Float getDeltavalue() {
        return deltavalue;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public Float getReferenceMacdValue() {
        return referenceMacdValue;
    }

    public Float getReferenceTrendValue() {
        return referenceTrendValue;
    }

    public Float getPercentage() {
        return percentage;
    }

    public Float getThreshold() {
        return threshold;
    }

    public TradeCondition getTradeCondition() {
        return tradeCondition;
    }

    public TradeConditionType getTradeConditionType() {
        return tradeConditionType;
    }

    public void setMacdValue(Float macdValue) {
        this.macdValue = macdValue;
    }
}