package com.crypto.entities;

import com.crypto.entities.MarketTrend;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;

import javax.persistence.*;

/**
 * Signal, a signal represents a BEAR or BULL situation
 * Created by Jan Wicherink on 29-4-2015.
 */
@Entity
@Table (name="SIGNALS")
public class Signal {

    @Id
    @GeneratedValue
    @Column (name="ID")
    private Integer id;

    @Column (name="INDX")
    private Integer indx;

    @Column(name="SIGNAL")
    @Enumerated(EnumType.STRING)
    private MarketTrend signal;

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
    public Signal(Integer id, MarketTrend signal, Integer indx, TradeRule tradeRule, Trading trading) {
        this.id = id;
        this.signal = signal;
        this.indx = indx;
        this.tradeRule = tradeRule;
        this.trading = trading;
    }

    /**
     * Default constructor
     */
    public Signal() {

    }

    public Integer getId() {
        return id;
    }

    public MarketTrend getSignal() {
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
