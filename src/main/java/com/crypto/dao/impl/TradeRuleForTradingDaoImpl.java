package com.crypto.dao.impl;

import com.crypto.dao.TradeRuleDao;
import com.crypto.dao.TradeRuleForTradingDao;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;
import com.crypto.enums.MarketTrend;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Trade rule for util Dao
 * <p/>
 * Created by Jan Wicherink on 7-5-15.
 */
@Stateless
public class TradeRuleForTradingDaoImpl implements TradeRuleForTradingDao {

    private static final long serialVersionUID = -7367069861076378951L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;


    @Override
    public List<TradeRule> getTradeRules(Trading trading) {
        final TypedQuery<TradeRule> query = (TypedQuery<TradeRule>) em.createQuery("SELECT tr FROM TradeRuleForTrading trt, TradeRule tr, Trading t  WHERE trt.tradeRule = tr.Id AND trt.trading=:trading");
        query.setParameter("trading", trading);

        return query.getResultList();
    }
}
