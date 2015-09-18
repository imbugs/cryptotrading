package com.crypto.trader;

import com.crypto.dao.*;
import com.crypto.entities.*;
import com.crypto.enums.LoggingLevel;
import com.crypto.enums.MarketOrderStatus;
import com.crypto.util.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A crypto coin trader
 * Created by Jan Wicherink on 24-6-15.
 */
@Stateful
public class Trader {

    @EJB
    private WithdrawalDao withdrawalDao;

    @EJB
    private WalletDao walletDao;

    @EJB
    private FundDao fundDao;

    @EJB
    private FundHistoryDao fundHistoryDao;

    @EJB
    private MarketOrderDao marketOrderDao;

    private Integer fromIndex;

    private Integer toIndex;


    // key value pairs of funds, key = currency code
    private Map<Currency, Fund> funds;

    private Wallet wallet;

    private Trading trading;

    @EJB
    private Logger logger;

    /**
     * Default constructor.
     */
    public Trader() {

    }

    /**
     * Initialise the trader
     */
    public void init() {
        Fund fundCoins = fundDao.get(trading.getTradePair(), trading.getTradePair().getCurrency());
        Fund fundCryptoCoins = fundDao.get(trading.getTradePair(), trading.getTradePair().getCryptoCurrency());

        this.funds = new HashMap<>();
        this.funds.put(trading.getTradePair().getCurrency(), fundCoins);
        this.funds.put(trading.getTradePair().getCryptoCurrency(), fundCryptoCoins);

        this.wallet = walletDao.get(trading);
    }

    /**
     * Create a sell market order
     *
     * @param cryptocoinHistory the cryptocoin history for which a sell order is to be created.
     * @return the market sell order.
     */
    public SellMarketOrder createSellMarketOrder(final CryptocoinHistory cryptocoinHistory) {

        final Integer index = cryptocoinHistory.getIndex();

        if (this.wallet.getCryptoCoins() == 0F) {
            logger.LOG(trading, LoggingLevel.INFO, index, "Geen verkoop order kunnen aanmaken, geen crypto munten in beurs.");
            return null;
        }

        Float maxTradingCryptoCoins = this.wallet.determineMaxTradingCryptoCoins(this.trading.getMaxTradingCryptoCoinsPerc());
        maxTradingCryptoCoins -= getCryptoCoinsOfOpenSellOrders();

        if (maxTradingCryptoCoins <= 0F) {
            logger.LOG(trading, LoggingLevel.INFO, index, "Geen verkoop order kunnen aanmaken, geen crypto munten in beurs.");
            return null;
        }

        Float amountOfCryptoCoins;

        if (this.wallet.getCryptoCoins() < maxTradingCryptoCoins) {
            amountOfCryptoCoins = this.wallet.getCryptoCoins();
        } else {
            amountOfCryptoCoins = maxTradingCryptoCoins;
        }

        // Round down to five decimals
        amountOfCryptoCoins = new Float(Math.floor(amountOfCryptoCoins * 100000)) / 100000F;

        SellMarketOrder sellMarketOrder =
                new SellMarketOrder(
                        cryptocoinHistory.getIndex(),
                        "",    // TODO : Order reference
                        this.trading,
                        cryptocoinHistory.getTimestamp(),
                        cryptocoinHistory.getClose(),
                        MarketOrderStatus.OPEN,
                        3,  // TODO  retry count
                        false,
                        amountOfCryptoCoins);

        sellMarketOrder.calculateFee();
        logger.LOG(trading, LoggingLevel.INFO, index, "Verkoop order aangemaakt voor : " +
                   trading.getTradePair().getCryptoCurrency().getCode() + ' '+ amountOfCryptoCoins);

        return sellMarketOrder;
    }


