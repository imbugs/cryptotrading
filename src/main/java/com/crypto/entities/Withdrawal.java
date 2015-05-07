package com.crypto.entities;

import com.crypto.entities.pkey.WithdrawalPk;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Withdrawal of a fund
 * <p/>
 * Created by Jan Wicherink  on 7-5-15.
 */
@Entity
@Table(name = "WITHDRAWALS")
public class Withdrawal {

    @EmbeddedId
    private WithdrawalPk pk;

    @Column(name = "COINS")
    private Float coins;

    /**
     * Constructor
     *
     * @param trading  the trading
     * @param coins    number of coins of the withdrawal
     * @param currency the currency of the coins that are withdrawn
     */
    public Withdrawal(Trading trading, Float coins, Currency currency) {
        this.coins = coins;
        this.pk = new WithdrawalPk(trading, currency);
    }

    /**
     * Default constructor
     */
    public Withdrawal() {

    }

    public Trading getTrading() {
        return pk.getTrading();
    }

    public Float getCoins() {
        return coins;
    }

    public Currency getCurrency() {
        return pk.getCurrency();
    }
}
