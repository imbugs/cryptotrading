package com.crypto.services.rest;

/**
 * Delivers rest for using the util crypto util environment
 *
 * Created by Jan Wicherink on 27-5-15.
 */

import com.crypto.calculator.bulk.CalculationProgress;
import com.crypto.calculator.bulk.CryptoCoinHistoryTrendCalculator;
import com.crypto.dao.TradingDao;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
import com.google.gson.Gson;
import javassist.NotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Rest service returning trading information
 */
@Path("/")
@Stateful
@SessionScoped
public class TradingServices implements Serializable{

    private static final long serialVersionUID = -5594656575774682432L;

    @EJB
    private CryptoCoinHistoryTrendCalculator cryptoCoinHistoryTrendCalculator;

    @EJB
    private TradingDao tradingDao;

    /**
     * Get the current trading
     * @return the current trading
     */
    @GET
    @Path("/getCurrentTrading/")
    @Produces(MediaType.APPLICATION_JSON)
    public Trading getCurrentTrading() throws NotFoundException {

        final List<Trading> tradings = tradingDao.getAll();

        if (tradings.get(0) != null) {
            return tradings.get(0);
        }
        else {
            throw new NotFoundException("Current Trading not found");
        }
    }

    /**
     * Recalculates all the trendlines and signals of a util.
     */
    @POST
    @Path("/recalculate/{tradingId}")
    public void reCalculate(@PathParam("tradingId") Integer tradingId) throws ExecutionException, InterruptedException {

        final Trading trading = tradingDao.get(tradingId);

        cryptoCoinHistoryTrendCalculator.init(trading);
        cryptoCoinHistoryTrendCalculator.recalculateInParallel();
    }

    /**\
     * Get current calculation status
     * @return the calculation status.
     */
    @GET
    @Path("/calculationStatus/")
    @Produces (MediaType.APPLICATION_JSON)
    public HashMap<String, CalculationProgress> getCalculationStatus() {
      return cryptoCoinHistoryTrendCalculator.getCalculationProgress();
    }
}