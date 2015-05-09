package com.crypto.calculator;

/**
 * A calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public interface Calculator {

    /**
     * Calculate
     */
    public void calculate();

    /**
     * Set the calculated value
     * @param value the calculated value
     */
    public void setValue (final Float value);

    /**
     * Get the calculated value
     * @return the calculated value
     */
    public Float getValue();

    /**
     * Set the calculated value
     * @param delta the difference with the precious value at index -1
     */
    public void setDelta (final Float delta);

    /**
     * Get the delta value
     * @return the delta value
     */
    public Float getDelta();


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
