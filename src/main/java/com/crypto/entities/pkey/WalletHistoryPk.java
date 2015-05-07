package com.crypto.entities.pkey;

import com.crypto.entities.Trading;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Wallet history primary key
 *
 * Created by Jan Wicherink on 7-5-15.
 */
@Embeddable
public class WalletHistoryPk implements Serializable {

    private static final long serialVersionUID = -2192527279820137680L;

    @Column(name="TIMESTAMP")
    private Timestamp timestamp;

    // The trading of this wallet
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID")
    private Trading trading;

    /**
     * Constructor
     * @param timestamp timestamp
     * @param trading trading
     */
    public WalletHistoryPk(Timestamp timestamp, Trading trading) {
        this.timestamp = timestamp;
        this.trading = trading;
    }

    /**
     * Default constructor
     */
    public WalletHistoryPk() {

    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Trading getTrading() {
        return trading;
    }
}
