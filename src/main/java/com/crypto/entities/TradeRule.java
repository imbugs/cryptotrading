package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Trade rule, a rule for trading
 *
 * Created by Jan Wicherink on 29-4-2015.
 */

@Entity
public class TradeRule {

    @Id
    @Column (name="ID")
    private Integer Id;

    @Column(name="TYPE")
    private MarketEnum type;

    @Column(name="MARKET") 
    private MarketEnum market;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name = "ENABLED")
    private Boolean enabled;

    /**
     * Constructor
     * @param id trade rule identification
     * @param type the type of market
     * @param market the market of the trade rule
     * @param description description of the trade rule
     * @param enabled true when activated, false otherwise
     */
    public TradeRule(final Integer id, final MarketEnum type, final MarketEnum market, final String description, final Boolean enabled) {
        Id = id;
        this.type = type;
        this.market = market;
        this.description = description;
        this.enabled = enabled;
    }

    public Integer getId() {
        return Id;
    }

    public MarketEnum getType() {
        return type;
    }

    public MarketEnum getMarket() {
        return market;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
