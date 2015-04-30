package com.crypto.entities;

import com.crypto.enums.TrendType;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jan Wicherink on 25-3-2015.
 */
public class TrendTest {

    @Test
    public void testTrendNameForMovingAverageWithPeriod40 () {

       Trend trend = new Trend(1, TrendType.MA, 40, null);

        assertEquals("MA40", trend.getName());
    }

    @Test
    public void testTrendNameForMovingAverageWithoutPeriod () {

        Trend trend = new Trend(1, TrendType.MA, null, null);

        assertEquals("MA?", trend.getName());
    }

    @Test
    public void testTrendNameForExponentialMovingAverageWithPeriod50 () {

        Trend trend = new Trend(1, TrendType.EMA, 50, null);

        assertEquals("EMA50", trend.getName());
    }

    @Test
    public void testTrendNameForExponentialMovingAverageWithoutPeriod () {

        Trend trend = new Trend(1, TrendType.EMA, null, null);

        assertEquals("EMA?", trend.getName());
    }

    @Test
    public void testTrendNameForSmoothingAverageWithPeriod60 () {

        Trend trend = new Trend (1, TrendType.MA, 50, null);
        Trend smoothingTrend = new Trend(2, TrendType.SMA, 60, trend);

        assertEquals("MA50", trend.getName());
        assertEquals("SMA60MA50", smoothingTrend.getName());
    }

    @Test
     public void testTrendNameForSmoothingAverageWithPeriod () {

        Trend trend = new Trend (1, TrendType.MA, 50, null);
        Trend smoothingTrend = new Trend(2, TrendType.SMA, null, trend);

        assertEquals("MA50", trend.getName());
        assertEquals("SMA?MA50", smoothingTrend.getName());
    }

    @Test
    public void testTrendNameForSmoothingAverageWithAnyPeriod () {

        Trend trend = new Trend (1, TrendType.MA, null, null);
        Trend smoothingTrend = new Trend(2, TrendType.SMA, null, trend);

        assertEquals("MA?", trend.getName());
        assertEquals("SMA?MA?", smoothingTrend.getName());
    }
}
