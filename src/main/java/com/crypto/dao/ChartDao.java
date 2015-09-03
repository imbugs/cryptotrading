package com.crypto.dao;

import com.crypto.entities.Chart;
import com.crypto.enums.ChartType;

import java.io.Serializable;

/**
 * Chart Dao
 * Created by jan Wicherink on 3-9-15.
 */
public interface ChartDao extends Serializable {

    public Chart getChart (final ChartType type);
}
