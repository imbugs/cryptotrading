package com.crypto.services.rest.wrapper;

import java.util.List;

/**
 * Chart data wrapper
 * wraps all the data to be shown on the chart.

 *
 * Created by Jan Wicherink on 2-9-15.
 */
public class ChartDataWrapper {

    private List<List<Float>> valueLists;

    private List<Label> labels;

    private Integer minX;

    private Integer maxX;

    private Float minY;

    private Float maxY;

    /**
     * Constructor
     *
     * @param valueLists the lists of trend lists with trend values.
     * @param labels the list of trend legend labels.
     * @param minX      minimal index value on chart.
     * @param maxX      maximal index value on chart.
     */
    public ChartDataWrapper(List<List<Float>> valueLists, List<Label> labels, Integer minX, Integer maxX) {
        this.valueLists = valueLists;
        this.labels = labels;
        this.minX = minX;
        this.maxX = maxX;

        determineYaxisExtremes();
    }

    public List<List<Float>> getValueLists() {
        return valueLists;
    }

    public List<Label> getLabels() {
        return labels;
    }

    /**
     * Determine Y value extremes of the Y axis, get these values from the first list of trend values.
     */
    private void determineYaxisExtremes() {
        this.minY = this.valueLists.get(0).stream().min(Float::compare).get() * 0.999F;
        this.maxY = this.valueLists.get(0).stream().max(Float::compare).get() * 1.001F;
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
}
