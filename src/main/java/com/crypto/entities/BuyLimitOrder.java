package com.crypto.entities;

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

    /**
     * Constructor
     *
     * @param id                  the id
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
     */
    public BuyLimitOrder(Integer id, String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated, Float stopLossRate, Timestamp timestampEndOfOrder) {
        super(id, orderReference, index, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated, stopLossRate, timestampEndOfOrder);
    }
}