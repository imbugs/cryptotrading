package com.crypto.services.rest;

import com.crypto.dao.LoggingDao;
import com.crypto.dao.TradeConditionDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.Logging;
import com.crypto.entities.Trading;
import com.crypto.enums.LoggingLevel;
import javassist.NotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Logging service.
 *
 * Created by Jan Wicherink on 11-9-2015.
 */
@Path("/")
@Stateless
public class LoggingService {

    @EJB
    private LoggingDao loggingDao;

    @EJB
    private TradingDao tradingDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getLogging/{tradingId}/{level}")
    public List<Logging> getLogging(@PathParam("tradingId") Integer tradingId, @PathParam("level") LoggingLevel level) throws NotFoundException {

        final Trading trading = tradingDao.get(tradingId);

        final List<Logging> loggings = loggingDao.getAll(trading, level);

        if (loggings != null) {
            return loggings;
        }
        else {
            throw new NotFoundException("No logging found at log level: " + level);
        }
    }
}
