package com.crypto.entities;

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

    @Id
    @Column (name="ID")
    private Integer Id;

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

    @Column(name="STOPLOSS_RATE")
    private Float stopLossRate;

    @Column (name="TIMESTAMP_END_OF_ORDER")
    private Timestamp timestampEndOfOrder;

    /**
     * Constructor
     * @param id the id
     * @param index the index of the order
     * @param orderReference our order reference of the order
     * @param trading the trading
     * @param timestamp the timestamp of the order
     * @param exchangeRate the exchange rate at which to sell or buy
     * @param fee the fee to be payed
     * @param status the status of the order
     * @param retryCount the number of retries to execute this order
     * @param manuallyCreated indicator if the order was manually created
     * @param stopLossRate exchange rate to sell or buy the order to limit the loss
     * @param timestampEndOfOrder closing timestamp of the order
     */
    public LimitOrder(Integer id, String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated, Float stopLossRate, Timestamp timestampEndOfOrder) {
        Id = id;
        this.orderReference = orderReference;
        this.index = index;
        this.trading = trading;
        this.timestamp = timestamp;
        this.exchangeRate = exchangeRate;
        this.fee = fee;
        this.status = status;
        this.retryCount = retryCount;
        this.manuallyCreated = manuallyCreated;
        this.stopLossRate = stopLossRate;
        this.timestampEndOfOrder = timestampEndOfOrder;
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

    public Boolean getManuallyCreated() {
        return manuallyCreated;
    }

    public Float getStopLossRate() {
        return stopLossRate;
    }

    public Timestamp getTimestampEndOfOrder() {
        return timestampEndOfOrder;
    }
}
