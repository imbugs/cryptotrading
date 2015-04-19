package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * Created by jan on 19-4-15.
 */
@Entity
public class BuyOrder extends Order {


    @Column(name="COINS")
    private Float coins;

    public BuyOrder(String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated, Float coins) {
        super(orderReference, index, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated);

        this.coins = coins;
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
