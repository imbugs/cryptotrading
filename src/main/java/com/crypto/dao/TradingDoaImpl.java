package com.crypto.dao;

import com.crypto.entities.Trading;
import com.crypto.entities.TradingSite;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Jan Wicherink on 25-4-15.
 */
@Stateful
public class TradingDoaImpl implements TradingDao {

    private static final long serialVersionUID = -8024256375977572764L;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Trading get(Integer Id) {
         return em.find (Trading.class, Id);
    }

    @Override
    public List<Trading> getAll() {

        return null;
    }

    @Override
    public List<Trading> getActiveTradings() {

        return null;
    }

    @Override
    public List<Trading> getActiveTradingsOfTradingSite(TradingSite tradingSite) {
        return null;
    }

    @Override
    public void update(Trading trading) {

    }
}
