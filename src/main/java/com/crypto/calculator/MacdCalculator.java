package com.crypto.calculator;

import com.crypto.entities.Trend;

/**
 * A calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public interface MacdCalculator extends Calculator{

    public Trend getShortTrend();

    public void setShortTrend(Trend shortTrend);

    public Trend getLongTrend();

    public void setLongTrend(Trend longTrend);
}
