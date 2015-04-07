package com.crypto.dao;


import com.crypto.entities.TradePair;
import com.crypto.entities.TradingSite;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


/**
 * Created by Jan Wicherink on 7-4-2015.
 */
public class TradePairDaoImpl implements TradePairDao {

    @PersistenceContext(unitName = "CryptoDS")
    EntityManager em;

    @Override
    public TradePair getTradePairById(Integer id) {

       Query query = em.createQuery("SELECT t FROM TradePair t WHERE t.id = " + id.toString());

       return (TradePair) query.getSingleResult();
    }

    @Override
    public void update(TradePair tradePair) {

        em.merge(tradePair);
    }

    @Override
    public TradePair getTradePairOfTradingSite(TradingSite tradingSite) {
        Query query = em.createQuery("SELECT t FROM TradePair t WHERE t.tradingSite = " + tradingSite.getCode());

        return (TradePair) query.getSingleResult();
    }
}
