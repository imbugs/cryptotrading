package com.crypto.entities;

import com.crypto.entities.pkey.SignalPk;
import com.crypto.enums.MarketTrend;

import javax.persistence.*;

/**
 * Signal, a signal represents a BEAR or BULL situation
 * Created by Jan Wicherink on 29-4-2015.
 */
@Entity
@Table (name="SIGNALS")
public class Signal {

    @EmbeddedId
    private SignalPk pk;

    @Column(name="TRADE_SIGNAL")
    @Enumerated(EnumType.STRING)
    private MarketTrend tradeSignal;

    @ManyToOne
    @JoinColumn (name="TRADERULE_ID")
    private TradeRule tradeRule;

    /**
     * The signal for a bull or bear situation
     * @param tradeSignal the signal type, bull or bear
     * @param indx the index at which the signal is raised
     * @param tradeRule the traderule responsible for this signal
     * @param trading the util applicable for this signal
     */
    public Signal(MarketTrend tradeSignal, Integer indx, TradeRule tradeRule, Trading trading) {
        this.pk = new SignalPk(indx,trading);
        this.tradeSignal = tradeSignal;
        this.tradeRule = tradeRule;
    }

    /**
     * Default constructor
     */
    public Signal() {

    }

    public MarketTrend getTradeSignal() {
        return tradeSignal;
    }

    public Integer getIndx() {
        return pk.getIndex();
    }

    public TradeRule getTradeRule() {
        return tradeRule;
    }

    public Trading getTrading() {
        return pk.getTrading();
    }

    public SignalPk getPk() {
        return pk;
    }
}
