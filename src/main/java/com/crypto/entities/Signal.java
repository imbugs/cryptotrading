package com.crypto.entities;

import javax.persistence.*;

/**
 * Created by Jan Wicherink on 29-4-2015.
 */
@Entity
public class Signal {

    @Id
    @GeneratedValue
    private Integer id;

    @Column (name="INDX")
    private Integer indx;

    @Column(name="SIGNAL")
    private Market signal;

    @ManyToOne
    @JoinColumn (name="TRADERULE_ID")
    private TradeRule tradeRule;

    @ManyToOne
    @JoinColumn (name="TRADING_ID")
    private Trading trading;

    /**
     * The signal for a bull or bear situation
     * @param id the id of the signal
     * @param signal the signal type, bull or bear
     * @param indx the index at which the signal is raised
     * @param tradeRule the traderule responsible for this signal
     * @param trading the trading applicable for this signal
     */
    public Signal(Integer id, Market signal, Integer indx, TradeRule tradeRule, Trading trading) {
        this.id = id;
        this.signal = signal;
        this.indx = indx;
        this.tradeRule = tradeRule;
        this.trading = trading;
    }

    public Integer getId() {
        return id;
    }

    public Market getSignal() {
        return signal;
    }

    public Integer getIndx() {
        return indx;
    }

    public TradeRule getTradeRule() {
        return tradeRule;
    }

    public Trading getTrading() {
        return trading;
    }
}
