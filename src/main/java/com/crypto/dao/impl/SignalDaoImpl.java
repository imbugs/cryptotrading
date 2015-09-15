package com.crypto.dao.impl;

import com.crypto.dao.SignalDao;
import com.crypto.entities.Signal;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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

        final Signal persistedSignal = em.find(Signal.class, signal.getPk());

        if (persistedSignal == null) {
            em.persist(signal);
        }
    }

    @Override
    public Signal get(final Integer indx, final TradeRule tradeRule, final Trading trading) {

        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE s.pk.trading = :trading AND s.tradeRule = :tradeRule AND s.pk.index = :indx");

        query.setParameter("trading", trading);
        query.setParameter("tradeRule", tradeRule);
        query.setParameter("indx", indx);

        Signal signal;

        try {
            signal = query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
        return signal;
    }

    @Override
    public List<Signal> getAll(Integer fromIndx, Integer toIndex, Trading trading) {
        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE s.pk.trading = :trading AND s.pk.index >= :fromIndx AND s.pk.index <= :toIndex");
        query.setParameter("trading", trading);
        query.setParameter("fromIndx", fromIndx);
        query.setParameter("toIndex", toIndex);

        List<Signal> signals;

        try {
            signals = query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        return signals;
    }

    @Override
    public Signal getLast(Trading trading) {

        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("SELECT s FROM Signal s WHERE s.pk.trading = :trading AND s.pk.index = (SELECT MAX(s2.pk.index) FROM Signal s2 WHERE s2.pk.trading = :trading)");
        query.setParameter("trading", trading);

        Signal signal;

        try {
            signal = query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }

        return signal;
    }

    @Override
    public void truncateSignalData(Trading trading) {

        final TypedQuery<Signal> query = (TypedQuery<Signal>) em.createQuery("DELETE FROM Signal s WHERE s.pk.trading = :trading");
        query.setParameter("trading", trading);

        query.executeUpdate();
    }

    @Override
    public void commit() {
        em.flush();
    }
}
