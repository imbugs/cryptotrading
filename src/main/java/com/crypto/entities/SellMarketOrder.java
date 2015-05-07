package com.crypto.entities;

import com.crypto.enums.MarketOrderStatus;
import com.crypto.enums.OrderType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * Created by Jan Wicherink on 19-4-15.
 */
@Entity
@DiscriminatorValue("SELL")
public class SellMarketOrder extends MarketOrder {

    @Column(name="CRYPTOCOINS")
    private Float cryptoCoins;

    public SellMarketOrder(Integer index, String orderReference, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, MarketOrderStatus status, Integer retryCount, Boolean manuallyCreated, Float cryptoCoins) {
        super(index, orderReference, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated);
        this.cryptoCoins = cryptoCoins;
    }

    public SellMarketOrder (){
        super();
    }

    public void calculateFee() {

        final Float feePercentage = this.getPk().getTrading().getTradePair().getTransactionFee();

        final Float value = getExchangeRate() * this.getCryptoCoins() - getFee();

        final Float fee = value * feePercentage / 100;

        setFee(fee);
    }

    public Float getCoins() {

        return (this.getCryptoCoins() * getExchangeRate()) - getFee();
    }


    public Float getCryptoCoins () {
        return this.cryptoCoins;
    }

    public void setCryptoCoins(Float cryptoCoins) {
        this.cryptoCoins = cryptoCoins;
    }

    public String getOrderType () {

        return OrderType.SELL.toString().toLowerCase();
    }
}