package com.crypto.entities;

import javax.persistence.*;

/**
 * The CryptocoinTrend stores the trend and macd values calculated on the crypto coin rate developement.
 * It stores teh actual trend (macd) value along with the delta value (increase or decrease) with respect
 * to the previous value
 *
 * Created by Jan Wicherink on 1-5-15.
 */
@Entity
@Table(name="CRYPTOCOIN_TRENDS")
@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(value = "MACD")
public class MacdValue {

    @Id
    @Column(name="ID")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name="TRADE_PAIR_ID")
    private TradePair tradePair;

    @Column(name="INDX")
    private Integer indx;

    @ManyToOne
    @JoinColumn(name="MACD_ID", nullable = true, updatable = true)
    private Macd macd;

    @Column(name="VALUE")
    private Float value;

    @Column(name="DELTA")
    private Float delta;

    /**
     * Constructor
     *
     * @param id        the Id
     * @param tradePair the tradepair
     * @param indx      the index
     * @param macd      the macd of this cryptocointrend value
     * @param value     the value of the trend
     * @param delta     the delta value of trend with respect to the previous trend value with index = indx-1
     */
    public MacdValue(Integer id, TradePair tradePair, Integer indx, Trend trend, Macd macd, Float value, Float delta) {
        Id = id;
        this.tradePair = tradePair;
        indx = indx;
        this.macd = macd;
        this.value = value;
        this.delta = delta;
    }

    /**
     * Default constructor
     */
    public MacdValue() {

    }

    public Integer getId() {
        return Id;
    }

    public TradePair getTradePair() {
        return tradePair;
    }

    public Integer getIndx() {
        return indx;
    }

    public Macd getMacd() {
        return macd;
    }

    public Float getValue() {
        return value;
    }

    public Float getDelta() {
        return delta;
    }
}
