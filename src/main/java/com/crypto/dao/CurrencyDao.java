package com.crypto.dao;

import com.crypto.entities.Currency;

/**
 * Created by Jan Wicherink on 25-3-2015.
 */
public interface CurrencyDao {

    /**
     * get a currency
     * @param code the code representing the currency
     * @return the currency
     */
    Currency getCurrency (String code);

    /**
     * Add a currency to the database
     * @param code the code representing the currency
     * @param description the description of the currency
     * @param symbol the symbol representing the currency
     * @return the persisted currency
     */
    public Currency addCurrency (final String code, final String description, final String symbol);

    /**
     * Update an existing currency in the database
     * @param currency the currency to be updated.
     * @return the updated currency
     */
    public Currency updateCurrency (Currency currency);
}
