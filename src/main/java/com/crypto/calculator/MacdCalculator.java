package com.crypto.calculator;

import com.crypto.entities.Macd;
import com.crypto.entities.Trend;

/**
 * A calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public interface MacdCalculator extends Calculator{

    public Macd getMacd();

    public void setMacd(Macd macd);
}
