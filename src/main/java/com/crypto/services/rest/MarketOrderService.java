package com.crypto.services.rest;

import com.crypto.dao.MarketOrderDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.MarketOrder;
import com.crypto.entities.Trading;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Rest service returning market orders.
 */
@Path("/")
@Stateless
public class MarketOrderService {

    @EJB
    private TradingDao tradingDao;

    @EJB
    private MarketOrderDao marketOrderDao;

    @GET
    @Path("/marketOrders/{tradingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MarketOrder> getAllMarketOrders(@PathParam("tradingId") Integer tradingId) {

        final Trading trading = tradingDao.get(tradingId);

        final List<MarketOrder> marketOrders = marketOrderDao.getAll(trading);

        return marketOrders;
    }
}