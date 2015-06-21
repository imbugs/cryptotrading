package com.crypto.entities;

import javax.persistence.*;

/**
 * Trade rule for trading entity
 * Created by jan on 21-6-15.
 */
@Entity
@Table(name="TRADE_RULES_FOR_TRADING")
public class TradeRuleForTrading {

    @javax.persistence.Id
    @Column(name="ID")
    private Integer Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADE_RULE_ID",  nullable = false, updatable = false)
    private TradeRule tradeRule;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID",  nullable = false, updatable = false)
    private Trading trading;
}
