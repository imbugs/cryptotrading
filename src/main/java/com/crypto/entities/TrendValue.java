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
@DiscriminatorValue(value = "TREND")
public class TrendValue {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name="TRADE_PAIR_ID")
    private TradePair tradePair;

    @Column(name="INDX")
    private Integer indx;

    @ManyToOne
    @JoinColumn(name="TREND_ID", nullable = true, updatable = true)
    private Trend trend;

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
     * @param tradePair the tradepair
     * @param indx      the index
     * @param trend     the trend of this cryptocoin trend value
     * @param macd      the macd of this cryptocoin trend value
     * @param value     the value of the trend
     * @param delta     the delta value of trend with respect to the previous trend value with index = indx-1
     */
    public TrendValue(TradePair tradePair, Integer indx, Trend trend, Macd macd, Float value, Float delta) {
        this.tradePair = tradePair;
        this.indx = indx;
        this.trend = trend;
        this.macd  = macd;
        this.value = value;
        this.delta = delta;
    }

    /**
     * Default constructor
     */
    public TrendValue() {

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

    public Trend getTrend() {
        return trend;
    }

    public Float getValue() {
        return value;
    }

    public Float getDelta() {
        return delta;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public void setDelta(Float delta) {
        this.delta = delta;
    }
}
