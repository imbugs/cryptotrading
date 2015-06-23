package com.crypto.calculator;

import com.crypto.datahandler.impl.MacdBulkDataHandler;
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
        final MacdBulkDataHandler dataProvider = new MacdBulkDataHandler() {
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
            public void setTrading(Trading trading) {

            }
        };

        // Act
        final Trend shortTrend = new Trend();
        final Trend longTrend = new Trend();
        final Macd macd = new Macd(1,shortTrend,longTrend);

        final MacdValueCalculator macdValueCalculator = new MacdValueCalculator(dataProvider);
        macdValueCalculator.setIndex(new Integer(100));
        macdValueCalculator.setMacd(macd);
        macdValueCalculator.calculate();

        //Assert
        assertEquals(new Float(6F), macdValueCalculator.getCalculatedValue().getValue());
        assertEquals(new Float(2F), macdValueCalculator.getDelta());
    }
}