    /**
     * Create a buy market order.
     *
     * @param cryptocoinHistory the current crypto currency history.
     * @return the market order.
     */
    public BuyMarketOrder createBuyMarketOrder(final CryptocoinHistory cryptocoinHistory) {

        final Integer index = cryptocoinHistory.getIndex();

        if (this.wallet.getCoins() == 0F) {
            // There's nothing to buy, check if the wallet can be refunded.
            refundWallet();

            if (this.wallet.getCoins() == 0F) {
                // Refunding failed.
                logger.LOG(trading, LoggingLevel.INFO, index, "Aanvullen beurs met munten vanuit fonds niet mogelijk, fons leeg");

                return null;
            }
        }

        Float maxTradingCoins = this.wallet.determineMaxTradingCoins(this.trading.getMaxTradingCoinsPerc());
        maxTradingCoins -= getCoinsOfOpenBuyOrders();

        if (maxTradingCoins <= 0F) {
            logger.LOG(trading, LoggingLevel.INFO, index, "Onvoldoende munten in beurs voor aankoop koop order");

            return null;
        }

        Float amountOfCoins;

        if (this.wallet.getCoins() < maxTradingCoins) {
            amountOfCoins = this.wallet.getCoins();
        } else {
            amountOfCoins = maxTradingCoins;
        }

        // Round down to two decimals
        amountOfCoins = new Float(Math.floor(amountOfCoins * 100)) / 100F;

        BuyMarketOrder buyMarketOrder =
                new BuyMarketOrder(
                        cryptocoinHistory.getIndex(),
                        "",    // TODO : Order reference
                        this.trading,
                        cryptocoinHistory.getTimestamp(),
                        cryptocoinHistory.getClose(),
                        MarketOrderStatus.OPEN,
                        3,  // TODO  retry count
                        false,
                        amountOfCoins);

        buyMarketOrder.calculateFee();

        logger.LOG(trading, LoggingLevel.INFO, index, "Koop order aangemaakt voor : " +
                   trading.getTradePair().getCurrency().getCode() + ' '+ amountOfCoins);

        return buyMarketOrder;
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

        final String currencyString = wallet.getCurrency().getCode();
        final String cryptoCurrencyString = wallet.getCryptoCurrency().getCode();

        if (isLoggingEnabled()) {
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Beschikbare munten in fonds: " + currencyString + ' '+ fundCoins);
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Beschikbare crypto munten in fonds: " + cryptoCurrencyString + ' ' + fundCryptoCoins);
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Munten in beurs : " + currencyString + ' ' + wallet.getCoins());
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Crypto munten in beurs : " + cryptoCurrencyString + ' '+ wallet.getCryptoCoins());
        }

        Float coinsToBeWithdrawn = new Float(Math.floor((walletCoins * 100)) / 100);

        if (coinsToBeWithdrawn > 0) {
            // Add the funding to the wallet
            this.wallet.addCoins(coinsToBeWithdrawn);

            // Withdraw from the funds.
            withdraw(this.wallet.getCurrency(), coinsToBeWithdrawn);

            if (isLoggingEnabled()) {
                logger.LOG(trading, LoggingLevel.DEBUG, null, "Vul beurs aan met munten uit fonds: " + currencyString + ' ' + walletCoins);
            }
        } else {
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Aanvullen van beurs met munten vanuit fonds niet mogelijk doordat fonds leeg is.");
        }

        Float cryptoCoinsToBeWithdrawn = new Float(Math.floor((walletCryptoCoins * 10000)) / 10000);

        if (cryptoCoinsToBeWithdrawn > 0) {
            // Add the funding to the wallet
            this.wallet.addCryptoCoins(cryptoCoinsToBeWithdrawn);

            // Withdraw from the funding.
            withdraw(this.wallet.getCryptoCurrency(), cryptoCoinsToBeWithdrawn);

            if (isLoggingEnabled()) {
                logger.LOG(trading, LoggingLevel.DEBUG, null, "Aanvullen van beurs met crypto munten uit fonds: " + cryptoCurrencyString + ' ' + walletCryptoCoins);
            }
        } else {
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Aanvullen van beurs met crypto munten vanuit fonds niet mogelijk doordat fonds leeg is.");
        }

        saveWallet();
        saveFunds();
    }

    /**
     * Get total number of coins currently in open buy orders.
     *
     * @return the amount of coins in orders.
     */
    public Float getCoinsOfOpenBuyOrders() {

        Float total = 0F;

        for (final MarketOrder order : (List<MarketOrder>) marketOrderDao.getOpenOrders(this.trading).stream().filter((o) -> o instanceof BuyMarketOrder).collect(Collectors.toList())) {
            total += ((BuyMarketOrder) order).getCoins();
        }

        return total;
    }


    /**
     * Get the total number of cryptocoins currently in open sell orders.
     *
     * @return the amount of crypto coins in orders.
     */
    public Float getCryptoCoinsOfOpenSellOrders() {

        Float total = 0F;

        for (final MarketOrder order : (List<MarketOrder>) marketOrderDao.getOpenOrders(this.trading).stream().filter((o) -> o instanceof SellMarketOrder).collect(Collectors.toList())) {
            total += ((SellMarketOrder) order).getCryptoCoins();
        }
        return total;
    }

    /**
     * Process all withdrawals and subtract the witdrawal from the fund
     * After updating the funds, remove the withdrawals, to allow for new withdrawals
     */
    public void processWithdrawals() {

        if (isLoggingEnabled()) {
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Reset opnames uit fonds.");
        }

        withdrawalDao.getAll().forEach((withdrawal) -> {
                    writeOffFunding(withdrawal);
                    withdrawalDao.remove(withdrawal);
                }
        );
    }


    /**
     * Restore earned coins to funding.
     *
     * @param coins the coins earned.
     */
    protected void restoreToFund(final Float coins) {

        if (isLoggingEnabled()) {
            logger.LOG(trading, LoggingLevel.DEBUG, null, "Stort munten terug in fonds : " + coins.toString());
        }

        Fund fund = funds.get(trading.getTradePair().getCurrency());
        fund.addCoins(coins);

        saveFund(fund);
    }


