package com.crypto.entities;

import javax.persistence.Entity;
import javax.persistence.*;

/**
 * Moving Average Convergence Divergence
 *
 * Created by Jan Wicherink on 1-5-15.
 */
@Entity
@Table (name="MACD")
public class Macd {

    @Id
    @Column(name = "ID")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "SHORT_TREND_ID")
    private Trend shortTrend;

    @ManyToOne
    @JoinColumn(name = "LONG_TREND_ID")
    private Trend longTrend;

    /**
     * Constructor
     *
     * @param id         the Id
     * @param shortTrend the short trend of the Macd
     * @param longTrend  the long trend of the Macd
     */
    public Macd(Integer id, Trend shortTrend, Trend longTrend) {
        Id = id;
        this.shortTrend = shortTrend;
        this.longTrend = longTrend;
    }

    /**
     * Default constructor
     */
    public Macd() {
    }

    public Integer getId() {
        return Id;
    }

    public Trend getShortTrend() {
        return shortTrend;
    }

    public Trend getLongTrend() {
        return longTrend;
    }


    public void setId(Integer id) {
        Id = id;
    }


    public String getName() {

        return shortTrend.getName() + '-' + longTrend.getName();
    }
}