package com.crypto.dao.impl;

import com.crypto.dao.SignalDao;
import com.crypto.entities.Signal;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by Jan Wicherink on 29-4-15.
 */
@Stateless
public class SignalDaoImpl implements SignalDao {

    private static final long serialVersionUID = -3751694320331535266L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(Signal signal) {

        em.persist(signal);
    }

    @Override
    public Signal get(final Integer indx, final TradeRule tradeRule, final Trading trading) {

        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE s.pk.trading = :trading AND s.tradeRule = :tradeRule AND s.pk.index = :indx");

        query.setParameter("trading", trading);
        query.setParameter("tradeRule", tradeRule);
        query.setParameter("indx", indx);

        return query.getSingleResult();
    }

    @Override
    public Signal getLast(Trading trading) {

        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE s.pk.trading = :trading AND s.pk.index = (SELECT MAX(s2.pk.index) FROM Signal s2 WHERE s2.pk.trading = :trading)");
        query.setParameter("trading", trading);

        return query.getSingleResult();
    }
}
