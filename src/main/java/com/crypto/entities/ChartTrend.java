package com.crypto.entities;

import javax.persistence.*;

/**
 * A trend on a chart
 *
 * Created by Jan Wicherink  on 3-9-15.
 */
@Entity
@Table(name="TRENDS_FOR_CHARTS")
public class ChartTrend {

    @Id
    private Integer id;

    @JoinColumn(name="TREND_ID", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Trend trend;

    @JoinColumn(name="MACD_ID", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Macd macd;

    @JoinColumn(name="CHART_ID", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Chart chart;

    /**
     * Default constructor
     */
    public ChartTrend() {

    }

    public Integer getId() {
        return id;
    }

    public Trend getTrend() {
        return trend;
    }

    public Macd getMacd() {
        return macd;
    }

    public Chart getChart() {
        return chart;
    }
}
