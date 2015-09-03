package com.crypto.services.rest.wrapper;

import java.util.List;

/**
 * Macd data wrapper class
 * Created by Jan Wicherink on 2-9-15.
 */
public class MacdDataWrapper {

    private List<Float> macdList;

    private Integer minX;

    private Integer maxX;

    private Float minY;

    private Float maxY;

    private String label;

    /**
     * Constructor
     * @param macdList list of Macd Values
     * @param minX minimal index value in macdList
     * @param maxX maximal index value in macdList
     * @param minY minimal value in macdList
     * @param maxY maximum value in macdList
     * @param label the label of this macd in chart
     */
    public MacdDataWrapper(List<Float> macdList, Integer minX, Integer maxX, Float minY, Float maxY, String label) {
        this.macdList = macdList;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.label = label;
    }

    public List<Float> getMacdList() {
        return macdList;
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
}
