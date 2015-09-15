package com.crypto.dao.impl;

import com.crypto.dao.FundDao;
import com.crypto.entities.Currency;
import com.crypto.entities.Fund;
import com.crypto.entities.TradePair;
import com.crypto.entities.pkey.FundPk;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.logging.Logger;

/**
 * Created by jan on 16-4-15.
 */
@Stateless
public class FundDaoImpl implements FundDao {

    private static final long serialVersionUID = 5827910951806035912L;

    private static final Logger LOG = Logger.getLogger(FundDaoImpl.class.getName());

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void perist(Fund fund) {
        em.persist(fund);
    }

    @Override
    public Fund get(TradePair tradepair, Currency currency) {

        LOG.info("Get fund, tradepair id: " + tradepair.getId() + " currency: " + currency.getCode());

        final FundPk pk = new FundPk(tradepair, currency);

        return em.find(Fund.class, pk);
    }

    @Override
    public void deleteAll(TradePair tradePair) {

        final Query query = em.createQuery("DELETE FROM Fund f WHERE f.pk.tradePair=:tradePair AND f.pk.currency=:currency");
        query.setParameter("tradePair", tradePair);
        query.setParameter("currency", tradePair.getCurrency());
        query.executeUpdate();

        query.setParameter("currency", tradePair.getCryptoCurrency());
        query.executeUpdate();
    }
}