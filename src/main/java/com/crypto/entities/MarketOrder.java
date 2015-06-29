package com.crypto.entities;

import com.crypto.entities.pkey.OrderPk;
import com.crypto.enums.MarketOrderStatus;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * A market order, order to immediately sell or buy a position at at given exchange rate.
 *
 * Created by Jan Wicherink on 19-4-15.
 */
@Entity
@Table (name="MARKET_ORDERS")
@DiscriminatorColumn(name = "ORDER_TYPE", discriminatorType = DiscriminatorType.STRING)
public class MarketOrder {

    @EmbeddedId
    private OrderPk pk;

    @Column (name="ORDER_REFERENCE")
    private String orderReference;

    @Column (name="TIMESTAMP")
    private Timestamp timestamp;

    @Column (name="EXCHANGE_RATE")
    private Float exchangeRate;

    @Column (name="FEE")
    private Float fee;

    @Column (name="STATUS")
    @Enumerated(value = EnumType.STRING)
    private MarketOrderStatus status;

    @Column (name="RETRY_COUNT")
    private Integer retryCount;

    @Column (name="MANUALLY_CREATED")
    private Boolean manuallyCreated;

    /**
     * Constructor
     * @param index the index of the order
     * @param orderReference our order reference of the order
     * @param trading the util
     * @param timestamp the timestamp of the order
     * @param exchangeRate the exchange rate at which to sell or buy
     * @param status the status of the order
     * @param retryCount the number of retries to execute this order
     * @param manuallyCreated indicator if the order was manually created
     */
    public MarketOrder(Integer index, String orderReference, Trading trading, Timestamp timestamp, Float exchangeRate, MarketOrderStatus status, Integer retryCount, Boolean manuallyCreated) {
        OrderPk orderPk = new OrderPk(index,trading);
        this.pk = orderPk;
        this.orderReference = orderReference;
        this.timestamp = timestamp;
        this.exchangeRate = exchangeRate;
        this.status = status;
        this.retryCount = retryCount;
        this.manuallyCreated = manuallyCreated;
    }

    public MarketOrder() {

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

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public OrderPk getPk() {
        return pk;
    }

    public Integer getIndex() {
        return getPk().getIndex();
    }
}
