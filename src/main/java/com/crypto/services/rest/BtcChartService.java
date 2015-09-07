package com.crypto.services.rest;

import com.crypto.dao.*;
import com.crypto.entities.*;
import com.crypto.enums.ChartType;
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
    public List<ChartDataWrapper> getCryptoCoinData(@PathParam("tradingId") Integer tradingId) {

        // Get crypto trading  history data
        final Trading trading = tradingDao.get(tradingId);
        final Integer startIndex = cryptocoinHistoryDao.getStartIndex(trading.getTradePair());
        final Integer endIndex = cryptocoinHistoryDao.getLastIndex(trading.getTradePair());

        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getAll(trading.getTradePair());

        // Get the trends for the BTC chart
        final Chart btcChart = chartDao.getChart(ChartType.BTC);
        final List<ChartTrend> chartTrends = chartTrendDao.getChartTrends(btcChart);

        // Get trend values
        final List<TrendValue> trendValues = cryptocoinTrendDao.getAllTrendValues(startIndex, endIndex, chartTrends.get(1).getTrend(), trading.getTradePair());

        final List<Float> cryptoCoinList = new ArrayList<>();

        for (CryptocoinHistory cryptocoinHistory : cryptocoinHistories) {
            cryptoCoinList.add(cryptocoinHistory.getClose());
        }

        final ChartDataWrapper btcDataWrapper = new ChartDataWrapper(cryptoCoinList, startIndex, endIndex, "Koers " + trading.getTradePair().getCryptoCurrency().getCode());

        final List<Float> trendList = new ArrayList<>();

        for (TrendValue trendValue : trendValues) {
            trendList.add(trendValue.getValue());
        }

        final ChartDataWrapper trendDataWrapper = new ChartDataWrapper(trendList, startIndex, endIndex, chartTrends.get(1).getTrend().getName());

        // Add all the btc and trend values to a list of Chart Data wrappers
        final List<ChartDataWrapper> wrapperList = new ArrayList<>();
        wrapperList.add(btcDataWrapper);
        wrapperList.add(trendDataWrapper);

        return wrapperList;
    }
}
