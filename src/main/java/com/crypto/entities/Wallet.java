package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The wallet containing coins and cryptocoins intended for trading.
 *
 * Created by Jan Wicherink on 31-3-2015.
 */

@Entity
public class Wallet implements Serializable{

    // The trading of this wallet
    @Id
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID")
    private Trading trading;

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

    public Wallet(Trading trading, Float coins, Float cryptoCoins, Currency currency, CryptoCurrency cryptoCurrency, Float exchangeRate) {
        this.trading = trading;
        this.coins = coins;
        this.cryptoCoins = cryptoCoins;
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.exchangeRate = exchangeRate;
    }

    public void addCoins (Float coinsToBeAdded) {
        this.coins = this.coins + coinsToBeAdded;
    }

    public void addCryptoCoins (Float cryptoCoinsToBeAdded) {
        this.cryptoCoins = this.cryptoCoins + cryptoCoinsToBeAdded;
    }

    public Float determineMaxTradingCryptoCoins (Float maxTradingCryptoCoinsPerc) {

        final Float maxCryptoCoins = this.cryptoCoins * maxTradingCryptoCoinsPerc / 100;

        return maxCryptoCoins;
    }

    public Float determineMaxTradingCoins ( Float maxTradingCoinsPerc) {

        final Float maxCoins = this.coins * maxTradingCoinsPerc/ 100;

        return maxCoins;
    }

    public Float getTotalValue() {

        final Float value = this.coins + this.cryptoCoins * this.exchangeRate;

        return value;
    }

    public Trading getTrading() {
        return trading;
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


}
