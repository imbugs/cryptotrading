package com.crypto.services.rest;

import com.crypto.dao.*;
import com.crypto.entities.*;
import com.crypto.enums.ChartType;
import com.crypto.services.rest.wrapper.BtcChartDataWrapper;
import com.crypto.services.rest.wrapper.ChartDataWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

/**
 * Services crypto coin data and trends for the BTC chart
 * Created by Jan Wicherink on 3-9-15.
 */
@Path("/")
@Stateless
public class BtcChartService {

    @EJB
    private ChartDao chartDao;

    @EJB
    private ChartTrendDao chartTrendDao;

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TradingDao tradingDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getBtcData/{tradingId}")
    public BtcChartDataWrapper getCryptoCoinData(@PathParam("tradingId") Integer tradingId) {

        // Get crypto trading  history data
        final Trading trading = tradingDao.get(tradingId);

        // Get the trends for the BTC chart
        final Chart btcChart = chartDao.getChart(ChartType.BTC);
        final List<ChartTrend> chartTrends = chartTrendDao.getChartTrends(btcChart);

        // Get crypto coin data
        final Integer startIndex = cryptocoinHistoryDao.getStartIndex(trading.getTradePair());
        final Integer endIndex = cryptocoinHistoryDao.getLastIndex(trading.getTradePair());
        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getAll(trading.getTradePair());

        final List<Float> cryptoCoinList = new ArrayList<>();

        for (CryptocoinHistory cryptocoinHistory : cryptocoinHistories) {
            cryptoCoinList.add(cryptocoinHistory.getClose());
        }
        final String cryptocoinLabel = "Koers " + trading.getTradePair().getCryptoCurrency().getCode();

         // Get all trend value lists
        final List<List<Float>> trendLists = new ArrayList<>();
        final List<String> trendLabels = new ArrayList<>();

        for (ChartTrend chartTrend : chartTrends) {
            // Get trend values
            final List<TrendValue> trendValues = cryptocoinTrendDao.getAllTrendValues(startIndex, endIndex, chartTrend.getTrend(), trading.getTradePair());
            final List<Float> trendValueList = new ArrayList<>();
            for (TrendValue trendValue : trendValues){
                trendValueList.add(trendValue.getValue());
            }
            trendLists.add(trendValueList);
            trendLabels.add(chartTrend.getTrend().getName());
        }

        final BtcChartDataWrapper btcDataWrapper = new BtcChartDataWrapper(cryptoCoinList, cryptocoinLabel, trendLists, trendLabels, startIndex, endIndex);

        return btcDataWrapper;
    }
}
