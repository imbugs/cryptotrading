package com.crypto.dao;

import com.crypto.com.crypto.dao.CurrencyDao;
import org.junit.Ignore;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;

/**
 * Created by Jan Wicherink on 31-3-2015.
 */

public class TestCurrencyDao {

    @Inject private CurrencyDao currencyDao;

    @Test
    public void testAddCurrency() {

      currencyDao.getCurrency("EUR");
    }
}
