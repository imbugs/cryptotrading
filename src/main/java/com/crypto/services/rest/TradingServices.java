package com.crypto.services.rest;

/**
 * Delivers rest for using the util crypto util environment
 *
 * Created by Jan Wicherink on 27-5-15.
 */

import com.crypto.dao.TradingDao;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
import com.crypto.util.Utils;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Rest service returning trading information
 */
@Path("/")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class TradingServices {

    @EJB
    private Utils utils;

    @EJB
    private TradingDao tradingDao;

    /**
     * Get the current trading (site and coin versus crypto coins)
     * @return the current trading
     */
    @GET
    @Path("/getTrading/")
    @Produces("application/text")
    public String getCurrenetTrading () {

       final List<Trading> tradings = tradingDao.getAll();

       if (tradings.get(0) != null) {

           TradePair tradePair = tradings.get(0).getTradePair();
           String tradingSiteTitle = tradePair.getTradingSite().getDescription() +
                                     " (" + tradePair.getCryptoCurrency().getDescription() + "/"
                                          + tradePair.getCurrency().getDescription() + ")";
           return tradingSiteTitle;
       }
       else {
           throw new NotFoundException("Current Trading not found");
       }
    }

    /**
     * Recalculates all the trendlines and signals of a util.
     */
    @POST
    @Path("/recalculateInParallel/")
    public void recalculate() throws ExecutionException, InterruptedException {

        final Trading trading = tradingDao.get(0);

        utils.calculateTrendLines(trading);
    }
}