package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * Created by Jan Wicherink on 19-4-15.
 */
@Entity
public class Order {

    @Column (name="ORDER_REFERENCE")
    private String orderReference;

    @Column (name="INDX")
    private Integer index;

    @Column (name="TRADING_ID")
    private Trading trading;

    @Column (name="TIMESTAMP")
    private Timestamp timestamp;

    @Column (name="EXCHANGE_RATE")
    private Float exchangeRate;

    @Column (name="FEE")
    private Float fee;

    @Column (name="STATUS")
    private String status;

    @Column (name="RETRY_COUNT")
    private Integer retryCount;

    @Column (name="MANUALLY_CREATED")
    private Boolean manuallyCreated;

    public Order(String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated) {
        this.orderReference = orderReference;
        this.index = index;
        this.trading = trading;
        this.timestamp = timestamp;
        this.exchangeRate = exchangeRate;
        this.fee = fee;
        this.status = status;
        this.retryCount = retryCount;
        this.manuallyCreated = manuallyCreated;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public Integer getIndex() {
        return index;
    }

    public Trading getTrading() {
        return trading;
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

    public String getStatus() {
        return status;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public Boolean isManuallyCreated() {
        return manuallyCreated;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }
}
