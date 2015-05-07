package com.crypto.dao;

import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradeRule;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Trade condition Dao implementation
 * Created by Jan Wicherink on 6-5-15.
 */
@Stateful
public class TradeConditionDaoImpl implements TradeConditionDao {

    private static final long serialVersionUID = -5114145946137731357L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public TradeCondition get(Integer Id) {
        return em.find(TradeCondition.class, Id);
    }

    @Override
    public List<TradeCondition> getAllActiveTradeConditionsOfTradeRule(final TradeRule tradeRule) {
        final TypedQuery<TradeCondition> query = (TypedQuery<TradeCondition>) em.createQuery("SELECT t FROM TradeCondition t WHERE t.tradeRule=:tradeRule AND enabled=1");
        query.setParameter("tradeRule", tradeRule);

        return query.getResultList();
    }
}
