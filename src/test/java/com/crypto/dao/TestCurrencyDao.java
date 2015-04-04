package com.crypto.dao;

import com.crypto.com.crypto.dao.CurrencyDaoImpl;
import org.junit.Test;

import javax.inject.Inject;

/**
 * Created by Jan Wicherink on 31-3-2015.
 */

public class TestCurrencyDao {

    @Inject private CurrencyDaoImpl currencyDao;

    @Test
    public void testAddCurrency() {

      currencyDao.getCurrency("EUR");
    }
}
