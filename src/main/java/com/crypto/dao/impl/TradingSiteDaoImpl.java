package com.crypto.dao.impl;

import com.crypto.dao.TradingSiteDao;
import com.crypto.entities.TradingSite;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Trading site Dao implementation
 * <p/>
 * Created by Jan Wicherink on 13-4-2015.
 */
@Stateless
public class TradingSiteDaoImpl implements TradingSiteDao {

    private static final long serialVersionUID = 9103982057375624503L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(TradingSite tradingSite) {

        em.persist(tradingSite);
    }

    @Override
    public TradingSite get(String code) {

        return em.find(TradingSite.class, code);
    }

    @Override
    public void update(TradingSite tradingSite) {

        em.merge(tradingSite);
    }
}

