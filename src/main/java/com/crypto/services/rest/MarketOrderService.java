package com.crypto.services.rest;

import com.crypto.dao.MarketOrderDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.MarketOrder;
import com.crypto.entities.Trading;
import com.crypto.services.rest.wrapper.MarketOrderWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
    public List<MarketOrderWrapper> getAllMarketOrders(@PathParam("tradingId") Integer tradingId) {

        final Trading trading = tradingDao.get(tradingId);

        final List<MarketOrder> marketOrders = marketOrderDao.getAll(trading);
        final List<MarketOrderWrapper> marketOrderWrappers = new ArrayList<>();

        marketOrders.forEach( marketOrder -> {

            final MarketOrderWrapper marketOrderWrapper = new MarketOrderWrapper(marketOrder);
            marketOrderWrappers.add(marketOrderWrapper);
        });

        return marketOrderWrappers;
    }
}