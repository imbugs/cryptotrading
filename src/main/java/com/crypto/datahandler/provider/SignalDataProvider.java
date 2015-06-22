package com.crypto.datahandler.provider;

import com.crypto.entities.*;

import java.util.List;

/**
 * Provider of data for signal calculatnMovingAverageDataProvider
 * <p/>
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
     * Get a macd value at a given index
     *
     * @param index the index of the value
     * @return the macd value
     */
    public MacdValue getMacdValue(final Integer index);


    /**
     * Returns the Macd
     *
     * @return the macd.
     */
    public Macd getMacd();

    /**
     * Set the macd
     *
     * @param macd the macd.
     */
    public void setMacd(final Macd macd);

    /**
     * Get the trend of this moving average data provider
     *
     * @return the trend.
     */
    public Trend getTrend();

    /**
     * Set the trend of the moving average data provider
     *
     * @param trend the trend.
     */
    public void setTrend(final Trend trend);

    /**
     * Get all trade conditions of a trade rule
     *
     * @param tradeRule the trade rule
     * @return all the trade condistions of a trade rule.
     */
    public List<TradeCondition> getAllTradeConditions(final TradeRule tradeRule);

    /**
     * Get the last trading signal.
     *
     * @param trading the trading.
     * @return the last signal of the trading.
     */
    public Signal getLastSignal(final Trading trading);


    /**
     * Saves a trade condition log
     *
     * @param conditionLog the trade condition log.
     */
    public void saveLog(TradeConditionLog conditionLog);
}
