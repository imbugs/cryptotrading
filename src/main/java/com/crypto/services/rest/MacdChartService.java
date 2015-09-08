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
 * Service to fetch the Cryptocoin Macd trend data for the Macd chart
 *
 * Created by Jan Wicherink on 1-9-15.
 */
@Path("/")
@Stateless
public class MacdChartService {

    @EJB
    private ChartDao chartDao;

    @EJB
    private ChartTrendDao chartTrendDao;

    @EJB
    private TradingDao tradingDao;

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private MacdDao macdDao;

    @GET
    @Path("/getMacdData/{tradingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ChartDataWrapper getMacdData (@PathParam("tradingId") Integer tradingId) {

        final Trading trading = tradingDao.get(tradingId);

         // Get the macd of the MACD chart
        final Chart macdChart = chartDao.getChart(ChartType.MACD);
        final ChartTrend chartTrend = chartTrendDao.getChartTrends(macdChart).get(0);

        final Macd macd = chartTrend.getMacd();

        final Integer startIndex = cryptocoinHistoryDao.getStartIndex(trading.getTradePair());
        final Integer endIndex   = cryptocoinHistoryDao.getLastIndex(trading.getTradePair());

        final List<MacdValue> macdValues = cryptocoinTrendDao.getAllMacdValues(startIndex + macd.getLongTrend().getPeriod(), endIndex, macd, trading.getTradePair());

        final List<List<Object>> macdList = new ArrayList<>();

        Float minYValue = null;
        Float maxYValue = null;

        for (MacdValue macdValue : macdValues) {
            List<Object> dataPoint = new ArrayList<>();
            dataPoint.add(macdValue.getIndex());
            dataPoint.add(macdValue.getValue());

            macdList.add(dataPoint);
        };

        final List<Label> labels = new ArrayList<>();
        labels.add (new Label(macd.getName(), false));

        final List<List<List<Object>>> valueLists = new ArrayList<>();
        valueLists.add (macdList);

        final ChartDataWrapper chartDataWrapper = new ChartDataWrapper(valueLists, labels, startIndex, endIndex);

        return chartDataWrapper;
    }
}
