package com.crypto.dao;

import com.crypto.entities.MarketOrder;
import com.crypto.entities.Trading;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jan Wicherink on 21-4-15.
 */
@Stateful
public class MarketOrderDaoImpl implements MarketOrderDao {

    private static final long serialVersionUID = -325191410469027237L;

    @PersistenceContext (unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(MarketOrder marketOrder) {

        em.persist(marketOrder);
    }

    @Override
    public void merge(MarketOrder marketOrder) {

        em.merge(marketOrder);
    }

    @Override
    public MarketOrder getByOrderReference(String orderReferecence) {
       final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.orderReference= :orderReference");
       query.setParameter("orderReference", orderReferecence);

       return query.getSingleResult();
    }

    @Override
    public void remove(MarketOrder marketOrder) {

        em.remove(marketOrder);
    }

    @Override
    public MarketOrder getLastSell(Integer beforeIndex, Trading trading) {
        final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.pk.index < :beforeIndex AND m.pk.trading = :trading AND ORDER_TYPE = 'SELL' ORDER BY m.timestamp DESC");
        query.setParameter("beforeIndex", beforeIndex);
        query.setParameter("trading", trading);

        return query.getResultList().get(0);
    }

    @Override
    public MarketOrder getLastBuy(Integer beforeIndex, Trading trading) {
        final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.pk.index < :beforeIndex AND m.pk.trading = :trading AND ORDER_TYPE = 'BUY' ORDER BY m.timestamp DESC");
        query.setParameter("beforeIndex", beforeIndex);
        query.setParameter("trading", trading);

        return query.getResultList().get(0);
    }

    @Override
    public List<MarketOrder> getOpenOrders(Trading trading) {
        final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.pk.trading = :trading AND m.status IN ('OPEN','EXECUTING') ORDER BY m.timestamp ASC");
        query.setParameter("trading", trading);

        return query.getResultList();
    }

    @Override
    public List<MarketOrder> getOpenManualSellOrders(Trading trading) {
        final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.trading = :trading AND m.status IN ('OPEN','RETRY','EXECUTING') AND m.manuallyCreated=1 AND ORDER_TYPE='SELL' ORDER BY m.timestamp ASC");
        query.setParameter("trading", trading);

        return query.getResultList();
    }

    @Override
    public List<MarketOrder> getOpenManualBuyOrders(Trading trading) {
        final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.trading = :trading AND m.status IN ('OPEN','RETRY','EXECUTING') AND m.manuallyCreated=1 AND ORDER_TYPE='BUY' ORDER BY m.timestamp ASC");
        query.setParameter("trading", trading);

        return query.getResultList();
    }

    @Override
    public List<MarketOrder> getRetryOrders(Trading trading) {
        final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.trading = :trading AND m.status = 'RETRY' ORDER BY m.timestamp ASC");
        query.setParameter("trading", trading);

        return query.getResultList();
    }

    @Override
    public List<MarketOrder> getAll(Trading trading) {
        final TypedQuery<MarketOrder> query = (TypedQuery<MarketOrder>) em.createQuery("SELECT m FROM MarketOrder m WHERE m.trading = :trading ORDER BY m.timestamp ASC");
        query.setParameter("trading", trading);

        return query.getResultList();
    }
}
