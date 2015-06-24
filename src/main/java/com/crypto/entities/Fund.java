package com.crypto.entities;

import com.crypto.entities.pkey.FundPk;

import javax.persistence.*;

/**
 * The fund represents the funding of a util
 * Created by Jan Wicherink on 16-4-15.
 */
@Entity
@Table(name="FUNDS")
public class Fund {

    @EmbeddedId
    private FundPk pk;

    @Column(name="COINS")
    private Float coins;

    public Fund(TradePair tradepair, Float coins, Currency currency) {

        this.pk    = new FundPk(tradepair, currency);
        this.coins = coins;
    }

    public Fund() {
    }

    public void addCoins(final Float coinsToBeAdded)
    {
        this.coins = this.coins + coinsToBeAdded;
    }

    public void subtractCoins(final Float coinsToBeSubtracted)
    {
        this.coins = this.coins - coinsToBeSubtracted;
    }

    public TradePair getTradepair() {
        return this.pk.getTradepair();
    }

    public Float getCoins() {
        return coins;
    }

    public void setCoins(Float coins) {
        this.coins = coins;
    }

    public Currency getCurrency() {
        return this.pk.getCurrency();
    }
}
