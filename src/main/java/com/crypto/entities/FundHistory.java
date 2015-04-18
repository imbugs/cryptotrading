package com.crypto.entities;

import javax.persistence.*;

/**
 * Created by root on 18-4-15.
 */
@Entity
@Table(name="FUND_HISTORY")
public class FundHistory {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID",  nullable = false, updatable = false)
    private Trading trading;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "CURRENCY", nullable=false, updatable=false)
    private Currency currency;

    @Column(name = "COINS")
    private Float coins;

    /**
     * Constructor
     * @param trading the trading
     * @param currency the currency of the fund history
     * @param coins the coins of the fund history
     */
    public FundHistory(Trading trading, Currency currency, Float coins) {
        this.trading = trading;
        this.currency = currency;
        this.coins = coins;
    }

    public FundHistory() {
    }

    public Trading getTrading() {

        return trading;
    }

    public Currency getCurrency() {

        return currency;
    }

    public Float getCoins() {

        return coins;
    }
}