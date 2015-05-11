package com.crypto.dao.impl;

import com.crypto.dao.FundHistoryDao;
import com.crypto.entities.FundHistory;
import com.crypto.entities.Trading;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jan Wicherink on 18-4-15.
 */
@Stateful
public class FundHistoryDaoImpl implements FundHistoryDao {

    private static final long serialVersionUID = 3331197354254100509L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(FundHistory fundHistory) {

        em.persist(fundHistory);
    }

    @Override
    public List<FundHistory> getAll(Trading trading) {

       final TypedQuery<FundHistory> query = (TypedQuery<FundHistory>) em.createQuery("SELECT f FROM FundHistory f WHERE f.trading = :trading");
       query.setParameter ("trading", trading);

       return query.getResultList();
    }
}