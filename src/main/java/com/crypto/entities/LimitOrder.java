package com.crypto.entities;

import com.crypto.entities.pkey.OrderPk;
import com.crypto.enums.LimitOrderStatus;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * A limit order, intended to sell of buy at a given exchange rate, but excuted when the exchange rate exceeds a given limit
 * in order to limit losses
 *
 * Created by Jan Wicherink on 19-4-15.
 */
@Entity
@Table (name="LIMIT_ORDERS")
@DiscriminatorColumn(name = "ORDER_TYPE", discriminatorType = DiscriminatorType.STRING)
public class LimitOrder {

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
    private LimitOrderStatus status;

    @Column (name="RETRY_COUNT")
    private Integer retryCount;

    @Column (name="MANUALLY_CREATED")
    private Boolean manuallyCreated;

    @Column(name="STOPLOSS_RATE")
    private Float stopLossRate;

    @Column (name="TIMESTAMP_END_OF_ORDER")
    private Timestamp timestampEndOfOrder;

    /**
     * Constructor
     * @param index the index of the order
     * @param orderReference our order reference of the order
     * @param trading the util
     * @param timestamp the timestamp of the order
     * @param exchangeRate the exchange rate at which to sell or buy
     * @param fee the fee to be payed
     * @param status the status of the order
     * @param retryCount the number of retries to execute this order
     * @param manuallyCreated indicator if the order was manually created
     * @param stopLossRate exchange rate to sell or buy the order to limit the loss
     * @param timestampEndOfOrder closing timestamp of the order
     */
    public LimitOrder(String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, LimitOrderStatus status, Integer retryCount, Boolean manuallyCreated, Float stopLossRate, Timestamp timestampEndOfOrder) {

        this.pk = new OrderPk(index, trading);
        this.orderReference = orderReference;
        this.timestamp = timestamp;
        this.exchangeRate = exchangeRate;
        this.fee = fee;
        this.status = status;
        this.retryCount = retryCount;
        this.manuallyCreated = manuallyCreated;
        this.stopLossRate = stopLossRate;
        this.timestampEndOfOrder = timestampEndOfOrder;
    }

    /**
     * Default constructor
     */
    public LimitOrder () {

    }

    public String getOrderReference() {
        return orderReference;
    }

    public Trading getTrading() {
        return pk.getTrading();
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

    public LimitOrderStatus getStatus() {
        return status;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public Boolean getManuallyCreated() {
        return manuallyCreated;
    }

    public Float getStopLossRate() {
        return stopLossRate;
    }

    public Timestamp getTimestampEndOfOrder() {
        return timestampEndOfOrder;
    }

    public OrderPk getPk() {
        return pk;
    }

    public Integer getIndex() {
        return getPk().getIndex();
    }
}
