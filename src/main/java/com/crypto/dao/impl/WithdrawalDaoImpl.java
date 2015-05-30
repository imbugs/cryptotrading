package com.crypto.dao.impl;

import com.crypto.dao.WithdrawalDao;
import com.crypto.entities.Currency;
import com.crypto.entities.Trading;
import com.crypto.entities.Withdrawal;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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

        return query.getSingleResult();
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
}
