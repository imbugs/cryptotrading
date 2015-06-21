package com.crypto.trading.services;

/**
 * Delivers services for using the trading crypto trading environment
 *
 * Created by Jan Wicherink on 27-5-15.
 */

import com.crypto.dao.TradingDao;
import com.crypto.entities.Trading;
import com.crypto.trading.Utils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Rest service returning currency
 */
@Path("/")
@Stateless
public class TradingServices {

    @EJB
    private Utils utils;

    @EJB
    private TradingDao tradingDao;

    /**
     * Recalculates all the trendlines and signals of a trading
     */
    @POST
    @Path("/recalculate/")
    public void recalculate() {

        final Trading trading = tradingDao.get(1);

        utils.calculateTrendLines(trading);
    }
}