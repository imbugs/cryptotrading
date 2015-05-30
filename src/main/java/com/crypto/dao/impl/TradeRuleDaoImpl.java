package com.crypto.dao.impl;

import com.crypto.dao.TradeRuleDao;
import com.crypto.entities.TradeRule;
import com.crypto.enums.MarketTrend;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Trade rule Dao
 * <p/>
 * Created by Jan Wicherink on 7-5-15.
 */
@Stateless
public class TradeRuleDaoImpl implements TradeRuleDao {

    private static final long serialVersionUID = -7367069861076378951L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public TradeRule get(final Integer Id) {
        return em.find(TradeRule.class, Id);
    }

    @Override
    public List<TradeRule> getActiveRules() {
        final TypedQuery<TradeRule> query = (TypedQuery<TradeRule>) em.createQuery("SELECT t FROM TradeRule t WHERE t.enabled=1");

        return query.getResultList();
    }

    @Override
    public List<TradeRule> getActiveBullRules() {
        final TypedQuery<TradeRule> query = (TypedQuery<TradeRule>) em.createQuery("SELECT t FROM TradeRule t WHERE t.enabled=1 AND t.type=:type");
        query.setParameter("type", MarketTrend.BULL);

        return query.getResultList();
    }

    @Override
    public List<TradeRule> getActiveBearRules() {
        final TypedQuery<TradeRule> query = (TypedQuery<TradeRule>) em.createQuery("SELECT t FROM TradeRule t WHERE t.enabled=1 AND t.type=:type");
        query.setParameter("type", MarketTrend.BEAR);

        return query.getResultList();
    }
}
