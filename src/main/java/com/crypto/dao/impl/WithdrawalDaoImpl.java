package com.crypto.dao.impl;

import com.crypto.dao.WithdrawalDao;
import com.crypto.entities.Currency;
import com.crypto.entities.Trading;
import com.crypto.entities.Withdrawal;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.Queue;

/**
 * Withdrawal Dao implementation
 * <p/>
 * Created by Jan Wicherink on 7-5-15.
 */
@Stateless
public class WithdrawalDaoImpl implements WithdrawalDao {

    private static final long serialVersionUID = 8189090762420276664L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public Withdrawal get(Trading trading, Currency currency) {
        TypedQuery<Withdrawal> query = (TypedQuery<Withdrawal>) em.createQuery("SELECT w FROM Withdrawal w WHERE w.pk.trading=:trading AND w.pk.currency=:currency");
        query.setParameter("trading", trading);
        query.setParameter("currency", currency);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Withdrawal> getAll() {
        TypedQuery<Withdrawal> query = (TypedQuery<Withdrawal>) em.createQuery("SELECT w FROM Withdrawal w");

        return query.getResultList();
    }

    @Override
    public List<Withdrawal> getWithdrawalsOfTrading(Trading trading) {
        TypedQuery<Withdrawal> query = (TypedQuery<Withdrawal>) em.createQuery("SELECT w FROM Withdrawal w WHERE w.pk.trading=:trading");
        query.setParameter("trading", trading);

        return query.getResultList();
    }

    @Override
    public void remove(Withdrawal withdrawal) {
        em.remove(withdrawal);
    }

    @Override
    public void update(Withdrawal withdrawal) {
        em.merge(withdrawal);
    }

    @Override
    public void persist(Withdrawal withdrawal) {
        em.persist(withdrawal);
    }

    @Override
    public void deleteAll(Trading trading) {

        final Query query = em.createQuery("DELETE FROM Withdrawal w WHERE w.pk.trading=:trading");
        query.setParameter("trading", trading);
        query.executeUpdate();
    }
}
