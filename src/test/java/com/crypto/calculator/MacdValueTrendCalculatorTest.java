package com.crypto.calculator;

import com.crypto.datahandler.provider.MacdDataProvider;
import com.crypto.entities.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the Macd calculator
 *
 * Created by Jan Wicherink on 3-5-15.
 */
public class MacdValueTrendCalculatorTest {

    @Test
    public void testMacdValueCalculator() {

        //Arrange
        final MacdDataProvider dataProvider = new MacdDataProvider() {
            @Override
            public MacdValue getMacdValue(Integer index) {

                if (index == 100) {
                    return new MacdValue(100,null,null, 6F, 1F);
                }
                else {
                    return new MacdValue(99, null,null, 4F, 1F);
                }
            }

            @Override
            public TrendValue getShortTrendValue(Integer index) {
                return new TrendValue(new TradePair(), 100, new Trend(), null, 10F, 1F);

            }

            @Override
            public TrendValue getLongTrendValue(Integer index) {
                return new TrendValue(new TradePair(), 100, new Trend(), null, 4F, 1F);
            }

            @Override
            public TradePair getTradePair() {
                return null;
            }

            @Override
            public void setTradePair(TradePair tradePair) {

            }
        };

        // Act
        final Trend shortTrend = new Trend();
        final Trend longTrend = new Trend();
        final Macd macd = new Macd(1,shortTrend,longTrend);

        final MacdValueCalculator macdValueCalculator = new MacdValueCalculator(dataProvider, new Integer(100), macd);
        macdValueCalculator.calculate();

        //Assert
        assertEquals(new Float(6F), macdValueCalculator.getCalculatedValue().getValue());
        assertEquals(new Float(2F), macdValueCalculator.getDelta());
    }
}