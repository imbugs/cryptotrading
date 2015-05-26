package com.crypto.calculator;

import com.crypto.entities.Trend;

/**
 * A calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public interface TrendCalculator extends Calculator{

    /**
     * Set the trend of the calculator
     * @param trend the trend of the calculator
     */
    public void setTrend(final Trend trend);

    /**
     * Get the trend of the calculator
     * @return the trend of the calculator
     */
    public Trend getTrend();

}
