package com.crypto.dao;

import com.crypto.entities.Trading;
import com.crypto.entities.Wallet;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The wallet Dao implementation
 * <p/>
 * Created by Jan Wicherink on 15-4-15.
 */
@Stateful
public class WalletDaoImpl implements WalletDao {

    private static final long serialVersionUID = -2760326770511695578L;

    @PersistenceContext(unitName = "CryptoDS")
    EntityManager em;

    @Override
    public Wallet get(Trading trading) {
        final Query query = em.createQuery("SELECT w FROM Wallet w WHERE w.trading.id=" + trading.getId());
        return (Wallet) query.getSingleResult();
    }
}
