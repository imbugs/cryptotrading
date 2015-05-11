package com.crypto.dao.impl;

import com.crypto.dao.TrendDao;
import com.crypto.entities.Trend;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The Trend Dao implementation
 *
 * Created by Jan Wicherink on 15-4-15.
 */
@Stateful
public class TrendDaoImpl implements TrendDao {


    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public Trend get(Integer Id) {

        return em.find(Trend.class, Id);
    }

    @Override
    public List<Trend> getAllMovingAverageTrends() {
        final TypedQuery <Trend> query = (TypedQuery<Trend>) em.createQuery("SELECT t FROM Trend t WHERE t.type = 'MA'");

        return query.getResultList();
    }

    @Override
    public List<Trend> getAllExponentialMovingAverageTrends() {
        final TypedQuery<Trend> query = (TypedQuery<Trend>) em.createQuery("SELECT t FROM Trend t WHERE t.type = 'EMA'");

        return query.getResultList();
    }

    @Override
    public List<Trend> getAllSmoothingMovingAverageTrends() {
        final TypedQuery <Trend> query = (TypedQuery<Trend>) em.createQuery("SELECT t FROM Trend t WHERE t.type = 'SMA'");

        return query.getResultList();
    }
}
