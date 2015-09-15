package com.crypto.entities;


import com.crypto.enums.LoggingLevel;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Logging
 *
 * Created by jan on 18-4-15.
 */
@Entity
@Table (name="LOGGING")
public class Logging {

    @Id
    @GeneratedValue
    private Long Id;

    @Column(name="TIMESTAMP")
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TRADING_ID")
    private Trading trading;

    @Column(name="INDX")
    private Integer index;

    @Column(name="LEVEL")
    private String level;

    @Column (name="MESSAGE")
    private String message;

    /**
     * Constructor
     * @param timestamp timestamp of the logging
     * @param trading the util the logging concerns
     * @param index the index of the logging
     * @param level the logging level
     * @param message the logging message
     */
    public Logging(Timestamp timestamp, Trading trading, Integer index, LoggingLevel level, String message) {
        this.timestamp = timestamp;
        this.trading = trading;
        this.index = index;
        this.level = level.name();
        this.message = message;
    }

    public Logging() {
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

    public String getMessage() {
        return message;
    }
}
