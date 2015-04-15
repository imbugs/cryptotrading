package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The wallet containing coins and cryptocoins intended for trading.
 *
 * Created by Jan Wicherink on 31-3-2015.
 */

@Entity
@Table (name="WALLET")
public class Wallet implements Serializable{


    private static final long serialVersionUID = -2028307033146190019L;

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

    /**
     * Default constructor
     */
    public Wallet() {

    }

    /**
     * Wallet constructor
     * @param trading the trading
     * @param coins the coins in the wallet
     * @param cryptoCoins the cryptocoins in the wallet
     * @param currency the currency of the coins
     * @param cryptoCurrency the cryptocurrency of the crypto coins
     * @param exchangeRate the exchange rate between coins and crypto coins
     */
    public Wallet(Trading trading, Float coins, Float cryptoCoins, Currency currency, CryptoCurrency cryptoCurrency, Float exchangeRate) {
        this.trading = trading;
        this.coins = coins;
        this.cryptoCoins = cryptoCoins;
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.exchangeRate = exchangeRate;
    }

    /**
     * Add coins to the wallet
     * @param coinsToBeAdded the coins to be added to the wallet
     */
    public void addCoins (Float coinsToBeAdded) {
        this.coins = this.coins + coinsToBeAdded;
    }

    /**
     * Add crypto coins to the wallet
     * @param cryptoCoinsToBeAdded the crypto coins to be added to the wallet
     */
    public void addCryptoCoins (Float cryptoCoinsToBeAdded) {
        this.cryptoCoins = this.cryptoCoins + cryptoCoinsToBeAdded;
    }

    /**
     * Determine the maximum amount of cryptocoins that can be used in a trading
     * @param maxTradingCryptoCoinsPerc
     * @return the maximum number of crypto coins
     */
    public Float determineMaxTradingCryptoCoins (Float maxTradingCryptoCoinsPerc) {

        final Float maxCryptoCoins = this.cryptoCoins * maxTradingCryptoCoinsPerc / 100;

        return maxCryptoCoins;
    }

    /**
     * Determine the maximum number of coins available for trading
     * @param maxTradingCoinsPerc
     * @return the maximum number of coins for trading.
     */
    public Float determineMaxTradingCoins ( Float maxTradingCoinsPerc) {

        final Float maxCoins = this.coins * maxTradingCoinsPerc/ 100;

        return maxCoins;
    }

    /**
     * Get total value of the wallet expressed in coins
     * @return the total value of the wallet
     */
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
