package com.crypto.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A trend is currency exchange rate development
 * <p/>
 * Created by Jan Wicherink on 25-3-2015.
 */

public class Trend {
    private Integer id;
    private TrendType type;
    private Integer period;
    private Trend smoothingTrend;

    /**
     * Consructor
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
        String periodString = "";

        if (this.period != null) {
            periodString = this.period.toString();
        } else {
            periodString = "?";
        }

        switch (this.type) {
            case MOVING_AVERAGE:
            case EXPONENTIAL_MOVING_AVERAGE:
                name = this.type.toString() + periodString;
                break;

            case SMOOTHING_MOVING_AVERAGE:
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
