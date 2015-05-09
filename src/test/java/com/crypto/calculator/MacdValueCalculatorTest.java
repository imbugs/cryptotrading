package com.crypto.calculator;

import com.crypto.dataprovider.MacdDataProvider;
import com.crypto.entities.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the Macd calculator
 *
 * Created by Jan Wicherink on 3-5-15.
 */
public class MacdValueCalculatorTest {

    @Test
    public void testMacdValueCalculator() {

        final MacdDataProvider dataProvider = new MacdDataProvider() {
            @Override
            public MacdValue getValue(Integer index) {

                if (index == 100) {
                    return new MacdValue(100,null,null, 6F, 1F);
                }
                else {
                    return new MacdValue(99, null,null, 4F, 1F);
                }
            }

            @Override
            public Integer getIndex() {
                return 100;
            }

            @Override
            public TrendValue getShortTrendValue(Integer index) {
                return new TrendValue(1, new TradePair(), 100, new Trend(), null, 10F, 1F);

            }

            @Override
            public TrendValue getLongTrendValue(Integer index) {
                return new TrendValue(1, new TradePair(), 100, new Trend(), null, 4F, 1F);
            }

            @Override
            public Macd getMacd() {
                return null;
            }

            @Override
            public TradePair getTradePair() {
                return null;
            }
        };

        final MacdValueCalculator macdValueCalculator = new MacdValueCalculator(dataProvider, new Integer(100));
        macdValueCalculator.calculate();

        assertEquals(new Float(6F), macdValueCalculator.getValue());
        assertEquals(new Float(2F), macdValueCalculator.getDelta());
    }
}
