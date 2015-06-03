package com.crypto.datahandler.impl;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.TrendDao;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.MacdDataProvider;
import com.crypto.entities.*;

import javax.ejb.EJB;
import java.util.List;

/**
 * Macd bulk data handler
 *
 * Created by Jan Wicherink on 2-6-15.
 */
public class MacdBulkDataHandler extends BulkDataHandler implements BulkDataProvider<CryptocoinHistory>, MacdDataProvider, DataPersister<MacdValue> {

    @EJB
    private CryptocoinHistoryDao cryptocoinHistoryDao;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    @EJB
    private TrendDao trendDao;

    private Macd macd;

    private TradePair tradePair;

    /**
     * Default constructor
     */
    public MacdBulkDataHandler() {

    }

    @Override
    public TrendValue getShortTrendValue(final Integer index) {
        return null;
    }

    @Override
    public TrendValue getLongTrendValue(final Integer index) {

        return null;
    }

    @Override
    public MacdValue getMacdValue(final Integer index) {
        return null;
    }

    @Override
    public TradePair getTradePair() {
        return this.tradePair;
    }

    @Override
    public void setTradePair(final TradePair tradePair) {
        this.tradePair = tradePair;
    }

    @Override
    public void storeValue(final MacdValue value) {

    }
}
