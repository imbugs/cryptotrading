package com.crypto.services.rest.wrapper;

import com.crypto.entities.Fund;
import com.crypto.entities.Withdrawal;

/**
 * Wrapper class for the balance sheet of the trading
 * Created by Jan Wicherink on 16-9-2015.
 */
public class Balance {

    private Float coins;

    private Float cryptoCoins;

    private Fund fundCoins;

    private Fund fundCryptoCoins;

    private Withdrawal withdrawalCoins;

    private Withdrawal withdrawalCryptoCoins;

    private Float exchangeRate;

    private Float totalValue;

    private Float profit = 0F;

    /**
     * Constructor
     *
     * @param coins coins
     * @param cryptoCoins crypto coins
     * @param fundCoins coins in fund
     * @param fundCryptoCoins crypto coins in fund
     * @param withdrawalCoins withdrawal of coins from fund
     * @param withdrawalCryptoCoins withdrawal of cryptoCoins of fund
     * @param exchangeRate exchange rage
     */
    public Balance(Float coins, Float cryptoCoins, Fund fundCoins, Fund fundCryptoCoins, Withdrawal withdrawalCoins, Withdrawal withdrawalCryptoCoins, Float exchangeRate) {
        this.coins = coins;
        this.cryptoCoins = cryptoCoins;
        this.fundCoins = fundCoins;
        this.fundCryptoCoins = fundCryptoCoins;
        this.withdrawalCoins = withdrawalCoins;
        this.withdrawalCryptoCoins = withdrawalCryptoCoins;
        this.exchangeRate = exchangeRate;
    }

    public Float getCoins() {
        return coins;
    }

    public Float getCryptoCoins() {
        return cryptoCoins;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public Float getTotalValue() {
        return totalValue;
    }

    public void calculateTotalValue() {

        Float withdrawalCoinsValue;
        Float withdrawalCryptoCoinsValue;

        if (withdrawalCoins == null) {
            withdrawalCoinsValue = 0F;
        }
        else {
            withdrawalCoinsValue = withdrawalCoins.getCoins();
        }

        if (withdrawalCryptoCoins == null) {
           withdrawalCryptoCoinsValue = 0F;
        }
        else {
            withdrawalCryptoCoinsValue = withdrawalCryptoCoins.getCoins();
        }
            this.totalValue = cryptoCoins + fundCoins.getCoins() - withdrawalCoinsValue + ((cryptoCoins + fundCryptoCoins.getCoins() - withdrawalCryptoCoinsValue) * exchangeRate);
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Fund getFundCoins() {
        return fundCoins;
    }

    public Fund getFundCryptoCoins() {
        return fundCryptoCoins;
    }

    public Withdrawal getWithdrawalCoins() {
        return withdrawalCoins;
    }

    public Withdrawal getWithdrawalCryptoCoins() {
        return withdrawalCryptoCoins;
    }
}
