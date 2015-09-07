package com.crypto.services.rest.wrapper;

import java.util.List;

/**
 * Chart data wrapper
 * wraps all the data to be shown on the chart.
 *
 * Created by Jan Wicherink on 2-9-15.
 */
public class ChartDataWrapper {

    private List<Float> valueList;

    private String label;

    private Integer minX;

    private Integer maxX;

    private Float minY;

    private Float maxY;

    /**
     * Constructor
     *
     * @param valueList list of values to be displayed on the chart.
     * @param label  the legend label.
     * @param minX      minimal index value on chart.
     * @param maxX      maximal index value on chart.
     */
    public ChartDataWrapper(List<Float> valueList, String label, Integer minX, Integer maxX) {
        this.valueList = valueList;
        this.label = label;
        this.minX = minX;
        this.maxX = maxX;

        // Determine the extreme values;
        this.determineYaxisExtremes();
    }

    public Integer getMinX() {
        return minX;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public Float getMinY() {
        return minY;
    }

    public Float getMaxY() {
        return maxY;
    }

    /**
     * Determine Y value extremes of the Y axis
     */
    private void determineYaxisExtremes() {
        this.minY = this.valueList.stream().min(Float::compare).get() * 0.999F;
        this.maxY = this.valueList.stream().max(Float::compare).get() * 1.001F;
    }

    public List<Float> getValueList() {
        return valueList;
    }

    public String getLabel() {
        return label;
    }
}

