package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Market type;

    @Column(name="MARKET") 
    private Market market;

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
    public TradeRule(final Integer id, final Market type, final Market market, final String description, final Boolean enabled) {
        Id = id;
        this.type = type;
        this.market = market;
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

    public Market getType() {
        return type;
    }

    public Market getMarket() {
        return market;
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

    public void setType(Market type) {
        this.type = type;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
