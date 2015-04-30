package com.crypto.entities.pkey;

import com.crypto.entities.Trading;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Composite primary key of Signal
 *
 * Created by Jan Wicherink on 30-4-15.
 */
@Embeddable
public class SignalPk implements Serializable{

    private static final long serialVersionUID = -2786111705483238963L;

    @Column(name="INDX")
    private Integer index;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID", nullable = false, updatable = true)
    private Trading trading;

    /**
     * Constructor
     *
     * @param index the index
     * @param trading the trading
     */
    public SignalPk(Integer index, Trading trading) {
        this.index = index;
        this.trading = trading;
    }

    /**
     * Default constructor
     */
    public SignalPk () {

    }

    public Integer getIndex() {
        return index;
    }

    public Trading getTrading() {
        return trading;
    }
}
