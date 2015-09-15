package com.crypto.services.rest;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
import com.crypto.services.rest.wrapper.CryptoCoinRangeWrapper;
import javassist.NotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Rest service rendering crypto coin history rest
 * <p/>
 * Created by Jan Wicherink on 19-8-15.
 */
@Path("/")
@Stateless
public class CryptoCoinHistoryService {

    @EJB
    private TradingDao tradingDao;

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @GET
    @Path("/currentCryptoRate/{tradingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CryptocoinHistory getCurrentCryptoRate(@PathParam("tradingId") Integer tradingId) throws NotFoundException {

        final Trading trading = tradingDao.get(tradingId);

        if (trading != null) {

            TradePair tradePair = trading.getTradePair();

            final CryptocoinHistory cryptocoinHistory = cryptocoinHistoryDao.getLast(tradePair);

            if (cryptocoinHistory != null) {
                return cryptocoinHistory;
            } else {
                throw new NotFoundException("Current cryptocoin history not found for tradepair :" + tradePair.getId());
            }
        } else {
            throw new NotFoundException("Current trading not found");
        }
    }


    @GET
    @Path("/getCryptoCoinIndexRange/{tradingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CryptoCoinRangeWrapper getCryptoCoinIndexRange(@PathParam("tradingId") Integer tradingId) throws NotFoundException {

        final Trading trading = tradingDao.get(tradingId);

        if (trading != null) {

            TradePair tradePair = trading.getTradePair();

            final Integer minIndex = cryptocoinHistoryDao.getStartIndex(tradePair);
            final Integer maxIndex = cryptocoinHistoryDao.getLastIndex(tradePair);

            final CryptoCoinRangeWrapper cryptoCoinRangeWrapper = new CryptoCoinRangeWrapper(minIndex, maxIndex);

            return cryptoCoinRangeWrapper;
        } else {
            throw new NotFoundException("Current trading not found");
        }
    }
}
