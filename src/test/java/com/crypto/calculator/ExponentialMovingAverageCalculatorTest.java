package com.crypto.calculator;

import com.crypto.dataprovider.MovingAverageDataProvider;
import com.crypto.entities.*;
import com.crypto.enums.TrendType;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Exponential Moving average test
 * 
 * Created by Jan Wicherink on 8-5-15.
 */
public class ExponentialMovingAverageCalculatorTest {

    @Test
    public void testExponentialMovingAverageCalculator() throws ParseException {

       final TradingSite tradingSite = new TradingSite("KRAKEN", "Kraken", "www.kraken.com");
       final Currency currency = new Currency("EUR", "Euro", "&euro");
       final CryptoCurrency cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
       final TradePair tradePair = new TradePair(1, tradingSite, currency, cryptoCurrency, 0.01F);

       final MovingAverageDataProvider dataProvider = new MovingAverageDataProvider() {
           @Override
           public Float getSumOverPeriod(Integer index) {
               return null;
           }

           @Override
           public CryptocoinHistory getValue(Integer index) {

               final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
               Date date =null;
               try {
                   date = dateFormat.parse("2015-05-08 12:00:00");
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               final Timestamp timestamp = new Timestamp(date.getTime());

               CryptocoinHistory cryptocoinHistory = new CryptocoinHistory(new Integer(100), timestamp, tradePair, 200F, 100F, 300F, 400F, 500L);

               return cryptocoinHistory;
           }

           @Override
           public Integer getIndex() {
               return 100;
           }

           @Override
           public TrendValue getTrendValue(Integer index) {

               return new TrendValue(1, tradePair, 99, null, null, 300F, 100F);
           }

           @Override
           public Trend getTrend() {
               return new Trend(1, TrendType.EMA, 10, null);
           }

           @Override
           public TradePair getTradePair() {

               return tradePair;
           }
       };

       final ExponentialMovingAverageCalculator calculator = new ExponentialMovingAverageCalculator(dataProvider, 100);
        calculator.calculate();

       assertEquals (new Float(645.4545F), calculator.getValue());

       calculator.calculate();
    }
}