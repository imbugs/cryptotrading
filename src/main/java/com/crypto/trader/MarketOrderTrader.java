package com.crypto.trader;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.SignalDao;
import com.crypto.dao.WalletHistoryDao;
import com.crypto.entities.*;
import com.crypto.enums.MarketTrend;
import com.crypto.util.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import java.util.Map;

/**
 * Trader which traders market orders, orders which are traded immediately against a given cryptocurrency rate.
 * <p/>
 * Created by Jan Wicherink on 16-7-15.
 */
@Stateful
public class MarketOrderTrader extends Trader {

    @EJB
    private WalletHistoryDao walletHistoryDao;

    @EJB
    private SignalDao signalDao;

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;


    /**
     * Default constructor
     */
    public MarketOrderTrader() {
        super();
    }


    /**
     * Constructor
     *
     * @param sinceIndex since index for trading
     * @param funds      the funds that are available for trading
     * @param wallet     the wallet
     * @param trading    the trading
     * @param logger     the logger used for logging.
     */
    public MarketOrderTrader(final Integer sinceIndex, final Map<Currency, Fund> funds, final Wallet wallet, final Trading trading, final Logger logger) {

        super(sinceIndex, funds, wallet, trading, logger);
    }


    private Boolean walletValueDecreases(final CryptocoinHistory cryptocoinHistory) {
        final WalletHistory previousWalletHistory = walletHistoryDao.getLast(getTrading());
        final Float previouslyWalletHistoryValue = previousWalletHistory.getTotalValue();
        final Float currentWalletHistoryValue = (getWallet().getCryptoCoins() * cryptocoinHistory.getClose()) + getWallet().getCoins();
        Float profitPercentage = getTrading().getMinProfitPercentage();

        profitPercentage += getTrading().getTradePair().getTransactionFee();

        final Float minWalletHistoryValue = previouslyWalletHistoryValue * (100 + profitPercentage) / 100;

        if (currentWalletHistoryValue <= minWalletHistoryValue) {
            return true;
        }
        return false;
    }


    /**
     * Checks if a sell is a bad sell, when the current cryptocoin currency rate is lower than the last buy price
     * taking into account the trading fee.
     *
     * @param cryptocoinHistory
     */
    public Boolean badSellTrade(final CryptocoinHistory cryptocoinHistory) {

        if (getTrading().getCheckBadSell()) {

            final MarketOrder marketOrder = getMarketOrderDao().getLastBuy(cryptocoinHistory.getIndex(), getTrading());

            if (marketOrder != null) {

                final Float lastExchangeRate = marketOrder.getExchangeRate();
                Float minProfitPercentage = getTrading().getMinProfitPercentage();

                if (minProfitPercentage > 0) {

                    if (getTrading().getCheckBadSellWallet()) {
                        // Bad sell when the total wallet history value decreases

                        return walletValueDecreases(cryptocoinHistory);
                    } else {
                        // Bad sell when the profit is less that the profit percentage based on selling against current exchange rate

                        minProfitPercentage += getTrading().getTradePair().getTransactionFee();

                        Float minExchangeRate = lastExchangeRate * (100 + minProfitPercentage) / 100;

                        if (cryptocoinHistory.getClose() <= minExchangeRate) {
                            return true;
                        }
                    }
                } else {
                    if (getTrading().getCheckBadSellWallet()) {

                        return walletValueDecreases(cryptocoinHistory);
                    } else {
                        final Float effectiveRate = (100 - getTrading().getTradePair().getTransactionFee()) / 100 * cryptocoinHistory.getClose();

                        if (effectiveRate <= lastExchangeRate) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Checks if a buy is a bad buy, this is the case when the current rate is higher than the last sell.
     *
     * @param cryptocoinHistory
     * @return
     */
    public Boolean badBuyTrade(CryptocoinHistory cryptocoinHistory) {

        if (getTrading().getCheckBadBuy()) {

            final MarketOrder marketOrder = getMarketOrderDao().getLastSell(cryptocoinHistory.getIndex(), getTrading());

            if (marketOrder != null) {

                final Float lastExchangeRate = marketOrder.getExchangeRate();
                final Float effectiveRate = (100 + getTrading().getTradePair().getTransactionFee()) / 100 * cryptocoinHistory.getClose();

                if (effectiveRate > lastExchangeRate) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Check if there is currently a signal for order creation.
     *
     * @param cryptocoinHistory the current cryptocoin history
     * @return the market order.
     */
    private MarketOrder checkCurrentSignalForOrderCreation(CryptocoinHistory cryptocoinHistory) {

        MarketOrder marketOrder = null;

        final Integer index = cryptocoinHistory.getIndex();

        for (TradeRule tradeRule : getTrading().getTradeRules()) {

            final Signal signal = signalDao.get(index, tradeRule, getTrading());

            if (signal != null && signal.getTradeSignal().equals(MarketTrend.BULL)) {
                if (!badBuyTrade(cryptocoinHistory)) {
                    marketOrder = createBuyMarketOrder(cryptocoinHistory);
                }
            }

            if (signal != null && signal.getTradeSignal().equals(MarketTrend.BEAR)) {
                if (!badBuyTrade(cryptocoinHistory)) {
                    marketOrder = createSellMarketOrder(cryptocoinHistory);
                }
            }
        }
        ;
        return marketOrder;
    }


    /**
     * Trade at given crypto currency index
     *
     * @param cryptocoinHistory the current cryptocoin history
     */
    public void tradeAtIndex(final CryptocoinHistory cryptocoinHistory) {

        final MarketOrder marketOrder = checkCurrentSignalForOrderCreation(cryptocoinHistory);

        if (marketOrder != null) {

            if (marketOrder instanceof BuyMarketOrder) {
                if (((BuyMarketOrder) marketOrder).getCryptoCoins() >= getTrading().getMinTradingCryptoCurrency()) {
                    getMarketOrderDao().persist(marketOrder);
                    updateWallet(marketOrder);
                }
            }

            if (marketOrder instanceof SellMarketOrder) {
                if (((SellMarketOrder) marketOrder).getCryptoCoins() >= getTrading().getMinTradingCryptoCurrency()) {
                    getMarketOrderDao().persist(marketOrder);
                    updateWallet(marketOrder);
                }

            }
        }
    }

    /**
     * Trade with all available crypto coin histories.
     */
    public void trade() {

        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getCryptoCoinHistorySinceIndex(getTrading().getTradePair(), getSinceIndex());

        cryptocoinHistories.forEach(cryptocoinHistory -> {
            tradeAtIndex(cryptocoinHistory);
        });
    }
}