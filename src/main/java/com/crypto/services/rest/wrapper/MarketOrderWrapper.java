package com.crypto.services.rest.wrapper;

import com.crypto.entities.BuyMarketOrder;
import com.crypto.entities.MarketOrder;
import com.crypto.entities.SellMarketOrder;
import com.crypto.enums.MarketOrderStatus;
import com.crypto.enums.OrderType;

import java.sql.Timestamp;

/**
 * Wrapper class for Market Order entities
 *
 * Created by Jan Wicherink on 22-8-15.
 */
public class MarketOrderWrapper {

    private Integer index;

    private String orderReference;

    private Timestamp timestamp;

    private Float exchangeRate;

    private Float fee;

    private MarketOrderStatus status;

    private Integer retryCount;

    private Boolean manuallyCreated;

    private OrderType type;

    private Float coins;

    private Float cryptoCoins;

    /**
     * Constructor, builds a MarketOrderModel from a MarketOrder
     * @param marketOrder the market order to be wrapped into a market order wrapper.
     */
    public MarketOrderWrapper(final MarketOrder marketOrder) {

        this.index = marketOrder.getIndex();
        this.orderReference = marketOrder.getOrderReference();
        this.timestamp = marketOrder.getTimestamp();
        this.exchangeRate = marketOrder.getExchangeRate();
        this.status =marketOrder.getStatus();
        this.retryCount = marketOrder.getRetryCount();
        this.manuallyCreated = marketOrder.getManuallyCreated();
        this.fee = marketOrder.getFee();

        if (marketOrder instanceof BuyMarketOrder) {
            this.type = OrderType.BUY;
            this.coins = ((BuyMarketOrder) marketOrder).getCoins();
            this.cryptoCoins = ((BuyMarketOrder) marketOrder).getCryptoCoins();
        }
        else {
            this.type = OrderType.SELL;
            this.coins = ((SellMarketOrder) marketOrder).getCoins();
            this.cryptoCoins = ((SellMarketOrder) marketOrder).getCryptoCoins();
        }
    }

    public Integer getIndex() {
        return index;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public Float getFee() {
        return fee;
    }

    public MarketOrderStatus getStatus() {
        return status;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public Boolean getManuallyCreated() {
        return manuallyCreated;
    }

    public OrderType getType() {
        return type;
    }

    public Float getCoins() {
        return coins;
    }

    public Float getCryptoCoins() {
        return cryptoCoins;
    }
}
