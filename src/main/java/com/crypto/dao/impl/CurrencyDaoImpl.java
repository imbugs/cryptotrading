package com.crypto.dao.impl;

import com.crypto.dao.CurrencyDao;
import com.crypto.entities.Currency;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The curremcy Dao implementation
 * <p/>
 * Created by Jan Wicherink on 30-3-2015.
 */

@Stateless
public class CurrencyDaoImpl implements CurrencyDao {

    private static final long serialVersionUID = 9080357979975346857L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    /**
     * Add a new currency to the database
     */
    public void persist(Currency currency) {

        em.persist(currency);
    }

    /**
     * Update an existing currency in the database
     *
     * @param currency the currency to be updated
     */
    public Currency update(Currency currency) {

        return em.merge(currency);
    }

    /**
     * Get a currency with a given code from the database
     *
     * @param code the code of the currency
     * @return the fetched currency with the given code.
     */
    public Currency get(final String code) {

        return em.find(Currency.class, code);
    }
}
