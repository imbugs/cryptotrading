package com.crypto.services.rest.wrapper;

import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

/**
 * Macd data wrapper class
 * Created by Jan Wicherink on 2-9-15.
 */
public class ChartDataWrapper {

    private List<Float> valueList;

    private Integer minX;

    private Integer maxX;

    private Float minY;

    private Float maxY;

    private String label;

    /**
     * Constructor
     *
     * @param valueList list of Values
     * @param minX      minimal index value in macdList
     * @param maxX      maximal index value in macdList
     * @param label     the label of this macd in chart
     */
    public ChartDataWrapper(List<Float> valueList, Integer minX, Integer maxX, String label) {
        this.valueList = valueList;
        this.minX = minX;
        this.maxX = maxX;
        this.label = label;

        // Determine the extreme values;
        this.determineExtremes();
    }

    public List<Float> getValueList() {
        return valueList;
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

    public String getLabel() {
        return label;
    }

    /**
     * Determine Y value extremes
     */
    private void determineExtremes() {
        this.minY = this.valueList.stream().min(Float::compare).get() * 0.999F;
        this.maxY = this.valueList.stream().max(Float::compare).get() * 1.001F;
    }
}
