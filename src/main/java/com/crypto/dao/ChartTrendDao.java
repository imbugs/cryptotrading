package com.crypto.dao;

import com.crypto.entities.Chart;
import com.crypto.entities.ChartTrend;

import java.io.Serializable;
import java.util.List;

/**
 * Chart trend dao
 *
 * Created by Jan Wicherink on 3-9-15.
 */
public interface ChartTrendDao extends Serializable {

    public List<ChartTrend> getChartTrends(final Chart chart);
}
