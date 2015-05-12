package com.crypto.entities;

import com.crypto.datahandler.provider.DataIndexProvider;
import com.crypto.entities.pkey.CrytptocoinHistoryPk;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Jan Wicherink on 31-3-2015.
 */
@Entity
@Table(name = "CRYPTOCOIN_HISTORY")
public class CryptocoinHistory implements Serializable, DataIndexProvider {

    private static final long serialVersionUID = -7267559706573006370L;

    @EmbeddedId
    private CrytptocoinHistoryPk pk;

    @Column(name = "TIMESTAMP")
    private Timestamp timestamp;

    // Timestamp format with date and time
    public static String TIMESTAMP_FORMAT_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";

    // Timestamp format with date only
    public static String TIMESTAMP_FORMAT_DATE = "yyyy-MM-dd";

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
     *
     * @param indx      the index of the history
     * @param timestamp the timestamp of the history
     * @param tradePair the tradepair belonging to the history
     * @param open      the opening exchange rate
     * @param low       the lowest exchange rate
     * @param high      the highest exchange rate
     * @param close     the closing exchange rate
     * @param volume    the volume of the trading
     */
    public CryptocoinHistory(Integer indx, Timestamp timestamp, TradePair tradePair, Float open, Float low, Float high, Float close, Long volume) {

        this.pk = new CrytptocoinHistoryPk(indx, tradePair);

        this.timestamp = timestamp;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
        this.volume = volume;
    }

    @Override
    public Integer getIndex() {
        return this.pk.getIndx();
    }

    public TradePair getTradePair() {
        return this.pk.getTradePair();
    }

    public Float getOpen() {
        return open;
    }

    public Float getLow() {
        return low;
    }

    public Float getHigh() {
        return high;
    }

    public Float getClose() {
        return close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setOpen(Float open) {
        this.open = open;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public void setClose(Float close) {
        this.close = close;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}
