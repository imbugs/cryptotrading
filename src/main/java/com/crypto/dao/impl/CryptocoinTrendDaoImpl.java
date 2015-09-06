package com.crypto.dao.impl;

import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cryptocoin trend dao implementation
 * Created by jan on 1-5-15.
 */
@Stateless
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

        TrendValue trendValue;

        try {
            trendValue = query.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }

        return trendValue;
    }

    @Override
    public List<TrendValue> getAllTrendValues(final Integer fromIndex, final Integer toIndex, final Trend trend, final TradePair tradePair) {

        final TypedQuery<TrendValue> query = (TypedQuery<TrendValue>) em.createQuery("SELECT t FROM TrendValue t WHERE t.trend=:trend AND t.tradePair = :tradePair AND t.indx>=:fromIndex AND t.indx<=:toIndex ORDER BY t.indx ASC");
        query.setParameter("trend", trend);
        query.setParameter("tradePair", tradePair);
        query.setParameter("fromIndex", fromIndex);
        query.setParameter("toIndex", toIndex);

        List<TrendValue> trendValues;

        try {
            trendValues = query.getResultList();
        }
        catch (Exception e) {
            return null;
        }
        return trendValues;
    }

    @Override
    public Float getSumTrend(Integer index, Trend trend, Integer period, TradePair tradePair) {

        final Integer fromIndex = index - period;

        final TypedQuery<Double> query = (TypedQuery<Double>) em.createQuery("SELECT SUM(t.value) FROM TrendValue t WHERE t.trend =:trend AND t.tradePair = :tradePair AND t.indx > :fromIndex AND t.indx <= :toIndex");
        query.setParameter("trend", trend);
        query.setParameter("tradePair", tradePair);
        query.setParameter("fromIndex", fromIndex);
        query.setParameter("toIndex", index);
        query.getSingleResult();

        Double sum;

        try {
            sum = query.getSingleResult();
        }
        catch (NoResultException e) {
            return 0F;
        }

        if (sum != null) {
            return new Float(sum);
        }

        return null;
    }

    @Override
    public MacdValue getMacdValue(Integer index, Macd macd, TradePair tradePair) {

        final TypedQuery<MacdValue> query = (TypedQuery<MacdValue>) em.createQuery("SELECT m FROM MacdValue m WHERE m.macd=:macd AND m.tradePair = :tradePair AND m.indx=:index");
        query.setParameter("macd", macd);
        query.setParameter("tradePair", tradePair);
        query.setParameter("index", index);

        MacdValue macdValue;

        try {
            macdValue = query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }

        return macdValue;
    }

    @Override
    public List<MacdValue> getAllMacdValues(Integer startIndex, Integer endIndex, Macd macd, TradePair tradePair) {

        final TypedQuery<MacdValue> query = (TypedQuery<MacdValue>) em.createQuery("SELECT m FROM MacdValue m WHERE m.macd=:macd AND m.tradePair = :tradePair AND m.indx>=:startIndex AND m.indx <=:endIndex");
        query.setParameter("macd", macd);
        query.setParameter("tradePair", tradePair);
        query.setParameter("startIndex", startIndex);
        query.setParameter("endIndex", endIndex);

       List<MacdValue> macdValues = new ArrayList<>();

        try {
            macdValues = query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }

        return macdValues;
    }


    @Override
    public void storeTrendValue(TrendValue trendValue) {

        em.persist(trendValue);
    }

    @Override
    public void storeMacdValue(MacdValue macdValue) {

        em.persist(macdValue);
    }

    @Override
    public void deleteBeforeDate(Date beforeDate) {

        final TypedQuery query = (TypedQuery) em.createQuery("DELETE FROM TrendValue t WHERE t.indx IN (SELECT c.pk.indx FROM CryptocoinHistory c WHERE c.timestamp < :timestamp)");
        query.setParameter("timestamp", new Timestamp(beforeDate.getTime()));
        query.executeUpdate();
    }

    @Override
    public void truncateTable() {
        final TypedQuery query =  (TypedQuery) em.createQuery("DELETE FROM TrendValue");

        query.executeUpdate();
        em.flush();
    }

    @Override
    public void commit() {
        em.flush();
    }
}
