package com.crypto.services.rest;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.TradingDao;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
import com.google.gson.Gson;
import com.sun.media.jfxmedia.Media;

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
    @Path("/currentCryptoRate")
    @Produces(MediaType.TEXT_HTML)
    public String getCurrentCryptoRate() {

        final List<Trading> tradings = tradingDao.getAll();

        if (tradings.get(0) != null) {

            TradePair tradePair = tradings.get(0).getTradePair();

            final CryptocoinHistory cryptocoinHistory = cryptocoinHistoryDao.getLast(tradePair);

            if (cryptocoinHistory != null) {

                final String currentRate = cryptocoinHistory.getClose().toString() + ' ' +
                        tradePair.getCurrency().getCode() + '/' +
                        tradePair.getCryptoCurrency().getCode();

                return currentRate;
            }
            else {
                throw new NotFoundException("Current cryptocoin history not found for tradepair :" + tradePair.getId());
            }
        } else {
            throw new NotFoundException("Current trading not found");
        }
    }
}
