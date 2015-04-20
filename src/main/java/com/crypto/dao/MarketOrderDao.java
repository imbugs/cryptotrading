package com.crypto.dao;

import com.crypto.entities.MarketOrder;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
import org.yaml.snakeyaml.error.Mark;

import java.io.Serializable;
import java.util.List;

/**
 * Market order Dao
 * Created by Jan Wicherink on 20-4-15.
 */
public interface MarketOrderDao extends Serializable{

    /**
     * Persist the market order
     * @param marketOrder the market order
     */
    public void persist(final MarketOrder marketOrder);

    /**
     * Merge (update or insert) the market order
     * @param marketOrder the market order
     */
    public void merge (final MarketOrder marketOrder);

    /**
     * Get the market order by the order reference.
     *
     * @param orderReferecence the order reference
     * @return the market order
     */
    public MarketOrder getByOrderReference(final String orderReferecence);

    /**
     * Remove a market order
     * @param marketOrder the market order to be removed
     */
    public void remove (final MarketOrder marketOrder);

    /**
     * Get the last sell before a given index
     * @param beforeIndex the index
     * @param trading the trading
     * @return market order of the last sell
     */
    public MarketOrder getLastSell (final Integer beforeIndex, final Trading trading);

    /**
     * Get the last buy before a given index
     * @param beforeIndex the index
     * @param trading the trading
     * @return market order of the last buy
     */
    public MarketOrder getLastBuy (final Integer beforeIndex, final Trading trading);


    /**
     * Get the open orders of a given trading
     * @param trading the trading
     * @return the open order of a trading
     */
    public List<MarketOrder> getOpenOrders (final Trading trading);


    /**
     * Get the open manually created sell orders of a given trading
     * @param trading the trading
     * @return the open manually created sell orders of a trading
     */
    public List<MarketOrder> getOpenManualSellOrders (final Trading trading);


    /**
     * Get the open manually created buy orders of a given trading
     * @param trading the trading
     * @return the open manually created buy orders of a trading
     */
    public List<MarketOrder> getOpenManualBuyOrders (final Trading trading);

    /**
     * Get the open orders with retry status of a given trading
     * @param trading the trading
     * @return the open orders with retry status of a trading
     */
    public List<MarketOrder> getRetryOrders (final Trading trading);


    /**
     * Get all orders of a  given trading
     * @param trading the trading
     * @return the orders of a trading
     */
    public List<MarketOrder> getAll (final Trading trading);

}
