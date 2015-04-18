package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.Trend;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The Trend Dao implementation
 *
 * Created by Jan Wicherink on 15-4-15.
 */
@Stateful
public class TrendDaoImpl implements TrendDao {


    @PersistenceContext(unitName = "CryptoDS")
    EntityManager em;

    @Override
    public Trend get(Integer Id) {

        return em.find(Trend.class, Id);
    }

    @Override
    public List<Trend> getAllMovingAverageTrends() {
        final Query query = em.createQuery("SELECT t FROM Trend t WHERE t.type = 'MA'");

        return (List<Trend>) query.getResultList();
    }

    @Override
    public List<Trend> getAllExponentialMovingAverageTrends() {
        final Query query = em.createQuery("SELECT t FROM Trend t WHERE t.type = 'EMA'");

        return (List<Trend>) query.getResultList();
    }

    @Override
    public List<Trend> getAllSmoothingMovingAverageTrends() {
        final Query query = em.createQuery("SELECT t FROM Trend t WHERE t.type = 'SMA'");

        return (List<Trend>) query.getResultList();
    }
}
