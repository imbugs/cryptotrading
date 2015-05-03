package com.crypto.calculator;

import com.crypto.dataprovider.MovingAverageDataProvider;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;
import com.crypto.enums.TrendType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the moving average calculator
 *
 * Created by Jan Wicherink on 3-5-15.
 */
public class MovingAverageCalculatorTest {

    @Test
    public void testMovingAverageCalculator () {

        final MovingAverageCalculator movingAverageCalculator;

        final MovingAverageDataProvider dataProvider;

        dataProvider = new MovingAverageDataProvider() {

            @Override
            public Float getSumOverPeriod(Integer index) {
                 return (200F);
            }

            @Override
            public Float getValue(Integer index) {

                if (index == 100) {
                    return 4F;
                }
                else {
                    return 3F;
                }
            }

            @Override
            public TrendValue getTrendValue(Integer index) {

                if (index == 100) {
                    return new TrendValue(1, new TradePair(), 100, new Trend(), null, 4F, 1F);
                }
                else {
                    return new TrendValue(1, new TradePair(), 99, new Trend(), null, 3F, 1F);
                }
            }

            @Override
            public Trend getTrend() {
                return new Trend(100, TrendType.EMA, 50, null);
            }

            @Override
            public TradePair getTradePair() {
                return null;
            }
        };

        movingAverageCalculator = new MovingAverageCalculator(dataProvider,100);
        movingAverageCalculator.calculate();

        assertEquals(new Float(4F), movingAverageCalculator.getValue());
        assertEquals(new Float(1F), movingAverageCalculator.getDelta());
    }
}
