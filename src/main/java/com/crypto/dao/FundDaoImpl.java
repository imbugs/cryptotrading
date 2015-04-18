package com.crypto.dao;

import com.crypto.entities.Currency;
import com.crypto.entities.Fund;
import com.crypto.entities.TradePair;
import com.crypto.entities.Wallet;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    }

    @Override
    public Fund get(TradePair tradepair, Currency currency) {

        LOG.info("Get fund, tradepair id: " + tradepair.getId() + " currency: " + currency.getCode());

        final Query query = em.createQuery("SELECT f FROM Fund f WHERE f.tradepair.id=" + tradepair.getId() +
                                           " AND f.currency.code='" + currency.getCode() + "'");
        return (Fund) query.getSingleResult();
    }
}

