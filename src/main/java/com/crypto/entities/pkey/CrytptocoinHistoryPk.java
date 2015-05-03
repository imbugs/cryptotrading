package com.crypto.entities.pkey;

import com.crypto.entities.TradePair;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Composite primary key of Cryptocoin History
 *
 * Created by Jan Wicherink on 30-4-15.
 */
@Embeddable
public class CrytptocoinHistoryPk implements Serializable {


    private static final long serialVersionUID = 4524631471928884459L;

    @Column(name = "INDX")
    private Integer indx;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRADE_PAIR_ID", nullable = false, updatable = false)
    private TradePair tradePair;

    /**
     * Constructor
     *
     * @param indx index of the cryptocoin history
     * @param tradePair the tradepair of the cryptocoin history
     */
    public CrytptocoinHistoryPk(final Integer indx, final TradePair tradePair) {
        this.indx = indx;
        this.tradePair = tradePair;
    }

    /**
     * Default constructor
     */
    public CrytptocoinHistoryPk() {
    }

    public Integer getIndx() {
        return indx;
    }

    public TradePair getTradePair() {
        return tradePair;
    }
}
