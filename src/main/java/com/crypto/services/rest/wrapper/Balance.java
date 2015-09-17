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

    private Withdrawal withdrawalCoins;

    private Withdrawal withdrawalCryptoCoins;

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
     * @param withdrawalCoins       withdrawal of walletCoins from fund
     * @param withdrawalCryptoCoins withdrawal of wallletCryptoCoins of fund
     * @param exchangeRate          exchange rage
     */
    public Balance(Float walletCoins, Float wallletCryptoCoins, Fund fundCoins, Fund fundCryptoCoins, Withdrawal withdrawalCoins, Withdrawal withdrawalCryptoCoins, Float exchangeRate) {
        this.walletCoins = walletCoins;
        this.walletCryptoCoins = wallletCryptoCoins;
        this.fundCoins = fundCoins;
        this.fundCryptoCoins = fundCryptoCoins;
        this.withdrawalCoins = withdrawalCoins;
        this.withdrawalCryptoCoins = withdrawalCryptoCoins;
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

        if (withdrawalCoins == null) {
            withdrawalCoinsValue = 0F;
        } else {
            withdrawalCoinsValue = withdrawalCoins.getCoins();
        }

        if (withdrawalCryptoCoins == null) {
            withdrawalCryptoCoinsValue = 0F;
        } else {
            withdrawalCryptoCoinsValue = withdrawalCryptoCoins.getCoins();
        }
        this.totalValue = walletCoins + fundCoins.getCoins() - withdrawalCoinsValue + ((walletCryptoCoins + fundCryptoCoins.getCoins() - withdrawalCryptoCoinsValue) * exchangeRate);
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

    public Float getCoins () {
        Float withdrawalCoinsValue;
        Float totalValue;

        if (withdrawalCoins == null) {
            withdrawalCoinsValue = 0F;
        } else {
            withdrawalCoinsValue = withdrawalCoins.getCoins();
        }

        totalValue = walletCoins + fundCoins.getCoins() - withdrawalCoinsValue;
        return totalValue;
    }

    public Float getCryptoCoins() {

        Float withdrawalCryptoCoinsValue;
        Float totalValue;

        if (withdrawalCryptoCoins == null) {
            withdrawalCryptoCoinsValue = 0F;
        } else {
            withdrawalCryptoCoinsValue = withdrawalCryptoCoins.getCoins();
        }

        totalValue = walletCryptoCoins + fundCryptoCoins.getCoins() - withdrawalCryptoCoinsValue;
        return totalValue;
    }
}

