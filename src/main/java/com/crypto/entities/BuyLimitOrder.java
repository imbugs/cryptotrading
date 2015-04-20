package com.crypto.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * Created by jan on 20-4-15.
 */
@Entity
@DiscriminatorValue("BUY")
public class BuyLimitOrder extends LimitOrder {

    public BuyLimitOrder(String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated, Float stopLossRate, Timestamp timestampEndOfOrder) {
        super(orderReference, index, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated, stopLossRate, timestampEndOfOrder);
    }
}
