package com.crypto.calculator;

import com.crypto.calculator.Calculator;
import com.crypto.entities.Macd;
import com.crypto.entities.MacdValue;
import com.crypto.entities.Trend;

/**
 * A calculator
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public interface MacdCalculator extends Calculator<MacdValue> {

    public Macd getMacd();

    public void setMacd(final Macd macd);
}
