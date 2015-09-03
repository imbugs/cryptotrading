package com.crypto.services.rest;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.Trading;
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
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private TradingDao tradingDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getBtcData/{tradingId}")
    public ChartDataWrapper getCryptoCoinData(@PathParam("tradingId") Integer tradingId) {

        final Trading trading = tradingDao.get(tradingId);

        final Integer startIndex = cryptocoinHistoryDao.getStartIndex(trading.getTradePair());
        final Integer endIndex = cryptocoinHistoryDao.getLastIndex(trading.getTradePair());

        final List<CryptocoinHistory> cryptocoinHistories = cryptocoinHistoryDao.getAll(trading.getTradePair());

        final List<Float> cryptoCoinList = new ArrayList<>();
        Float minYValue = null;
        Float maxYValue = null;


        for (CryptocoinHistory cryptocoinHistory : cryptocoinHistories) {

            if (minYValue == null) {
                minYValue = cryptocoinHistory.getClose();
            }
            else {
                if (minYValue > cryptocoinHistory.getClose()) {
                    minYValue = cryptocoinHistory.getClose();
                }
            }

            if (maxYValue == null) {
                maxYValue = cryptocoinHistory.getClose();
            }
            else {
                if (maxYValue < cryptocoinHistory.getClose()) {
                    maxYValue = cryptocoinHistory.getClose();
                }
            }

            cryptoCoinList.add(cryptocoinHistory.getClose());
        }

        minYValue = new Float (floor(minYValue));
        maxYValue = new Float (ceil(maxYValue));

        ChartDataWrapper chartDataWrapper = new ChartDataWrapper(cryptoCoinList,startIndex, endIndex, minYValue, maxYValue, "Koers " + trading.getTradePair().getCryptoCurrency().getCode());

        return chartDataWrapper;
    }
}
