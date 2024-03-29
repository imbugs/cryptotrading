package com.crypto.dao.impl;

import com.crypto.dao.TradingDao;
import com.crypto.entities.Trading;
import com.crypto.entities.TradingSite;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jan Wicherink on 25-4-15.
 */
@Stateless
public class TradingDoaImpl implements TradingDao {

    private static final long serialVersionUID = -8024256375977572764L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public Trading get(Integer Id) {
        return em.find(Trading.class, Id);
    }

    @Override
    public List<Trading> getAll() {
        TypedQuery<Trading> query = (TypedQuery<Trading>) em.createQuery("SELECT t FROM Trading t");

        return query.getResultList();
    }

    @Override
    public List<Trading> getActiveTradings() {
        TypedQuery<Trading> query = (TypedQuery<Trading>) em.createQuery("SELECT t FROM Trading t WHERE t.enabled = 1");

        return query.getResultList();
    }

    @Override
    public List<Trading> getActiveTradingsOfTradingSite(TradingSite tradingSite) {
        TypedQuery<Trading> query = (TypedQuery<Trading>) em.createQuery("SELECT t FROM Trading t, TradePair p WHERE t.tradePair.id = p.id  AND t.enabled = 1 AND p.tradingSite = :tradingSite");
        query.setParameter("tradingSite", tradingSite);

        return query.getResultList();
    }

    @Override
    public void update(Trading trading) {

        em.merge(trading);
    }
}
