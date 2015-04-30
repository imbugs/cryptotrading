package com.crypto.entities.pkey;

import com.crypto.entities.Currency;
import com.crypto.entities.TradePair;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Primary key of Fund
 *
 * Created by Jan Wicherink on 30-4-15.
 */
@Embeddable
public class FundPk implements Serializable {

    private static final long serialVersionUID = -1750502535249379300L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRADE_PAIR_ID", nullable=false, updatable=false)
    private TradePair tradepair;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "CURRENCY", nullable=false, updatable=false)
    private Currency currency;

    /**
     * Constructor
     *
     * @param tradepair  the tradepair of the fund
     * @param currency the currency of the fund
     */
    public FundPk(TradePair tradepair, Currency currency) {
        this.tradepair = tradepair;
        this.currency = currency;
    }

    public TradePair getTradepair() {
        return tradepair;
    }

    public Currency getCurrency() {
        return currency;
    }
}
