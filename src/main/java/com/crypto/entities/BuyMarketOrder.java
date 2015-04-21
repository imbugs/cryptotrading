package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by jan on 19-4-15.
 */
@Entity
@DiscriminatorValue("BUY")
public class BuyMarketOrder extends MarketOrder {

    @Column(name="COINS")
    private Float coins;

    public BuyMarketOrder(Integer id, Integer index, String orderReference, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated, Float coins) {
        super(id, index, orderReference, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated);
        this.coins = coins;
    }


    /**
     * Default constructor;
     */
    public BuyMarketOrder() {
        super();
    }

    public void calculateFee () {

        final Float feePercentage = this.getTrading().getTradePair().getTransactionFee();

        final Float fee = this.getCoins() * feePercentage / 100;

        setFee(fee);
    }

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
