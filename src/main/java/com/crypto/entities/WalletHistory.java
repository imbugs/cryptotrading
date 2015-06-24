package com.crypto.entities;

import com.crypto.entities.pkey.WalletHistoryPk;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Wallet history
 *
 * Created by Jan Wicherink on 7-5-15.
 */
@Entity
@Table(name="WALLET_HISTORY")
public class WalletHistory {

    @EmbeddedId
    private WalletHistoryPk pk;

    // The number of coins in the wallet
    @Column(name="COINS")
    private Float coins;

    // The number of cryptocoins in the wallet
    @Column(name="CRYPTOCOINS")
    private Float cryptoCoins;

    // The currency of the coins
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="CURRENCY")
    private Currency currency;

    // The currency of the crypto coins
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="CRYPTO_CURRENCY")
    private CryptoCurrency cryptoCurrency;

    // The exchange rate, for exchanging coins to 1 cryptocoin
    @Column(name="EXCHANGE_RATE")
    private Float exchangeRate;

    @Column (name="TOTAL_VALUE")
    private Float totalValue;


    /**
     * Construncto
     * @param timestamp timestamp of the history
     * @param trading util of the wallet history
     * @param coins  number of coins
     * @param cryptoCoins number of cryptocoins
     * @param currency currencty of the coins
     * @param cryptoCurrency crypto currency of the cryptocoins
     * @param exchangeRate exchange rate
     * @param totalValue the total value of the wallet
     */
    public WalletHistory(Timestamp timestamp,  Trading trading, Float coins, Float cryptoCoins, Currency currency, CryptoCurrency cryptoCurrency, Float exchangeRate, Float totalValue) {
        this.pk = new WalletHistoryPk(timestamp, trading);
        this.coins = coins;
        this.cryptoCoins = cryptoCoins;
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.exchangeRate = exchangeRate;
        this.totalValue = totalValue;
    }

    /**
     * Default constructor
     */
    public WalletHistory () {

    }

    public Float getCoins() {
        return coins;
    }

    public Float getCryptoCoins() {
        return cryptoCoins;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public Trading getTrading () {
        return pk.getTrading();
    }

    public Timestamp getTimestamp () {
        return pk.getTimestamp();
    }

    public Float getTotalValue() {
        return totalValue;
    }
}
