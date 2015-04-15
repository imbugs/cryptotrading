package com.crypto.dao;

import com.crypto.entities.Trend;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jan Wicherink on 15-4-15.
 */
public interface TrendDao extends Serializable {


    /**
     * Get the trend with a given id
     * @param Id the id
     * @return the trend
     */
    public Trend get (final Integer Id);

    /**
     * Get all moving average trends
     * @return the moving average trends
     */
    public List<Trend> getAllMovingAverageTrends();


    /**
     * Get all exponential moving average trends
     * @return the moving average trends
     */
    public List<Trend> getAllExponentialMovingAverageTrends();


    /**
     * Get all smoothing moving average trends
     * @return the moving average trends
     */
    public List<Trend> getAllSmoothingMovingAverageTrends();

}
