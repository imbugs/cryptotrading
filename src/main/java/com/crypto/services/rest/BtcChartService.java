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

        // Get the crypto trading
        final Trading trading = tradingDao.get(tradingId);

        // Trend value lists and labels for the chart
        final List<List<List<Object>>> trendLists = new ArrayList<>();
        final List<Label> trendLabels = new ArrayList<>();

        // Get the trends to be displayed on the BTC chart
        final Chart btcChart = chartDao.getChart(ChartType.BTC);
        final List<ChartTrend> chartTrends = chartTrendDao.getChartTrends(btcChart);

        // Get crypto coin data
        final Integer startIndex = cryptocoinHistoryDao.getStartIndex(trading.getTradePair());
        final Integer endIndex = cryptocoinHistoryDao.getLastIndex(trading.getTradePair());
        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getAll(trading.getTradePair());
        final List<List<Object>> cryptoCoinList = new ArrayList<>();

        for (CryptocoinHistory cryptocoinHistory : cryptocoinHistories) {

            // Data point (index, value)
            List <Object> dataPoint = new ArrayList<>();
            dataPoint.add(cryptocoinHistory.getIndex());
            dataPoint.add(cryptocoinHistory.getClose());

            cryptoCoinList.add(dataPoint);
        }
        final Label cryptocoinLabel = new Label("Koers " + trading.getTradePair().getCryptoCurrency().getCode(), false, 2);

        // Add the cryptocoin data to the trend lists and labels
        trendLists.add(cryptoCoinList);
        trendLabels.add(cryptocoinLabel);

        // Trend data
        for (ChartTrend chartTrend : chartTrends) {
            // Get trend values
            final List<TrendValue> trendValues = cryptocoinTrendDao.getAllTrendValues(startIndex, endIndex, chartTrend.getTrend(), trading.getTradePair());
            final List<List<Object>> trendValueList = new ArrayList<>();
            for (TrendValue trendValue : trendValues){
                // data point (index, value)
                List <Object> dataPoint = new ArrayList<>();
                dataPoint.add(trendValue.getIndx());
                dataPoint.add(trendValue.getValue());
                trendValueList.add(dataPoint);
            }
            // Add trends to trend list and lables
            trendLists.add(trendValueList);
            trendLabels.add(new Label(chartTrend.getTrend().getName(), false, 1));
        }

        // Get the signal data
        final List<Signal> signals = signalDao.getAll(startIndex, endIndex, trading);
        final List<List<Object>> signalList = new ArrayList<>();
        final Label signalLabel = new Label("Signalen", true, 5);

        for (Signal signal : signals) {
            List <Object> dataPoint = new ArrayList<>();

            Integer index = signal.getPk().getIndex();
            dataPoint.add(index);
            dataPoint.add(cryptocoinHistories.get(index).getClose());

            switch (signal.getTradeSignal()) {
                case BEAR : dataPoint.add("VERKOOP");
                                   break;
                case BULL: dataPoint.add("KOOP");
            }
            signalList.add(dataPoint);
        }

        trendLists.add(signalList);
        trendLabels.add(signalLabel);

        final ChartDataWrapper btcDataWrapper = new ChartDataWrapper(trendLists, trendLabels, startIndex, endIndex);

        return btcDataWrapper;
    }
}
