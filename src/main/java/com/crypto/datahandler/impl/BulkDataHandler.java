package com.crypto.datahandler.impl;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TrendDao;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.DataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.Trading;
import com.crypto.entities.TrendValue;

import javax.ejb.EJB;
import java.util.List;

/**
 * Bulk data handler
 *
 * Created by Jan Wicherink on 2-6-15.
 */
public class BulkDataHandler {

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TrendDao trendDao;

    private Trading trading;


    /**
     * Default constructor
     */
    public BulkDataHandler() {

    }

    public List<CryptocoinHistory> getAll() {

        return cryptocoinHistoryDao.getAll(this.trading.getTradePair());
    }

    public Integer getStartIndex() {
        return cryptocoinHistoryDao.getStartIndex(this.trading.getTradePair());
    }

    public CryptocoinHistory getLast() {
        return cryptocoinHistoryDao.getLast(this.trading.getTradePair());
    }


    public TradePair getTradePair() {
        return this.trading.getTradePair();
    }

    public CryptocoinHistoryDao getCryptocoinHistoryDao() {
        return cryptocoinHistoryDao;
    }

    public CryptocoinTrendDao getCryptocoinTrendDao() {
        return cryptocoinTrendDao;
    }

    public TrendDao getTrendDao() {
        return trendDao;
    }

    public Trading getTrading() {
        return trading;
    }

    public void setTrading(Trading trading) {
        this.trading = trading;
    }
}
