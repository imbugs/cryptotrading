package com.crypto.services;

/**
 * Delivers services for using the util crypto util environment
 *
 * Created by Jan Wicherink on 27-5-15.
 */

import com.crypto.dao.TradingDao;
import com.crypto.entities.Trading;
import com.crypto.util.Utils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

/**
 * Rest service returning currency
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
     * Recalculates all the trendlines and signals of a util
     */
    @POST
    @Path("/recalculate/")
    public void recalculate() throws ExecutionException, InterruptedException {

        final Trading trading = tradingDao.get(1);

        utils.calculateTrendLines(trading);
    }
}