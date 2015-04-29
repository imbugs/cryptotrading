package com.crypto.dao;

import com.crypto.entities.Signal;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by Jan Wicherink on 29-4-15.
 */
@Stateful
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

        //   final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE s.trading = :trading AND s.tradeRule = :tradeRule AND s.indx = :indx");
        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE indx= :indx");

        query.setParameter("trading", trading);
        query.setParameter("tradeRule", tradeRule);
        query.setParameter("indx", indx);

        return query.getSingleResult();
    }

    @Override
    public Signal getLast(Trading trading) {

        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE s.trading = :trading AND s.indx = (SELECT MAX(s2.indx) FROM Signal s2 WHERE s2.trading = :trading)");
        query.setParameter("trading", trading);

        return query.getSingleResult();
    }
}
