package com.crypto.dao;

import com.crypto.entities.Currency;

import java.io.Serializable;

/**
 * Created by Jan Wicherink on 25-3-2015.
 */
public interface CurrencyDao extends Serializable {

    /**
     * Add a currency to the database
     * @param currency the currency to be persisted
     */
    public void persist(Currency currency);

    /**
     * get a currency
     * @param code the code representing the currency
     * @return the currency
     */
    Currency get(String code);

    /**
     * Update an existing currency in the database
     * @param currency the currency to be updated.
     * @return the updated currency
     */
    public Currency update(Currency currency);
}
