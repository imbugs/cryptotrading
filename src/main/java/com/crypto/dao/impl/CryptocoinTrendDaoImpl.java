package com.crypto.dao.impl;

import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;

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

        return query.getSingleResult();
    }

    @Override
    public Float getSumTrend(Integer index, Trend trend, Integer period, TradePair tradePair) {

        final Integer fromIndex = index - period;

        final TypedQuery<Double> query = (TypedQuery<Double>) em.createQuery("SELECT SUM(t.value) FROM TrendValue t WHERE t.trend =:trend AND t.tradePair = :tradePair AND t.indx > :fromIndex AND t.indx <= :toIndex");
        query.setParameter("trend", trend);
        query.setParameter("tradePair", tradePair);
        query.setParameter("fromIndex", fromIndex);
        query.setParameter("toIndex", index);

        return new Float(query.getSingleResult());
    }

    @Override
    public MacdValue getMacdValue(Integer index, Macd macd, TradePair tradePair) {

        final TypedQuery<MacdValue> query = (TypedQuery<MacdValue>) em.createQuery("SELECT m FROM MacdValue m WHERE m.macd=:macd AND m.tradePair = :tradePair AND m.indx=:index");
        query.setParameter("macd", macd);
        query.setParameter("tradePair", tradePair);
        query.setParameter("index", index);

        return query.getSingleResult();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
}
