package com.crypto.entities;

import com.crypto.enums.LogicalOperator;
import com.crypto.enums.TradeConditionType;

import javax.persistence.*;

/**
 * A trade condition
 * <p/>
 * Created by Jan Wicherink on 6-5-15.
 */
@Entity
@Table(name = "TRADE_CONDITIONS")
public class TradeCondition {

    @Id
    @Column(name = "ID")
    private Integer Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRADE_RULE_ID", nullable = false)
    private TradeRule tradeRule;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private TradeConditionType tradeConditionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MACD_ID", nullable = true)
    private Macd macd;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TREND_ID", nullable = true)
    private Trend trend;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REFERENCE_MACD_ID", nullable = true)
    private Macd reference_macd;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REFERENCE_TREND_ID", nullable = true)
    private Trend reference_trend;

    @Column(name = "PERCENTAGE")
    private Float percentage;

    @Column(name = "PERCENTAGE_LIMIT")
    private Float percentage_limit;

    @Column(name = "RATE")
    private Float rate;

    @Column(name = "PERIOD")
    private Integer period;

    @Column(name = "LOGICAL_OPERATOR")
    @Enumerated(EnumType.STRING)
    private LogicalOperator logicalOperator;

    @Column(name = "ENABLED")
    private Boolean enabled;

    /**
     * Trade condition
     *
     * @param id                 the identifier
     * @param tradeRule          the traderule to which the tradecondition belongs
     * @param tradeConditionType tradecondition type
     * @param macd               reference to a macd
     * @param trend              reference to a trend
     * @param reference_macd     reference to a reference macd
     * @param reference_trend    reference to a reference trend
     * @param percentage         percentage
     * @param percentage_limit   percentage limit
     * @param rate               util bitcoin rate
     * @param period             period to which the tradecondition belongs
     * @param logicalOperator    logical operator
     * @param enabled            true when the trade condition is active
     */
    public TradeCondition(Integer id, TradeRule tradeRule, TradeConditionType tradeConditionType, Macd macd, Trend trend, Macd reference_macd, Trend reference_trend, Float percentage, Float percentage_limit, Float rate, Integer period, LogicalOperator logicalOperator, Boolean enabled) {
        Id = id;
        this.tradeRule = tradeRule;
        this.tradeConditionType = tradeConditionType;
        this.macd = macd;
        this.trend = trend;
        this.reference_macd = reference_macd;
        this.reference_trend = reference_trend;
        this.percentage = percentage;
        this.percentage_limit = percentage_limit;
        this.rate = rate;
        this.period = period;
        this.logicalOperator = logicalOperator;
        this.enabled = enabled;
    }

    /**
     * Default constructor
     */
    public TradeCondition() {

    }

    public Integer getId() {
        return Id;
    }

    public TradeRule getTradeRule() {
        return tradeRule;
    }

    public TradeConditionType getTradeConditionType() {
        return tradeConditionType;
    }

    public Macd getMacd() {
        return macd;
    }

    public Trend getTrend() {
        return trend;
    }

    public Macd getReference_macd() {
        return reference_macd;
    }

    public Trend getReference_trend() {
        return reference_trend;
    }

    public Float getPercentage() {
        return percentage;
    }

    public Float getPercentage_limit() {
        return percentage_limit;
    }

    public Float getRate() {
        return rate;
    }

    public Integer getPeriod() {
        return period;
    }

      public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public void setLogicalOperator(LogicalOperator logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
}
