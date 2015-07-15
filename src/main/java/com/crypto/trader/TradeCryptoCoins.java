package com.crypto.trader;

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
}
