package com.crypto.dao.impl;

import com.crypto.dao.MacdDao;
import com.crypto.entities.Macd;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of the Macd Dao
 * <p/>
 * Created by Jan Wicherink on 4-5-15.
 */
@Stateless
public class MacdDaoImpl implements MacdDao {

    private static final long serialVersionUID = -4852177382690819345L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public List<Macd> getAll() {
        final TypedQuery<Macd> query = (TypedQuery<Macd>) em.createQuery("SELECT m FROM Macd m");

        return query.getResultList();
    }

    @Override
    public Macd get(Integer id) {
        final TypedQuery<Macd> query = (TypedQuery<Macd>) em.createQuery("SELECT m FROM Macd m WHERE m.id=:id");
        query.setParameter("id", id);

        return query.getSingleResult();
    }
}
