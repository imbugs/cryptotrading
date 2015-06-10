package com.crypto.calculator;

import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;

/**
 * A calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 *
 * @param <D> the data type of the calculation of this calculator
 */
public interface Calculator <D>{

    /**
     * Calculate
     */
    public void calculate();

    /**
     * Get the calculated value
     * @return the calculated value
     */
    public D getCalculatedValue();

    /**
     * Get the index of the value being calculated
     * @return the index of the value calculated
     */
    public Integer getIndex();

    /**
     * Set the index of the value being calculated
     * @param index the index of the calculated value
     */
    public void setIndex(Integer index);
}
