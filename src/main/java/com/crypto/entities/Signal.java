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

    @Column(name="SIGNAL")
    @Enumerated(EnumType.STRING)
    private MarketTrend signal;

    @ManyToOne
    @JoinColumn (name="TRADERULE_ID")
    private TradeRule tradeRule;

    /**
     * The signal for a bull or bear situation
     * @param signal the signal type, bull or bear
     * @param indx the index at which the signal is raised
     * @param tradeRule the traderule responsible for this signal
     * @param trading the trading applicable for this signal
     */
    public Signal(MarketTrend signal, Integer indx, TradeRule tradeRule, Trading trading) {
        this.pk = new SignalPk(indx,trading);
        this.signal = signal;
    }

    /**
     * Default constructor
     */
    public Signal() {

    }

    public MarketTrend getSignal() {
        return signal;
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
}
