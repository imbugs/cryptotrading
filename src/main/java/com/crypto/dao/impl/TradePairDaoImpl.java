package com.crypto.dao.impl;


import com.crypto.dao.TradePairDao;
import com.crypto.entities.TradePair;
import com.crypto.entities.TradingSite;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


/**
 * The trade pair Dao
 * <p/>
 * Created by Jan Wicherink on 7-4-2015.
 */
@Stateful
public class TradePairDaoImpl implements TradePairDao {

    private static final long serialVersionUID = -8127877891137126595L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(TradePair tradePair) {
        em.persist(tradePair);
    }

    @Override
    public TradePair get(Integer id) {

        final Query query = em.createQuery("SELECT t FROM TradePair t WHERE t.id = " + id.toString());

        return (TradePair) query.getSingleResult();
    }

    @Override
    public void update(TradePair tradePair) {

        em.merge(tradePair);
    }

    @Override
    public TradePair getTradePairOfTradingSite(TradingSite tradingSite) {
        final TypedQuery<TradePair> query = (TypedQuery<TradePair>) em.createQuery("SELECT t FROM TradePair t WHERE t.tradingSite = :tradingSite");
        query.setParameter("tradingSite", tradingSite);

        return query.getSingleResult();
    }
}
