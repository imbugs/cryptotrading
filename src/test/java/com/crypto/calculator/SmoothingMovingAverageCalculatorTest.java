package com.crypto.calculator;

import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;
import com.crypto.enums.TrendType;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.assertEquals;

/**
 * Tests the smoothing moving average calculator
 * Created by Jan Wicherink on 9-6-15.
 *
 *
 * SMA 10 used from EMA-10
 * EMA-10 value from index 6 to 15 (10 values)
 *
 * 6	126.525
 * 7	147.622  274.147
 * 8	168.719
 * 9	189.811
 * 10	210.904  569.434
 * 11	210.898
 * 12	210.904
 * 13	210.813  632.615
 * 14	210.742
 * 15	210.684  421.426
 *
 * Total sum :  1897.622
 *
 * Smoothed by SMA 10 :  1897.622 / 10 = 189.762
 *
 * SMA value at index 15 : 189.762 (according to calculated data)
 */
public class SmoothingMovingAverageCalculatorTest {

    @Test
    public void testSmoothingMovingAverageCalcuator () {

        //Arrange
        final SmoothingMovingAverageCalculator smoothingMovingAverageCalculator;
        final TradePair tradePair = new TradePair();

        // Trend being smoothed, make period 5, while actual period is 10, just to make sure the period of the sma trend
        // is used in the calculation and not the ema trend period.
        final Trend emaTrend = new Trend(0, TrendType.EMA, 5, null);

        // The smoothing trend = SMA
        final Trend smaTrend = new Trend(1, TrendType.SMA, 10, emaTrend);


          final MovingAverageDataProvider movingAverageDataProvider = new MovingAverageDataProvider() {

            @Override
            public CryptocoinHistory getValue(Integer index) {

                return null;
            }

            @Override
            public Float getSumOverPeriod(Integer index, Integer period) {
                return null;
            }

            @Override
            public Float getSumOverPeriod(Integer index, Trend smoothingTrend, Integer period) {
                return 1897.622F;
            }

            @Override
            public TrendValue getTrendValue(Integer index) {
               return new TrendValue(tradePair, 15, emaTrend, null, 210.684F, 0F);
            }

            @Override
            public TrendValue getSmoohtingTrendValue(Integer index) {
                return null;
            }

            @Override
            public Trend getTrend() {
                return smaTrend;
            }

            @Override
            public void setTrend(Trend trend) {

            }

            @Override
            public TradePair getTradePair() {
                return tradePair;
            }

            @Override
            public void setTradePair(TradePair tradePair) {

            }
        };

        smoothingMovingAverageCalculator = new SmoothingMovingAverageCalculator(movingAverageDataProvider);
        smoothingMovingAverageCalculator.setIndex(15);
        smoothingMovingAverageCalculator.setTrend(smaTrend);

        //Act
        smoothingMovingAverageCalculator.calculate();

        // Assert
        // Smoothing moving average at index 15
        assertEquals(189.762F, smoothingMovingAverageCalculator.getCalculatedValue().getValue(), 0.01F);
    }
}