    /**
     * Write off the witdrawal from the funding.
     *
     * @param withdrawal the withdrawal to be written off.
     */
    public void writeOffFunding(final Withdrawal withdrawal) {

        final Iterator it = this.funds.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry entry = (Map.Entry) it.next();

            if (entry.getKey().equals(withdrawal.getCurrency().getCode())) {

                final Fund fund = ((Fund) entry.getValue());
                fund.subtractCoins(withdrawal.getCoins());

                saveFund(fund);
            }
        }
    }


    /**
     * Persist a fund and it's history.
     *
     * @param fund the fund to be persisted.
     */
    public void saveFund(final Fund fund) {
        if (fund.getCoins() < 0) {
            fund.setCoins(0F);
        }

        fundDao.persist(fund);

        final FundHistory fundHistory = new FundHistory(this.trading, fund.getCurrency(), fund.getCoins());

        fundHistoryDao.persist(fundHistory);
    }


    /**
     * /**
     * Get funding from the available funds.
     *
     * @param currency the currency seeked as a funding
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
     * Witdraw the currency from the fund.
     *
     * @param currency the currency.
     * @param coins    the amount of coins withdrawn from the fund.
     */
    public void withdraw(final Currency currency, final Float coins) {

       Fund fund = this.funds.get(currency);
       fund.setCoins(fund.getCoins()-coins);

       this.funds.put(currency, fund);
    }

    /**
     * Save the wallet.
     */
    private void saveWallet() {
        walletDao.persist(this.wallet);
    }

    /**
     * Save the funds.
     */
    private void saveFunds() {
        fundDao.persist(this.funds.get(this.wallet.getCurrency()));
        fundDao.persist(this.funds.get(this.wallet.getCryptoCurrency()));
    }

    /**
     * Update the wallet after a market order has been created
     *
     * @param marketOrder the market order.
     */
    public void updateWallet(final MarketOrder marketOrder) {

        String message;

        if (marketOrder != null) {

            Float coins = getWallet().getCoins();
            Float cryptoCoins = getWallet().getCryptoCoins();

            if (getLogging()) {
                message = "Update crypto munten in beurs :" + cryptoCoins;
                logger.LOG(getTrading(), LoggingLevel.DEBUG, marketOrder.getIndex(), message);
            }

            if (marketOrder instanceof SellMarketOrder) {
                // Crypto coins taken from wallet

                cryptoCoins = cryptoCoins - ((SellMarketOrder) marketOrder).getCryptoCoins();
                coins = 0F;

                if (getLogging()) {
                    message = "Verkochte crypto munten :" + ((SellMarketOrder) marketOrder).getCryptoCoins();
                    logger.LOG(getTrading(), LoggingLevel.DEBUG, marketOrder.getIndex(), message);

                    message = "Nieuwe aantal crypto munten in beurs :" + cryptoCoins;
                    logger.LOG(getTrading(), LoggingLevel.DEBUG, marketOrder.getIndex(), message);

                    // Coins restored to fund and withdrawals removed from fund
                    restoreToFund(((SellMarketOrder) marketOrder).getCoins());
                    processWithdrawals();
                }
            }

            if (marketOrder instanceof BuyMarketOrder) {
                // Crypto coins added to wallet

                cryptoCoins = cryptoCoins + ((BuyMarketOrder) marketOrder).getCryptoCoins();
                coins = coins - ((BuyMarketOrder) marketOrder).getCoins();

                if (getLogging()) {
                    message = "Gekochte crypto munten :" + ((BuyMarketOrder) marketOrder).getCryptoCoins();
                    logger.LOG(getTrading(), LoggingLevel.DEBUG, marketOrder.getIndex(), message);

                    message = "Nieuwe aanatal crypto munten in beurs :" + cryptoCoins;
                    logger.LOG(getTrading(), LoggingLevel.DEBUG, marketOrder.getIndex(), message);
                }
            }

            getWallet().setCoins(coins);
            getWallet().setCryptoCoins(cryptoCoins);
            getWallet().setExchangeRate(marketOrder.getExchangeRate());
        }
    }

    public Boolean badSellTrade(CryptocoinHistory cryptocoinHistory) {
        return trading.getCheckBadSell();
    }

    public Boolean badBuyTrade(CryptocoinHistory cryptocoinHistory) {
        return trading.getCheckBadBuy();
    }

    public Boolean isLoggingEnabled() {
        return trading.getLogging();
    }

    public void setFromIndex(Integer fromIndex) {
        this.fromIndex = fromIndex;
    }

    public Integer getFromIndex() {
        return fromIndex;
    }

    public Integer getToIndex() {
        return toIndex;
    }

    public void setToIndex(Integer toIndex) {
        this.toIndex = toIndex;
    }

    public void setFunds(Map<Currency, Fund> funds) {
        this.funds = funds;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setTrading(Trading trading) {
        this.trading = trading;
    }

    public WithdrawalDao getWithdrawalDao() {
        return withdrawalDao;
    }

    public WalletDao getWalletDao() {
        return walletDao;
    }

    public FundDao getFundDao() {
        return fundDao;
    }

    public FundHistoryDao getFundHistoryDao() {
        return fundHistoryDao;
    }

    public MarketOrderDao getMarketOrderDao() {
        return marketOrderDao;
    }

    public Trading getTrading() {
        return trading;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Boolean getLogging() {
        return getTrading().getLogging();
    }

    public Map<Currency, Fund> getFunds() {
        return funds;
    }

    public Logger getLogger() {
        return logger;
    }
}