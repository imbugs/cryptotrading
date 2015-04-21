package com.crypto.entities;

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

    @Id
    @Column(name="ID")
    private Integer Id;

    @Column (name="INDX")
    private Integer index;

    @Column (name="ORDER_REFERENCE")
    private String orderReference;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name="TRADING_ID", nullable = false, updatable = true)
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
     */
    public MarketOrder(Integer id, Integer index, String orderReference, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated) {
        Id = id;
        this.index = index;
        this.orderReference = orderReference;
        this.trading = trading;
        this.timestamp = timestamp;
        this.exchangeRate = exchangeRate;
        this.fee = fee;
        this.status = status;
        this.retryCount = retryCount;
        this.manuallyCreated = manuallyCreated;
    }

    public MarketOrder() {

    }

    public Integer getId() {
        return Id;
    }

    public Integer getIndex() {
        return index;
    }

    public String getOrderReference() {
        return orderReference;
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

    public void setFee(Float fee) {
        this.fee = fee;
    }
}
