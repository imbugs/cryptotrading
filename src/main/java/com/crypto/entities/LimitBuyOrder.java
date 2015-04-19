package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Jan Wicherink on 19-4-15.
 */
@Entity
@Table(name="LIMIT_ORDERS")
public class LimitBuyOrder extends BuyOrder {

    @Column(name="STOPLOSS_RATE")
    private Float stopLossRate;

    @Column (name="TIMESTAMP_END_OF_ORDER")
    private Timestamp timestampEndOfOrder;


    public LimitBuyOrder(String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated, Float coins, Float stopLossRate, Timestamp timestampEndOfOrder) {
        super(orderReference, index, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated, coins);

        this.stopLossRate = stopLossRate;

        this.timestampEndOfOrder = timestampEndOfOrder;
    }


    public Float getStopLossRate() {
        return stopLossRate;
    }

    public void setStopLossRate(Float stopLossRate) {
        this.stopLossRate = stopLossRate;
    }


    public Timestamp getTimestampEndOfOrder() {
        return timestampEndOfOrder;
    }

    public void setTimestampEndOfOrder(Timestamp timestampEndOfOrder) {
        this.timestampEndOfOrder = timestampEndOfOrder;
    }
}