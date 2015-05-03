package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * Created by Jan Wicherink on 20-4-15.
 */
@Entity
@DiscriminatorValue("SELL")
public class SellLimitOrder extends LimitOrder {

    @Column(name="CRYPTOCOINS")
    private Float cryptoCoins;

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
     * @param cryptoCoins         the number of cryptocoins sold
     */
    public SellLimitOrder(String orderReference, Integer index, Trading trading, Timestamp timestamp, Float exchangeRate, Float fee, String status, Integer retryCount, Boolean manuallyCreated, Float stopLossRate, Timestamp timestampEndOfOrder, Float cryptoCoins) {
        super(orderReference, index, trading, timestamp, exchangeRate, fee, status, retryCount, manuallyCreated, stopLossRate, timestampEndOfOrder);
        this.cryptoCoins = cryptoCoins;
    }

    /**
     * Default constructor
     */
    public SellLimitOrder() {
        super();
    }

    public Float getCryptoCoins() {
        return cryptoCoins;
    }
}
