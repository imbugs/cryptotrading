package com.crypto.dao;

import com.crypto.entities.MovingAverage;
import com.crypto.entities.*;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;

/**
 * Cryptocoin trend dao implementation
 * Created by jan on 1-5-15.
 */
@Stateful
public class CryptocoinTrendDaoImpl implements CryptocoinTrendDao {

    private static final long serialVersionUID = 2365753471222140387L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public TrendValue getTrendValue(final Integer index, final Trend trend, final TradePair tradePair) {

        final TypedQuery<TrendValue> query = (TypedQuery<TrendValue>) em.createQuery("SELECT t FROM TrendValue t WHERE t.trend=:trend AND t.tradePair = :tradePair AND t.indx=:index");
        query.setParameter("trend", trend);
        query.setParameter("tradePair", tradePair);
        query.setParameter("index", index);

        return query.getSingleResult();
    }

    @Override
    public Float getSumTrend(Integer index, Trend trend, Integer period, TradePair tradePair) {
        return null;
    }

    @Override
    public MacdValue getMacdValue(Integer index, Macd macd, TradePair tradePair) {
        return null;
    }

    @Override
    public void storeMovingAverageValue(MovingAverage ma) {

    }

    @Override
    public void storeMacdValue(MacdValue macdValue) {

    }

    @Override
    public void deleteBeforeDate(Date beforeDate) {

    }
}
