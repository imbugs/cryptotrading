package com.crypto.dao.impl;

import com.crypto.dao.WalletDao;
import com.crypto.entities.Trading;
import com.crypto.entities.Wallet;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * The wallet Dao implementation
 * <p/>
 * Created by Jan Wicherink on 15-4-15.
 */
@Stateless
public class WalletDaoImpl implements WalletDao {

    private static final long serialVersionUID = -2760326770511695578L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(final Wallet wallet) {
        em.persist(wallet);
    }

    @Override
    public Wallet get(Trading trading) {
        final TypedQuery<Wallet> query = (TypedQuery<Wallet>) em.createQuery("SELECT w FROM Wallet w WHERE w.trading= :trading");
        query.setParameter("trading", trading);

        return query.getSingleResult();
    }

    @Override
    public void deleteAll(final Trading trading) {
        final Query query = em.createQuery("DELETE FROM Wallet w WHERE w.trading= :trading");
        query.setParameter("trading", trading);
        query.executeUpdate();
    }
}
