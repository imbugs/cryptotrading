package com.crypto.entities.pkey;

import com.crypto.entities.Currency;
import com.crypto.entities.Trading;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Withdrawal primary key
 *
 * Created by Jan Wicherink on 7-5-15.
 */
@Embeddable
public class WithdrawalPk implements Serializable {

    private static final long serialVersionUID = 3874972700731289145L;

    @ManyToOne
    @JoinColumn(name="TRADING_ID")
    private Trading trading;

    @ManyToOne
    @JoinColumn(name="CURRENCY")
    private Currency currency;

    /**
     * Constructor
     * @param trading trading
     * @param currency currency of the trading
     */
    public WithdrawalPk(Trading trading, Currency currency) {
        this.trading = trading;
        this.currency = currency;
    }

    /**
     * Default constructor
     */
    public WithdrawalPk () {

    }

    public Trading getTrading() {
        return trading;
    }

    public Currency getCurrency() {
        return currency;
    }
}
