package com.crypto.calculator;

import com.crypto.datahandler.provider.MovingAverageDataProvider;
import com.crypto.entities.*;
import com.crypto.enums.TrendType;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Exponential Moving Average test
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
 * SMA: 10 period sum / 10
 *
 * Multiplier: (2 / (Time periods + 1) ) = (2 / (10 + 1) ) = 0.1818 (18.18%)
 *
 * EMA: {Close - EMA(previous day)} x multiplier + EMA(previous day).
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public class ExponentialMovingAverageTrendCalculatorTest {

    @Test
    public void testExponentialMovingAverageCalculator() throws ParseException {

       //Arrange
       final TradingSite tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
       final Currency currency = new Currency("EUR", "Euro", "&euro");
       final CryptoCurrency cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
       final TradePair tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 0.01F);

       final MovingAverageDataProvider dataProvider = new MovingAverageDataProvider() {

           @Override
           public Float getSumOverPeriod(Integer index, Integer period) {
               return null;
           }

           @Override
           public CryptocoinHistory getValue(Integer index) {

               final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
               Date date =null;
               try {
                   date = dateFormat.parse("-2010-05-05 12:00:00");
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               final Timestamp timestamp = new Timestamp(date.getTime());

               CryptocoinHistory cryptocoinHistory = new CryptocoinHistory(new Integer(30), timestamp, tradePair, 22.17F, 22.17F, 22.17F, 22.17F, 500L);

               return cryptocoinHistory;
           }


           @Override
           public TrendValue getTrendValue(Integer index, Trend trend, TradePair tradePair) {

               // Return moving average value of index 29, 4th may 2010 :  EMA=23.08  delta = -0.15
               return new TrendValue(1, tradePair, 29, null, null, 23.08F, -0.15F);
           }

           @Override
           public TradePair getTradePair() {

               return tradePair;
           }
       };

       //Act
        final  Trend trend = new Trend(1, TrendType.EMA, 10, null);
        final ExponentialMovingAverageCalculator calculator = new ExponentialMovingAverageCalculator(dataProvider, 30, trend );
        calculator.calculate();

       // Assert
       // Expected exponential moving average value at index = 30 (5th may 2010) = 22.92 with rounding error of 0.01
       assertEquals (new Float(22.92F), calculator.getCalculatedValue(), 0.01F);

       calculator.calculate();
    }
}