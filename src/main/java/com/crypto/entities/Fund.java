package com.crypto.entities;

import javax.persistence.*;

/**
 * The fund represents the funding of a trading
 * Created by Jan Wicherink on 16-4-15.
 */
@Entity
@Table(name="FUNDS")
public class Fund {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "TRADE_PAIR_ID", nullable=false, updatable=false)
    private TradePair tradepair;

    @Column(name="COINS")
    private Float coins;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "CURRENCY", nullable=false, updatable=false)
    private Currency currency;

    public Fund(TradePair tradepair, Float coins, Currency currency) {
        this.tradepair = tradepair;
        this.coins = coins;
        this.currency = currency;
    }

    public Fund() {
    }

    public void addCoins(final Float coinsToBeAdded)
    {
        this.coins = this.coins + coinsToBeAdded;
    }

    public void subtractCoins(final Float coinsToBeSubtracted)
    {
        this.coins = this.coins - coinsToBeSubtracted;
    }

    public TradePair getTradepair() {
        return tradepair;
    }

    public void setTradepair(TradePair tradepair) {
        this.tradepair = tradepair;
    }

    public Float getCoins() {
        return coins;
    }

    public void setCoins(Float coins) {
        this.coins = coins;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
