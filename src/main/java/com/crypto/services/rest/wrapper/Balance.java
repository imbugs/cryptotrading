package com.crypto.services.rest.wrapper;

import com.crypto.entities.Fund;
import com.crypto.entities.Withdrawal;

/**
 * Wrapper class for the balance sheet of the trading
 * Created by Jan Wicherink on 16-9-2015.
 */
public class Balance {

    private Float walletCoins;

    private Float walletCryptoCoins;

    private Fund fundCoins;

    private Fund fundCryptoCoins;

    private Float exchangeRate;

    private Float totalValue;

    private Float profit = 0F;

    /**
     * Constructor
     *
     * @param walletCoins           walletCoins
     * @param wallletCryptoCoins    crypto walletCoins
     * @param fundCoins             walletCoins in fund
     * @param fundCryptoCoins       crypto walletCoins in fund
     * @param exchangeRate          exchange rage
     */
    public Balance(Float walletCoins, Float wallletCryptoCoins, Fund fundCoins, Fund fundCryptoCoins, Float exchangeRate) {
        this.walletCoins = walletCoins;
        this.walletCryptoCoins = wallletCryptoCoins;
        this.fundCoins = fundCoins;
        this.fundCryptoCoins = fundCryptoCoins;
        this.exchangeRate = exchangeRate;
    }

    public Float getWalletCoins() {
        return walletCoins;
    }

    public Float getWalletCryptoCoins() {
        return walletCryptoCoins;
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

        this.totalValue = walletCoins + fundCoins.getCoins() + ((walletCryptoCoins + fundCryptoCoins.getCoins()) * exchangeRate);
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

    public Float getCoins () {
        Float totalValue;

        totalValue = walletCoins + fundCoins.getCoins();
        return totalValue;
    }

    public Float getCryptoCoins() {
        Float withdrawalCryptoCoinsValue;
        Float totalValue;

        totalValue = walletCryptoCoins + fundCryptoCoins.getCoins();
        return totalValue;
    }
}

