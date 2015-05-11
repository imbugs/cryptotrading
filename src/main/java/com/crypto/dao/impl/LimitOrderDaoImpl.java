package com.crypto.dao.impl;

import com.crypto.dao.LimitOrderDao;
import com.crypto.entities.BuyLimitOrder;
import com.crypto.entities.LimitOrder;
import com.crypto.entities.SellLimitOrder;
import com.crypto.entities.Trading;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Limit Order Dao
 *
 * Created by Jan Wicherink on 3-5-15.
 */
@Stateful
public class LimitOrderDaoImpl implements LimitOrderDao {

    private static final long serialVersionUID = -2416786440836108983L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(LimitOrder order) {
      em.persist(order);
    }

    @Override
    public List<LimitOrder> getOpenLimitOrders(Trading trading) {
        final TypedQuery<LimitOrder> query = (TypedQuery<LimitOrder>) em.createQuery("SELECT l FROM LimitOrder l WHERE l.status='OPEN'");

        return query.getResultList();
    }

    @Override
    public Long getNumberOfOpenLimitOrders(Trading trading) {

        final Query query = em.createQuery("SELECT COUNT(l) FROM LimitOrder l WHERE l.status='OPEN'");

        return (Long) query.getSingleResult();
    }

    @Override
    public SellLimitOrder getLastSell(Integer beforeIndex, Trading trading) {
        final TypedQuery<LimitOrder> query = (TypedQuery<LimitOrder>) em.createQuery("SELECT l FROM LimitOrder l WHERE l.pk.index < :beforeIndex AND l.pk.trading = :trading AND ORDER_TYPE = 'SELL' ORDER BY l.timestamp DESC");
        query.setParameter("beforeIndex", beforeIndex);
        query.setParameter("trading", trading);

        return (SellLimitOrder) query.getResultList().get(0);
    }

    @Override
    public BuyLimitOrder getLastBuy(Integer beforeIndex, Trading trading) {
        final TypedQuery<LimitOrder> query = (TypedQuery<LimitOrder>) em.createQuery("SELECT l FROM LimitOrder l WHERE l.pk.index < :beforeIndex AND l.pk.trading = :trading AND ORDER_TYPE = 'BUY' ORDER BY l.timestamp DESC");
        query.setParameter("beforeIndex", beforeIndex);
        query.setParameter("trading", trading);

        return (BuyLimitOrder) query.getResultList().get(0);
    }

    @Override
    public void update(LimitOrder limitOrder) {

        em.merge(limitOrder);
    }
}
