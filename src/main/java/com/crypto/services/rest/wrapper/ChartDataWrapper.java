package com.crypto.services.rest.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Chart data wrapper
 * wraps all the data to be shown on the chart.

 *
 * Created by Jan Wicherink on 2-9-15.
 */
public class ChartDataWrapper {

    // a List of Lists with data points each list represents a set of data to be displayed on the chart
    // the List<Object> represents a datapoint on the chart and may contain : (x-axix value, y-axix-value, label string text).
    private List<List<List<Object>>> valueLists;

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
    public ChartDataWrapper(List<List<List<Object>>> valueLists, List<Label> labels, Integer minX, Integer maxX) {
        this.valueLists = valueLists;
        this.labels = labels;
        this.minX = minX;
        this.maxX = maxX;

        determineYaxisExtremes();
    }

    public List<List<List<Object>>> getValueLists() {
        return valueLists;
    }

    public List<Label> getLabels() {
        return labels;
    }

    /**
     * Determine Y value extremes of the Y axis, get these values from the first list of trend values.
     */
    private void determineYaxisExtremes() {

       // Get the y values from the first list of value lists (containing the crypto coin data).
       // the list contains datapoints (index, value)
       List<Float> yValues = new ArrayList<>();

       for (List<Object> dataPoint : this.valueLists.get(0)) {
          // Add the y value of a datapoint to the yValues list
          yValues.add ((Float)dataPoint.get(1));
       }

       // determine the extreme values of the y value list.
       this.minY = yValues.stream().min(Float::compare).get() * 0.999F;
       this.maxY = yValues.stream().max(Float::compare).get() * 1.001F;
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
