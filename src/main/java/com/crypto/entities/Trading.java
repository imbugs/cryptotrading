package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a util by the cryptocoin trader
 * Created by Jan Wicherink on 31-3-2015.
 */
@Entity
@Table(name="TRADINGS")
public class Trading implements Serializable {

    private static final long serialVersionUID = 5493213831662060441L;

    @Id
    @Column (name = "ID")
    private Integer id;

    // the minimum crypto coins accepted for util
    @Column (name = "MIN_TRADING_CRYPTOCURRENCY")
    private Float minTradingCryptoCurrency;

    // the maximum percentage to be used while util coins
    @Column (name = "MAX_TRADING_COINS_PERC")
    private Float maxTradingCoinsPerc;

    // The maximum percentage to be used while util crypto coins
    @Column (name = "MAX_TRADING_CRYPTOCOINS_PERC")
    private Float maxTradingCryptoCoinsPerc;

    // The percentage to be refunded to the wallets when an util has taken place
    @Column (name = "REFUND_PERC")
    private Float refundPercentage;

    // Check on bad buying situations while util
    @Column (name = "CHECK_BAD_BUY")
    private Boolean checkBadBuy;

    // Check on bad selling situations while util
    @Column (name = "CHECK_BAD_SELL")
    private Boolean checkBadSell;

    @Column (name = "CHECK_BAD_SELL_WALLET")
    private Boolean checkBadSellWallet;

    // The minimum profit desired when util
    @Column (name = "MIN_PROFIT_PERCENTAGE")
    private Float minProfitPercentage;

    // Boolean indicating if the util is enabled (active) or disabled (inactive)
    @Column (name = "ENABLED")
    private Boolean enabled;

    // Boolean indicating if logging is desired.
    @Column (name = "LOGGING")
    private Boolean logging;

    // The number of retries to sell of buy cryptocoins.
    @Column (name = "RETRIES")
    private Integer retries;

    // The util pair.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "TRADE_PAIR_ID", nullable=false, updatable=false)
    private TradePair tradePair;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="TRADE_RULES_FOR_TRADING",
            joinColumns={@JoinColumn(name="TRADING_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="TRADE_RULE_ID", referencedColumnName="ID")})
    private List<TradeRule> tradeRules;

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

    public void setId(Integer id) {
        this.id = id;
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


    public void setMinTradingCryptoCurrency(Float minTradingCryptoCurrency) {
        this.minTradingCryptoCurrency = minTradingCryptoCurrency;
    }

    public void setMaxTradingCoinsPerc(Float maxTradingCoinsPerc) {
        this.maxTradingCoinsPerc = maxTradingCoinsPerc;
    }

    public void setMaxTradingCryptoCoinsPerc(Float maxTradingCryptoCoinsPerc) {
        this.maxTradingCryptoCoinsPerc = maxTradingCryptoCoinsPerc;
    }

    public void setRefundPercentage(Float refundPercentage) {
        this.refundPercentage = refundPercentage;
    }

    public void setCheckBadBuy(Boolean checkBadBuy) {
        this.checkBadBuy = checkBadBuy;
    }

    public void setCheckBadSell(Boolean checkBadSell) {
        this.checkBadSell = checkBadSell;
    }

    public void setCheckBadSellWallet(Boolean checkBadSellWallet) {
        this.checkBadSellWallet = checkBadSellWallet;
    }

    public void setMinProfitPercentage(Float minProfitPercentage) {
        this.minProfitPercentage = minProfitPercentage;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setLogging(Boolean logging) {
        this.logging = logging;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public void setTradePair(TradePair tradePair) {
        this.tradePair = tradePair;
    }

    public List<TradeRule> getTradeRules() {
        return tradeRules;
    }
}