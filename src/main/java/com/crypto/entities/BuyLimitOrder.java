package com.crypto.entities;

import com.crypto.enums.LimitOrderStatus;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * Buy limit order, a buy order with an exchange rate limit.
 * Created by jan on 20-4-15.
 */
@Entity
@DiscriminatorValue("BUY")
public class BuyLimitOrder extends LimitOrder {

    @Column(name="COINS")
    private Float coins;

    /**
     * Constructor
     *
     * @param orderReference      our order reference of the order
     * @param index               the index of the order
     * @param trading             the trading
     * @param timestamp           the timestamp of the order
     * @param exchangeRate        the exchange rate at which to sell or buy
     * @param fee                 the fee to be payed
     * @param status              the status of the order
     * @param retryCount          the number of retries to execute this order
     * @param manuallyCreated     indicator if the order was manually created
     * @param stopLossRate        exchange rate to sell or buy the order to limit the loss
     * @param timestampEndOfOrder closing timestamp of the order
     * @param coins               the number of coins used to byy the limit order
     */
    public BuyLimitOrder(String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, LimitOrderStatus status, Integer retryCount, Boolean manuallyCreated, Float stopLossRate, Timestamp timestampEndOfOrder, Float coins) {
        super(orderReference, index, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated, stopLossRate, timestampEndOfOrder);
        this.coins = coins;
    }

    /**
     * Default constructor
     */
    public BuyLimitOrder() {
        super();
    }

    public Float getCoins() {
        return coins;
    }
}
