package com.crypto.entities;

import com.crypto.enums.MarketOrderStatus;
import com.crypto.enums.OrderType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * A buy market order, buying crypto coins with coins.
 *
 * Created by jan on 19-4-15.
 */
@Entity
@DiscriminatorValue("BUY")
public class BuyMarketOrder extends MarketOrder {

    @Column(name="COINS")
    private Float coins;

    public BuyMarketOrder(Integer index, String orderReference, Trading trading, Timestamp timestamp, Float exchangeRate, MarketOrderStatus status, Integer retryCount, Boolean manuallyCreated, Float coins) {
        super(index, orderReference, trading, timestamp, exchangeRate, status, retryCount, manuallyCreated);
        this.coins = coins;
    }

    /**
     * Default constructor;
     */
    public BuyMarketOrder() {
        super();
    }

    public void calculateFee () {

        final Float feePercentage = this.getPk().getTrading().getTradePair().getTransactionFee();

        final Float fee = this.getCoins() * feePercentage / 100;

        setFee(fee);
    }

    /**
     * Get the number of crypto coins that were bought with the given coins.
     *
     * @return the number of cryptocoins bought.
     */
    public Float getCryptoCoins () {

        return (this.getCoins() - getFee()) / getExchangeRate();
    }

    public Float getCoins() {
        return coins;
    }

    public void setCoins(Float coins) {
        this.coins = coins;
    }

    public String getOrderType () {

        return OrderType.BUY.toString().toLowerCase();
    }
}
