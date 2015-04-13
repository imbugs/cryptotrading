package com.crypto.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Jan Wicherink on 31-3-2015.
 */
@Entity
@Table(name = "CRYPTOCOIN_HISTORY")
public class CryptocoinHistory {

    @Id
    @GeneratedValue
    @Column(name = "INDX")
    private Integer indx;

    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADE_PAIR_ID", nullable=false, updatable=false)
    private TradePair tradePair;

    @Column(name = "OPEN")
    private Float open;

    @Column(name = "LOW")
    private Float low;

    @Column(name = "HIGH")
    private Float high;

    @Column(name = "CLOSE")
    private Float close;

    @Column(name = "VOLUME")
    private Long volume;

    public CryptocoinHistory() {
    }

    /**
     * Cryptocoin history
     * @param indx the index of the history
     * @param timestamp the timestampt of the history
     * @param tradePair the tradepair belonging to the history
     * @param open the opening exchange rate
     * @param low the lowest exchange rate
     * @param high the highest exchange rate
     * @param close the closing exchange rate
     * @param volume the volume of the trading
     */
    public CryptocoinHistory(Integer indx, Date timestamp, TradePair tradePair, Float open, Float low, Float high, Float close, Long volume) {
        this.indx = indx;
        this.timestamp = timestamp;
        this.tradePair = tradePair;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
        this.volume = volume;
    }

    /**
     * Cryptocoin history
     * @param tradePair the tradepair belonging to the history
     * @param open the opening exchange rate
     * @param low the lowest exchange rate
     * @param high the highest exchange rate
     * @param close the closing exchange rate
     * @param volume the volume of the trading
     */
    public CryptocoinHistory(TradePair tradePair, Float open, Float low, Float high, Float close, Long volume) {
        this.tradePair = tradePair;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
        this.volume = volume;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Float getOpen() {
        return open;
    }

    public void setOpen(Float open) {
        this.open = open;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public TradePair getTradePair() {
        return tradePair;
    }

    public void setTradePair(TradePair tradePair) {
        this.tradePair = tradePair;
    }

    public Float getClose() {
        return close;
    }

    public void setClose(Float close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}
