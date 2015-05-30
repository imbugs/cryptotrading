package com.crypto.dao.impl;

import com.crypto.dao.WalletHistoryDao;
import com.crypto.entities.Trading;
import com.crypto.entities.WalletHistory;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Wallet history dao implementation
 * Created by jan on 7-5-15.
 */
@Stateless
public class WalletHistoryDaoImpl implements WalletHistoryDao {

    private static final long serialVersionUID = -62342003231846814L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public List<WalletHistory> retrieveAll() {
        final TypedQuery<WalletHistory> query = (TypedQuery<WalletHistory>) em.createQuery("SELECT w FROM WalletHistory w");

        return query.getResultList();
    }

    @Override
    public WalletHistory getLast(Trading trading) {
        final TypedQuery<WalletHistory> query = (TypedQuery<WalletHistory>) em.createQuery("SELECT w FROM WalletHistory w WHERE w.pk.trading=:trading ORDER BY w.pk.timestamp DESC");
        query.setParameter("trading", trading);

        return query.getResultList().get(0);
    }

    @Override
    public void persist(WalletHistory walletHistory) {
        em.persist(walletHistory);
    }
}
