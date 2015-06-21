package com.crypto.datahandler.provider;

import com.crypto.entities.*;

import java.util.List;

/**
 * Provider of data for signal calculatnMovingAverageDataProvider
 *
 * Created by Jan Wicherink on 1-5-15.
 */
public interface SignalDataProvider extends DataProvider {

    /**
     * Get a value at a given index
     *
     * @param index the index

     * @return the crypto coin value
     */
    public CryptocoinHistory getValue(final Integer index);

     /**
     * Get a trend value at a given index
     *
     * @param index the index of the value
     * @return the trend value
     */
    public TrendValue getTrendValue(final Integer index);

    /**
     * Get the trend of this moving average data provider
     * @return
     */
    public Trend getTrend();

    /**
     * Set the trend of the moving average data provider
     * @param trend
     */
    public void setTrend(final Trend trend);

    /**
     * Get all trade conditions of a trade rule
     * @param tradeRule the trade rule
     * @return all the trade condistions of a trade rule.
     */
    public List<TradeCondition> getAllTradeConditions(final TradeRule tradeRule);

    /**
     * Get the last trading signal.
     * @param trading the trading.
     * @return the last signal of the trading.
     */
    public Signal getLastSignal (final Trading trading);
}
