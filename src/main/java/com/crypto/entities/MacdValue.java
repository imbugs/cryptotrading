package com.crypto.entities;

import com.crypto.datahandler.provider.DataIndexProvider;

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
public class MacdValue  implements DataIndexProvider{

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
    @JoinColumn(name="MACD_ID", nullable = true, updatable = true)
    private Macd macd;

    @Column(name="VALUE")
    private Float value;

    @Column(name="DELTA")
    private Float delta;

    /**
     * Constructor
     * @param indx      the index
     * @param macd      the macd of this cryptocointrend value
     * @param tradePair the tradepair
     */
    public MacdValue(final Integer indx, final Macd macd, final TradePair tradePair, final Float value, final Float delta) {

        this.indx = indx;
        this.tradePair = tradePair;
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

    public void setValue(Float value) {
        this.value = value;
    }

    public void setDelta(Float delta) {
        this.delta = delta;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Override
    public Integer getIndex() {
        return this.indx;
    }
}
