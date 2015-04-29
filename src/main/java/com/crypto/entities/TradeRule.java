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
    private Integer Id;

    @Column(name="MARKET") 
    private String market;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name = "ENABLED")
    private Boolean enabled;

    /**
     * Constructor
     * @param id trade rule identification
     * @param market the market of the trade rule
     * @param description description of the trade rule
     * @param enabled true when activated, false otherwise
     */
    public TradeRule(Integer id, String market, String description, Boolean enabled) {
        Id = id;
        this.market = market;
        this.description = description;
        this.enabled = enabled;
    }

    public Integer getId() {
        return Id;
    }

    public String getMarket() {
        return market;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
