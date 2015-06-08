package com.crypto.calculator;

import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trend;
import com.crypto.entities.TrendValue;
import com.crypto.enums.TrendType;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 /**
 * Moving Average test
 *
 * Using the following data table,the exponential moving average is calculated for 5 May -2010 (index 30)
 * Example data taken from : http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
 *
 *
 * 	Index Date	        Price	10-day MA	10-day EMA
 *	1     24-Mar--2010	22.27
 *	2     25-Mar--2010	22.19
 *	3     26-Mar--2010	22.08
 *	4     29-Mar--2010	22.17
 *	5     30-Mar--2010	22.18
 *	6     31-Mar--2010	22.13
 *	7     1-Apr--2010	22.23
 *	8     5-Apr--2010	22.43
 *	9     6-Apr--2010	22.24
 *	10    7-Apr--2010	22.29	22.22		22.22
 *	11    8-Apr--2010	22.15	22.21		22.21
 *	12    9-Apr--2010	22.39	22.23		22.24
 *	13    12-Apr--2010	22.38	22.26		22.27
 *	14    13-Apr--2010	22.61	22.31		22.33
 *	15    14-Apr--2010	23.36	22.42		22.52
 *	16    15-Apr--2010	24.05	22.61		22.80
 *	17    16-Apr--2010	23.75	22.77		22.97
 *	18    19-Apr--2010	23.83	22.91		23.13
 *	19    20-Apr--2010	23.95	23.08		23.28
 *	20    21-Apr--2010	23.63	23.21		23.34
 *	21    22-Apr--2010	23.82	23.38		23.43
 *	22    23-Apr--2010	23.87	23.53		23.51
 *	23    26-Apr--2010	23.65	23.65		23.54
 *	24    27-Apr--2010	23.19	23.71		23.47
 *	25    28-Apr--2010	23.10	23.69		23.40
 *	26    29-Apr--2010	23.33	23.61		23.39
 *	27    30-Apr--2010	22.68	23.51		23.26
 *	28    3-May--2010	23.10	23.43		23.23
 *	29    4-May--2010	22.40	23.28		23.08
 *	30    5-May--2010	22.17	23.13		22.92
 *
 *
 * Calculation :
 * MA: S10 period sum / 10  = 22.17 +  22.40 + 23.10 + 22.68 + 23.33 + 23.10 + 23.19 + 23.65 + 23.87 + 23.82
 *
 * Created by Jan Wicherink on 3-5-15.
 */
public class MovingAverageTrendCalculatorTest {

    @Test
    public void testMovingAverageCalculator () throws ParseException {

        //Arrange
        final MovingAverageCalculator movingAverageCalculator;

        final MovingAverageDataProvider dataProvider;

        dataProvider = new MovingAverageDataProvider() {

            @Override
            public Float getSumOverPeriod(Integer index, Integer period) {

                // Sum of price of the last 10 days (from the 5th of may 2010, index 30)
                final Float total = 22.17F +  22.40F + 23.10F + 22.68F + 23.33F + 23.10F + 23.19F + 23.65F + 23.87F + 23.82F;

                return (total);
            }

            @Override
            public Float getSumOverPeriod(Integer index, Trend smoothingTrend, Integer period) {
                return null;
            }

            @Override
            public Trend getTrend() {
                return null;
            }

            @Override
            public void setTrend(Trend trend) {

            }

            @Override
            public CryptocoinHistory getValue(Integer index) {

                final CryptocoinHistory cryptocoinHistory = new CryptocoinHistory();

                if (index == 30) {
                    cryptocoinHistory.setClose(22.17F);
                }
                else {
                    cryptocoinHistory.setClose(22.40F);
                }

                return cryptocoinHistory;
            }

            public Integer getIndex() {
                return 30;
            }

            @Override
            public TrendValue getTrendValue(Integer index) {

                if (index == 29) {
                    return new TrendValue(new TradePair(), 29, new Trend(), null, 23.28F, 0.15F);
                }
                else {
                    return null;
                }
            }

            @Override
            public TrendValue getSmoohtingTrendValue(Integer index) {
                return null;
            }

            @Override
            public TradePair getTradePair() {

                return null;
            }

            @Override
            public void setTradePair(TradePair tradePair) {

            }
        };

        final Trend trend = new Trend(0, TrendType.MA, 10, null);
        movingAverageCalculator = new MovingAverageCalculator(dataProvider, 30, trend);

        //Act
        movingAverageCalculator.calculate();

        // Assert
        // Moving Average at index 30, 5th may 2010 = 23.13 with rounding error of 0.01
        assertEquals(new Float(23.13F), movingAverageCalculator.getCalculatedValue().getValue(), 0.01F);
        assertEquals(new Float(-0.15F), movingAverageCalculator.getDelta(), 0.01F);
    }
}
