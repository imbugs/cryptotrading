package com.crypto.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by jan on 18-4-15.
 */
@Entity
@Table(name="LOGGING")
public class Logging {

    @Id
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID")
    private Trading trading;

    @Column(name="INDX")
    private Integer index;

    @Column(name="LEVEL")
    private String level;

    @Column (name="MESSAGE")
    private String messsage;

    /**
     * Constructor
     * @param timestamp timestamp of the logging
     * @param trading the trading the logging concerns
     * @param index the index of the logging
     * @param level the logging leve
     * @param messsage the logging message
     */
    public Logging(Timestamp timestamp, Trading trading, Integer index, String level, String messsage) {
        this.timestamp = timestamp;
        this.trading = trading;
        this.index = index;
        this.level = level;
        this.messsage = messsage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Trading getTrading() {
        return trading;
    }

    public Integer getIndex() {
        return index;
    }

    public String getLevel() {
        return level;
    }

    public String getMesssage() {
        return messsage;
    }
}
