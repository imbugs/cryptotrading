package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a trading by the cryptocoin trader
 * Created by Jan Wicherink on 31-3-2015.
 */
@Entity
@Table(name="TRADINGS")
public class Trading implements Serializable {

    private static final long serialVersionUID = 5493213831662060441L;

    @Id
    @Column (name = "ID")
    private Integer id;

    // the minimum crypto coins accepted for trading
    @Column (name = "MIN_TRADING_CRYPTOCURRENCY")
    private Float minTradingCryptoCurrency;

    // the maximum percentage to be used while trading coins
    @Column (name = "MAX_TRADING_COINS_PERC")
    private Float maxTradingCoinsPerc;

    // The maximum percentage to be used while trading crypto coins
    @Column (name = "MAX_TRADING_CRYPTOCOINS_PERC")
    private Float maxTradingCryptoCoinsPerc;

    // The percentage to be refunded to the wallets when an trading has taken place
    @Column (name = "REFUND_PERC")
    private Float refundPercentage;

    // Check on bad buying situations while trading
    @Column (name = "CHECK_BAD_BUY")
    private Boolean checkBadBuy;

    // Check on bad selling situations while trading
    @Column (name = "CHECK_BAD_SELL")
    private Boolean checkBadSell;

    @Column (name = "CHECK_BAD_SELL_WALLET")
    private Boolean checkBadSellWallet;

    // The minimum profit desired when trading
    @Column (name = "MIN_PROFIT_PERCENTAGE")
    private Float minProfitPercentage;

    // Boolean indicating if the trading is enabled (active) or disabled (inactive)
    @Column (name = "ENABLED")
    private Boolean enabled;

    // Boolean indicating if logging is desired.
    @Column (name = "LOGGING")
    private Boolean logging;

    // The number of retries to sell of buy cryptocoins.
    @Column (name = "RETRIES")
    private Integer retries;

    // The trading pair.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "TRADE_PAIR_ID", nullable=false, updatable=false)
    private TradePair tradePair;

    /**
     * Default constructor
     */
    public Trading () {

    }


    public Trading(Integer id, Float minTradingCryptoCurrency, Float maxTradingCoinsPerc, Float maxTradingCryptoCoinsPerc, Float refundPercentage, Boolean checkBadBuy, Boolean checkBadSell, Boolean checkBadSellWallet, Float minProfitPercentage, Boolean enabled, Boolean logging, Integer retries, TradePair tradePair) {
        this.id = id;
        this.minTradingCryptoCurrency = minTradingCryptoCurrency;
        this.maxTradingCoinsPerc = maxTradingCoinsPerc;
        this.maxTradingCryptoCoinsPerc = maxTradingCryptoCoinsPerc;
        this.refundPercentage = refundPercentage;
        this.checkBadBuy = checkBadBuy;
        this.checkBadSell = checkBadSell;
        this.checkBadSellWallet = checkBadSellWallet;
        this.minProfitPercentage = minProfitPercentage;
        this.enabled = enabled;
        this.logging = logging;
        this.retries = retries;
        this.tradePair = tradePair;
    }

    public Float getMinTradingCryptoCurrency() {
        return minTradingCryptoCurrency;
    }

    public Float getMaxTradingCoinsPerc() {
        return maxTradingCoinsPerc;
    }

    public Float getMaxTradingCryptoCoinsPerc() {
        return maxTradingCryptoCoinsPerc;
    }

    public Float getRefundPercentage() {
        return refundPercentage;
    }

    public Boolean getCheckBadBuy() {
        return checkBadBuy;
    }

    public Boolean getCheckBadSell() {
        return checkBadSell;
    }

    public Boolean getCheckBadSellWallet() {
        return checkBadSellWallet;
    }

    public Float getMinProfitPercentage() {
        return minProfitPercentage;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getLogging() {
        return logging;
    }

    public Integer getRetries() {
        return retries;
    }

    public TradePair getTradePair() {
        return tradePair;
    }

    public Integer getId() {
        return id;
    }
}