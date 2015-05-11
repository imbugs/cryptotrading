package com.crypto.dao.impl;

import com.crypto.dao.FundDao;
import com.crypto.entities.Currency;
import com.crypto.entities.Fund;
import com.crypto.entities.TradePair;
import com.crypto.entities.pkey.FundPk;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * Created by jan on 16-4-15.
 */
@Stateful
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

        final FundPk pk = new FundPk(tradepair,currency);

        return em.find(Fund.class, pk);
    }
}