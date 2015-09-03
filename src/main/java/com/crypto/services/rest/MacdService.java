package com.crypto.services.rest;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.MacdDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.Macd;
import com.crypto.entities.MacdValue;
import com.crypto.entities.Trading;
import com.crypto.services.rest.wrapper.MacdDataWrapper;

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
 * Service to fetch the Cryptocoin Macd trend data
 *
 * Created by Jan Wicherink on 1-9-15.
 */
@Path("/")
@Stateless
public class MacdService {

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
    public MacdDataWrapper getMacdData (@PathParam("tradingId") Integer tradingId) {

        final Trading trading = tradingDao.get(tradingId);

        // TODO :  Get Macd for chart from TRENDS_FOR_CHARTS data
        final Macd macd = macdDao.get(1);

        final Integer startIndex = cryptocoinHistoryDao.getStartIndex(trading.getTradePair()) + macd.getLongTrend().getPeriod();
        final Integer endIndex   = cryptocoinHistoryDao.getLastIndex(trading.getTradePair());

        final List<MacdValue> macdValues = cryptocoinTrendDao.getAllMacdValues(startIndex, endIndex, macd, trading.getTradePair());

        final List<Float> macdList = new ArrayList<>();

        Float minYValue = null;
        Float maxYValue = null;

        for (MacdValue macdValue : macdValues) {

            if (minYValue == null) {
                minYValue = macdValue.getValue();
            }
            else {
                if (macdValue.getValue() < minYValue) {
                    minYValue = macdValue.getValue();
                }
            }

            if (maxYValue == null) {
                maxYValue = macdValue.getValue();
            }
            else {
                if (macdValue.getValue() > maxYValue) {
                    maxYValue = macdValue.getValue();
                }
            }

            macdList.add(macdValue.getValue());
        };

        MacdDataWrapper macdDataWrapper = new MacdDataWrapper(macdList,startIndex, endIndex, minYValue, maxYValue, macd.getName());

        return macdDataWrapper;
    }
}
