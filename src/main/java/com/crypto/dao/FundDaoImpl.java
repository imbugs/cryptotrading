package com.crypto.dao;

import com.crypto.entities.Currency;
import com.crypto.entities.Fund;
import com.crypto.entities.TradePair;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;

/**
 * Created by jan on 16-4-15.
 */
@Stateful
public class FundDaoImpl implements FundDao {

    private static final long serialVersionUID = 5827910951806035912L;

    private static final Logger LOG = Logger.getLogger(FundDaoImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public void perist(Fund fund) {
        em.persist(fund);
    }

    @Override
    public Fund get(TradePair tradepair, Currency currency) {

        LOG.info("Get fund, tradepair id: " + tradepair.getId() + " currency: " + currency.getCode());

        final TypedQuery<Fund> query = (TypedQuery<Fund>) em.createQuery("SELECT f FROM Fund f WHERE f.tradepair = :tradepair AND f.currency = :currency");
        query.setParameter("tradepair", tradepair);
        query.setParameter("currency", currency);

        return query.getSingleResult();
    }
}