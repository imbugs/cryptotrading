package com.crypto.dao.impl;

import com.crypto.dao.TrendDao;
import com.crypto.entities.Trend;
import com.crypto.enums.TrendType;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The Trend Dao implementation
 * <p/>
 * Created by Jan Wicherink on 15-4-15.
 */
@Stateless
public class TrendDaoImpl implements TrendDao {

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public Trend get(Integer Id) {

        return em.find(Trend.class, Id);
    }

    @Override
    public List<Trend> getAllMovingAverageTrends() {
        final TypedQuery<Trend> query = (TypedQuery<Trend>) em.createQuery("SELECT t FROM Trend t WHERE t.type =:ma");
        query.setParameter("ma", TrendType.MA);

        return query.getResultList();
    }

    @Override
    public List<Trend> getAllExponentialMovingAverageTrends() {
        final TypedQuery<Trend> query = (TypedQuery<Trend>) em.createQuery("SELECT t FROM Trend t WHERE t.type = :ema");
        query.setParameter("ema", TrendType.EMA);

        return query.getResultList();
    }

    @Override
    public List<Trend> getAllSmoothingMovingAverageTrends() {
        final TypedQuery<Trend> query = (TypedQuery<Trend>) em.createQuery("SELECT t FROM Trend t WHERE t.type =:sma");
        query.setParameter("sma", TrendType.SMA);

        return query.getResultList();
    }
}
