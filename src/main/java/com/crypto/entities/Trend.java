package com.crypto.entities;


import com.crypto.enums.TrendType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A trend is currency exchange rate development
 * Created by Jan Wicherink on 25-3-2015.
 */
@Entity
@Table(name="TRENDS")
public class Trend implements Serializable {

    private static final long serialVersionUID = 5445153257648020272L;

    @Id
    @GeneratedValue
    @Column (name = "ID")
    private Integer id;

    @Column (name = "TREND_TYPE")
    @Enumerated(EnumType.STRING)
    private TrendType type;

    @Column (name = "PERIOD")
    private Integer period;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="SMOOTHING_TREND_ID", nullable=true, updatable=true)
    private Trend smoothingTrend;

    /**
     * Default constructor
     */
    public Trend () {

    }

    /**
     * Constructor
     *
     * @param id             identification
     * @param type           trend type
     * @param period         period given for the trend
     * @param smoothingTrend the smoothing trend
     */
    public Trend(Integer id, TrendType type, Integer period, Trend smoothingTrend) {
        this.id = id;
        this.type = type;
        this.period = period;
        this.smoothingTrend = smoothingTrend;
    }

    /**
     * Returns the name of the trend
     *
     * @return the name of the trend
     */
    public String getName() {
        String name = "";
        String periodString;

        if (this.period != null) {
            periodString = this.period.toString();
        } else {
            periodString = "?";
        }

        switch (this.type) {
            case MA:
            case EMA:
                name = this.type.toString() + periodString;
                break;

            case SMA:
                final String smaName = this.type.toString() + periodString;
                final String trendNam;

                if (this.smoothingTrend != null) {
                    trendNam = this.getSmoothingTrend().getName();
                }
                else {
                    trendNam = "?";
                }
                name = smaName + trendNam;
                break;
        }

        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TrendType getType() {
        return type;
    }

    public Integer getPeriod() {
        return period;
    }

    public Trend getSmoothingTrend() {
        return smoothingTrend;
    }

    public void setSmoothingTrend(Trend smoothingTrend) {
        this.smoothingTrend = smoothingTrend;
    }
}
