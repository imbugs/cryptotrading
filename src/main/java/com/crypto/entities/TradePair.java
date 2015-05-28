package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jan Wicherink on 31-3-2015.
 */
@Entity
@Table(name="TRADE_PAIRS")
public class TradePair implements Serializable {

    private static final long serialVersionUID = -4363837420737951917L;

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

    public Float getTransactionFee() {
        return transactionFee;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public TradingSite getTradingSite() {

        return tradingSite;
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
}
