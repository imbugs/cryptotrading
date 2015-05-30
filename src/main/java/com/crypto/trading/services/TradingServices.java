package com.crypto.trading.services;

/**
 * Delivers services for using the trading crypto trading environment
 *
 * Created by Jan Wicherink on 27-5-15.
 */

import com.crypto.dao.TradePairDao;
import com.crypto.dao.TradingSiteDao;
import com.crypto.entities.TradePair;
import com.crypto.entities.TradingSite;
import com.crypto.trading.Utils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Rest service returning currency
 */
@Path("/")
@Stateless
public class TradingServices {

    @EJB
    private Utils utils;

    @EJB
    private TradePairDao tradePairDao;

    @EJB
    private TradingSiteDao tradingSiteDao;

    /**
     * Recalculates all the trendlines and signals of a trading
     */
    @POST
    @Path("/recalculate/")
    public void recalculate() {

        final TradingSite tradingSite = tradingSiteDao.get("KRAKEN");

        final TradePair tradePair = tradePairDao.getTradePairOfTradingSite(tradingSite);

        utils.calculateTrendLines(tradePair);
    }
}