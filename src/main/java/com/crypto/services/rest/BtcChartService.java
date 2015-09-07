package com.crypto.services.rest;

import com.crypto.dao.*;
import com.crypto.entities.*;
import com.crypto.enums.ChartType;
import com.crypto.services.rest.wrapper.ChartDataWrapper;
import com.crypto.services.rest.wrapper.Label;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

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
    private SignalDao signalDao;

    @EJB
    private TradingDao tradingDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getBtcData/{tradingId}")
    public ChartDataWrapper getCryptoCoinData(@PathParam("tradingId") Integer tradingId) {

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
        final Label cryptocoinLabel = new Label("Koers " + trading.getTradePair().getCryptoCurrency().getCode());

         // Get all trend value lists
        final List<List<Float>> trendLists = new ArrayList<>();
        final List<Label> trendLabels = new ArrayList<>();

        // Get the signal data
   //     final List<Signal> signals = signalDao.getAll(startIndex, endIndex, trading);
   //     final List<Float> signalList = new ArrayList<>();

        trendLists.add(cryptoCoinList);
        trendLabels.add(cryptocoinLabel);

        for (ChartTrend chartTrend : chartTrends) {
            // Get trend values
            final List<TrendValue> trendValues = cryptocoinTrendDao.getAllTrendValues(startIndex, endIndex, chartTrend.getTrend(), trading.getTradePair());
            final List<Float> trendValueList = new ArrayList<>();
            for (TrendValue trendValue : trendValues){
                trendValueList.add(trendValue.getValue());
            }
            trendLists.add(trendValueList);
            trendLabels.add(new Label (chartTrend.getTrend().getName()));
        }

        final ChartDataWrapper btcDataWrapper = new ChartDataWrapper(trendLists, trendLabels, startIndex, endIndex);

        return btcDataWrapper;
    }
}
