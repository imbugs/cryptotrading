package com.crypto.trader;

import com.crypto.entities.CryptocoinHistory;

/**
 * Trade cyrpto coins interface.
 *
 * Created by Jan Wicherink on 24-6-15.
 */
public interface TradeCryptoCoins {

    /**
     * Trade
     */
    public void trade();

    /**
     * Trade at a given index
     * @param index the index
     */
    public void tradeAtIndex(final Integer index);


    /**
     * Evaluate if trading at a crypto coin history delivers a bad sell.
     * A bad sell is a sell where the crypto coin is sold at a lower exchange rate than purchased.
     *
     * @param cryptocoinHistory the crypto coin history.
     * @return true when itś a bad sell, false otherwise.
     */
    public Boolean badSellTrade(final CryptocoinHistory cryptocoinHistory);

    /**
     * Evaluate if trading at a crypto coin history delivers a bad buy.
     * A bad buy is a buy where the crypto coin is bought at a higher exchange rate than the previously sold rate.
     *
     * @param cryptocoinHistory the crypto coin history.
     * @return true when itś a buy sell, false otherwise.
     */
    public Boolean badBuyTrade(final CryptocoinHistory cryptocoinHistory);

    /**
     * Checks if logging is required.
     *
     * @return true when logging is enabled, false otherwise.
     */
    public Boolean isLoggingEnabled();
}
