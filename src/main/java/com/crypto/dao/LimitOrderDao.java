package com.crypto.dao;

import com.crypto.entities.BuyLimitOrder;
import com.crypto.entities.LimitOrder;
import com.crypto.entities.SellLimitOrder;
import com.crypto.entities.Trading;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jan Wicherink on 3-5-15.
 */
public interface LimitOrderDao extends Serializable {

    /**
     * Persist the limit order
     *
     * @param order the limit order
     */
    public void persist(LimitOrder order);


    /**
     * Get the limit orders with status open
     *
     * @param trading the trading of the limit orders
     * @return the open limit orders
     */
    public List<LimitOrder> getOpenLimitOrders(final Trading trading);


    /**
     * Get the total number of open limit orders
     *
     * @param trading the trading of the limit orders
     * @return the total number of open limit orders
     */
    public Long getNumberOfOpenLimitOrders(final Trading trading);


    /**
     * Get the last sell order before a given index
     *
     * @param beforeIndex the index
     * @param trading   the trading of the last sell
     * @return the last limit sell order before a given timestamp
     */
    public SellLimitOrder getLastSell(final Integer beforeIndex, final Trading trading);


    /**
     * Get the last buy order before a given timestamp
     *
     * @param beforeIndex the index
     * @param trading   the trading of the last sell
     * @return the last limit buy order before a given timestamp
     */
    public BuyLimitOrder getLastBuy(final Integer beforeIndex, final Trading trading);

    /**
     * Get the last sell before a given timestamp
     *
     * @param timestamp the timestamp
     * @param trading the trading of the last sell
     * @return the last limit order before a given timestamp
     */

    /**
     * Update the limit order
     *
     * @param limitOrder the limit order to be updates
     */
    public void update(final LimitOrder limitOrder);
}
