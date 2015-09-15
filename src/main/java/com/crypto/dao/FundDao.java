package com.crypto.dao;

import com.crypto.entities.Currency;
import com.crypto.entities.Fund;
import com.crypto.entities.TradePair;

import java.io.Serializable;

/**
 * Created by Jan Wicherink on 16-4-15.
 */
public interface FundDao extends Serializable {

    /**
     * Persist a fund
     * @param fund the fund to be persisted.
     */
    public void perist (final Fund fund);

    /**
     * Get a fund of a given trade pair and currency
     * @param tradepair the tradepair
     * @param currency the currency
     * @return the fund
     */
    public Fund get(final TradePair tradepair, Currency currency);

    /**
     * Remove all fund data of a trade pair.
     * @param tradePair the trade pair.
     */
    public void deleteAll(final TradePair tradePair);
}
