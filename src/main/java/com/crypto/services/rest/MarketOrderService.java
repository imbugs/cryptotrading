package com.crypto.services.rest;

import com.crypto.dao.MarketOrderDao;
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
    private MarketOrderDao marketOrderDao;

    @POST
    @Path("/marketOrders/{trading}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllMarketOrders(@QueryParam("trading") String tradingJsonStr) {

        final Gson gson = new Gson();

        final Trading trading = gson.fromJson (tradingJsonStr, Trading.class);

        final List<MarketOrder> marketOrders = marketOrderDao.getAll(trading);

        final String jsonString = gson.toJson(marketOrders);

        return jsonString;
    }
}

