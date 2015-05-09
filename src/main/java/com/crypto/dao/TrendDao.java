package com.crypto.dao;

import com.crypto.entities.Trend;

import java.io.Serializable;
import java.util.List;

/**
 * The Treand Dao
 *
 * Created by Jan Wicherink on 15-4-15.
 */
public interface TrendDao extends Serializable {

    /**
     * Get the trend with a given id
     *
     * @param Id the id
     * @return the trend
     */
    public Trend get (final Integer Id);

    /**
     * Get all moving average bulk
     * @return the moving average bulk
     */
    public List<Trend> getAllMovingAverageTrends();


    /**
     * Get all exponential moving average bulk
     * @return the moving average bulk
     */
    public List<Trend> getAllExponentialMovingAverageTrends();


    /**
     * Get all smoothing moving average bulk
     * @return the moving average bulk
     */
    public List<Trend> getAllSmoothingMovingAverageTrends();

}
