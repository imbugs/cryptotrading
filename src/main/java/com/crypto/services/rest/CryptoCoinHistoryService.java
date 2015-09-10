package com.crypto.services.rest;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
import com.google.gson.Gson;
import com.sun.media.jfxmedia.Media;
import javassist.NotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
            }
            else {
                throw new NotFoundException("Current cryptocoin history not found for tradepair :" + tradePair.getId());
            }
        } else {
            throw new NotFoundException("Current trading not found");
        }
    }
}
