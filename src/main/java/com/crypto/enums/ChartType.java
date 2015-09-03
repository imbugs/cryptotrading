package com.crypto.enums;

/**
 * Chart type
 *
 * Created by Jan Wicherink on 3-9-15.
 */
public enum ChartType {

    BTC ("BTC"),   // Cryto coin trend chart
    MACD("MACD"),  // Macd chart
    DELTA("DELTA");  // Delta chart

    private String type;

    /**
     * Constructor
     * @param type the chart type
     */
    ChartType(String type) {
        this.type = type;
    }

    @Override
    public String toString () {
        return this.type;
    }

}
