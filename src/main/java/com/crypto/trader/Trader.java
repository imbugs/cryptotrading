package com.crypto.trader;

import com.crypto.dao.WalletDao;
import com.crypto.dao.WithdrawalDao;
import com.crypto.entities.*;
import com.crypto.enums.LoggingLevel;
import com.crypto.util.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.Map;

/**
 * Created by jan on 24-6-15.
 */
@Stateful
public abstract class Trader implements TradeCryptoCoins {

    @EJB
    private WithdrawalDao withdrawalDao;

    @EJB
    private WalletDao walletDao;

    private Integer sinceIndex;

    // key value pairs of funds, key = currency code
    private Map<Currency, Fund> funds;

    private Wallet wallet;

    private Trading trading;

    private Logger logger;

    /**
     * Constructor.
     *
     * @param sinceIndex the start index to start trading.
     * @param funds the funds used for trading.
     * @param wallet the wallet of the trading.
     * @param trading the trading.
     */
    public Trader (final Integer sinceIndex, final Map<Currency, Fund> funds, final Wallet wallet, final Trading trading, final Logger logger) {
        this.sinceIndex = sinceIndex;
        this.funds = funds;
        this.wallet = wallet;
        this.trading = trading;
        this.logger = logger;
    }

    /**
     * Refund the wallet when it's empty.
     */
    protected void refundWallet() {

        final Float percentage = this.trading.getRefundPercentage();
        final Float fundCoins = getFunding(this.wallet.getCurrency());
        final Float fundCryptoCoins = getFunding(this.wallet.getCryptoCurrency());

        final Float walletCoins = (percentage / 100) * fundCoins;
        final Float walletCryptoCoins = (percentage / 100) * fundCryptoCoins;

        if (isLoggingEnabled()) {
            logger.LOG(trading, LoggingLevel.DEBUG, null,"Availiable fund coins: " + fundCoins);
            logger.LOG(trading, LoggingLevel.DEBUG, null,"Availiable fund cryptocoins: " + fundCryptoCoins);
            logger.LOG(trading, LoggingLevel.DEBUG, null,"Wallet coins : " + wallet.getCoins());
            logger.LOG(trading, LoggingLevel.DEBUG, null,"Wallet crypto coins : " + wallet.getCryptoCoins());
        }

        Float coinsToBeWithdrawn = walletCoins - alreadyWithdrawn(this.trading.getTradePair().getCurrency());
        coinsToBeWithdrawn = new Float (Math.floor((coinsToBeWithdrawn * 100) /100));

        if (coinsToBeWithdrawn > 0) {
           // Add the funding to the wallet
           this.wallet.addCoins(new Float(coinsToBeWithdrawn));

           // Register the withdrawal from the funding.
           withdraw(this.wallet.getCurrency(), coinsToBeWithdrawn);

           if (isLoggingEnabled()) {
               logger.LOG(trading, LoggingLevel.DEBUG, null, "'Refund coins: " + walletCoins);
           }
        }
        else {
            logger.LOG(trading, LoggingLevel.DEBUG, null, "'No withdrawal of coins possible");
        }


        Float cryptoCoinsToBeWithdrawn = walletCryptoCoins - alreadyWithdrawn(this.trading.getTradePair().getCryptoCurrency());
        cryptoCoinsToBeWithdrawn = new Float (Math.floor((cryptoCoinsToBeWithdrawn * 100) /100));

        if (cryptoCoinsToBeWithdrawn > 0) {
            // Add the funding to the wallet
            this.wallet.addCryptoCoins(new Float(cryptoCoinsToBeWithdrawn));

            // Register the withdrawal from the funding.
            withdraw(this.wallet.getCryptoCurrency(), cryptoCoinsToBeWithdrawn);

            if (isLoggingEnabled()) {
                logger.LOG(trading, LoggingLevel.DEBUG, null, "'Refund crypto coins: " + walletCryptoCoins);
            }
        }
        else {
            logger.LOG(trading, LoggingLevel.DEBUG, null, "'No withdrawal of crypto coins possible");
        }

        saveWallet();
    }

    /**
     * Get funding from the available funds.
     * @param currency the currency seekd as a funding
     * @return the available amount of currency, null when no funding is found.
     */
    public Float getFunding(final Currency currency) {

        for (Map.Entry entry : this.funds.entrySet()) {

            if (entry.getKey().equals(currency)) {
                return ((Fund) entry.getValue()).getCoins();
            }
        }
        return null;
    }


    /**
     * Register a Withdrawal of currency from the funding.
     *
     * @param currency the currency.
     * @param coins the amout of coins withdrawn.
     */
    public void withdraw(final Currency currency, Float coins) {
       Withdrawal withdrawal = withdrawalDao.get(trading, currency);

       if (withdrawal == null) {
           withdrawal = new Withdrawal(trading, coins, currency);
           withdrawalDao.persist(withdrawal);
       }
       else {
           // Add the addition withdrawal
           withdrawal.addCoins(coins);
           withdrawalDao.update(withdrawal);
       }
    }


    /**
     * Get the amount of currency already withdrawn from the funding.
     * @param currency the currency
     * @return the amount of currency already withdrawn.
     */
    private Float alreadyWithdrawn (final Currency currency) {

        final Withdrawal withdrawal = withdrawalDao.get(this.trading, currency);

        if (withdrawal == null) {
            return 0F;
        }
        return withdrawal.getCoins();
    }

    /**
     * Save the wallet.
     */
    private void saveWallet () {

        walletDao.persist(this.wallet);

    }
}
