package com.crypto.services.rest.wrapper;

import java.util.List;

/**
 * Chart data wrapper
 * wraps all the data to be shown on the chart.

 *
 * Created by Jan Wicherink on 2-9-15.
 */
public class BtcChartDataWrapper extends ChartDataWrapper {

    private List<List<Float>> trendLists;

    private List<String> trendLabels;

    /**
     * Constructor
     *
     * @param cryptocoinList   list of crypto coin values.
     * @param cryptocoinLabel  the crypto coin legend label.
     * @param trendLists the lists of trend lists with trend values.
     * @param trendLabels the list of trend legend labels.
     * @param minX      minimal index value on chart.
     * @param maxX      maximal index value on chart.
     */
    public BtcChartDataWrapper(List<Float> cryptocoinList, String cryptocoinLabel, List<List<Float>> trendLists, List<String> trendLabels, Integer minX, Integer maxX) {

        super (cryptocoinList, cryptocoinLabel, minX, maxX);

        this.trendLists = trendLists;
        this.trendLabels = trendLabels;
    }

    public List<List<Float>> getTrendLists() {
        return trendLists;
    }

    public List<String> getTrendLabels() {
        return trendLabels;
    }
}
