package com.crypto.entities;

import javax.persistence.*;

/**
 * Created by Jan Wicherink on 31-3-2015.
 */
@Entity
@Table(name="TRADE_PAIR")
public class TradePair {

    @Id
    @Column (name="ID")
    private Integer id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name="TRADING_SITE_CODE", nullable=false, updatable=false)
    private TradingSite tradingSite;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name="CURRENCY_CODE", nullable=false, updatable=false)
    private Currency currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name="CRYPTO_CURRENCY_CODE", nullable=false, updatable=false)
    private CryptoCurrency cryptoCurrency;

    @Column (name="TRANSACTION_FEE")
    private Float transactionFee;


    public TradePair() {
    }

    public TradePair(Integer id, TradingSite tradingSite, Currency currency, CryptoCurrency cryptoCurrency, Float transactionFee) {
        this.id = id;
        this.tradingSite = tradingSite;
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.transactionFee = transactionFee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        id = id;
    }

    public TradingSite getTradingSite() {
        return tradingSite;
    }

    public void setTradingSite(TradingSite tradingSite) {
        this.tradingSite = tradingSite;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public Float getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(Float transactionFee) {
        this.transactionFee = transactionFee;
    }
}
