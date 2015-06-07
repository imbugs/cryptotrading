package com.crypto.datahandler.impl;

import com.crypto.dao.CryptocoinHistoryDao;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.dao.MacdDao;
import com.crypto.dao.TrendDao;
import com.crypto.datahandler.persister.DataPersister;
import com.crypto.datahandler.provider.BulkDataProvider;
import com.crypto.datahandler.provider.MacdDataProvider;
import com.crypto.entities.*;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.List;

/**
 * Macd bulk data handler
 *
 * Created by Jan Wicherink on 2-6-15.
 */
@Stateful
@LocalBean
public class MacdBulkDataHandler extends BulkDataHandler implements BulkDataProvider<CryptocoinHistory>, MacdDataProvider, DataPersister<MacdValue> {

    @EJB
    private MacdDao macdDao;

    private Macd macd;


    /**
     * Default constructor
     */
    public MacdBulkDataHandler() {

    }

    /**
     * Get all Macd's
     * @return all of the macds
     */
    public List<Macd> getAllMacds() {
        return macdDao.getAll();
    }

    @Override
    public TrendValue getShortTrendValue(final Integer index) {

       return this.getCryptocoinTrendDao().getTrendValue(index, macd.getShortTrend(), getTradePair());
    }

    @Override
    public TrendValue getLongTrendValue(final Integer index) {
        return this.getCryptocoinTrendDao().getTrendValue(index, macd.getLongTrend(), getTradePair());
    }

    @Override
    public MacdValue getMacdValue(final Integer index) {
        return this.getCryptocoinTrendDao().getMacdValue(index, this.macd, getTradePair());
    }

    @Override
    public void storeValue(final MacdValue value) {
        this.getCryptocoinTrendDao().storeMacdValue(value);
    }

    public Macd getMacd() {
        return macd;
    }

    public void setMacd(Macd macd) {
        this.macd = macd;
    }
}
