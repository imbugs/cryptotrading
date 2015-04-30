package com.crypto.entities;

import javax.persistence.*;

/**
 * Trade rule, a rule for trading
 *
 * Created by Jan Wicherink on 29-4-2015.
 */

@Entity
@Table(name="TRADE_RULES")
public class TradeRule {

    @Id
    @Column (name="ID")
    private Integer Id;

    @Column(name="TYPE")
    @Enumerated (EnumType.STRING)
    private MarketTrend type;

    @Column(name="MARKET")
    @Enumerated (EnumType.STRING)
    private MarketTrend marketTrend;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name = "ENABLED")
    private Boolean enabled;

    /**
     * Constructor
     *
     * @param id trade rule identification
     * @param type the type of market
     * @param marketTrend the market of the trade rule
     * @param description description of the trade rule
     * @param enabled true when activated, false otherwise
     */
    public TradeRule(final Integer id, final MarketTrend type, final MarketTrend marketTrend, final String description, final Boolean enabled) {
        Id = id;
        this.type = type;
        this.marketTrend = marketTrend;
        this.description = description;
        this.enabled = enabled;
    }

    /**
     * Default constructor
     */
    public TradeRule (){

    }

    public Integer getId() {
        return Id;
    }

    public MarketTrend getType() {
        return type;
    }

    public MarketTrend getMarketTrend() {
        return marketTrend;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setType(MarketTrend type) {
        this.type = type;
    }

    public void setMarketTrend(MarketTrend marketTrend) {
        this.marketTrend = marketTrend;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
