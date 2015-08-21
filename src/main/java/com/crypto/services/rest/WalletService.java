package com.crypto.services.rest;

import com.crypto.dao.TradingDao;
import com.crypto.dao.WalletDao;
import com.crypto.entities.Trading;
import com.crypto.entities.Wallet;
import javassist.NotFoundException;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Wallet rest service.
 *
 * Created by Jan Wicherink on 21-8-15.
 */
@Path("/")
public class WalletService {

    @EJB
    private TradingDao tradingDao;

    @EJB
    private WalletDao walletDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getWallet/{tradingId}")
    public Wallet getWallet(@PathParam("tradingId") Integer tradingId) throws NotFoundException {

        final Trading trading = tradingDao.get(tradingId);

        final Wallet wallet = walletDao.get(trading);

        if (wallet != null) {
            return wallet;
        }
        else {
            throw new NotFoundException("Wallet not found for trading :" + tradingId);
        }
    }
}
