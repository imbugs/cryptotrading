package com.crypto.entities;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jan Wicherink on 15-4-15.
 */

public class WalletTest {

    private Trading trading;
    private Currency currency;
    private CryptoCurrency cryptoCurrency;
    private Wallet wallet;

    @Before
    public void setUp() {
        trading = new Trading();
        currency = new Currency("EUR", "Euro", "€");
        cryptoCurrency = new CryptoCurrency("BTC", "Bitcoin", "BTC");
        wallet = new Wallet(trading, 10F, 20F, currency, cryptoCurrency, 123.4F);
    }

    @Test
    public void testAddCoins() {

        wallet.addCoins(10F);

        assertEquals (20F, wallet.getCoins());
    }

    @Test
    public void testAddCryptoCoins() {

        wallet.addCryptoCoins(10F);

        assertEquals (30F, wallet.getCryptoCoins());
    }

    @Test
    public void testDetermineMaxTradingCryptoCoins() {

       Float maxTradingCoins =  wallet.determineMaxTradingCoins(50F);

       assertEquals(5F, maxTradingCoins);
    }

    @Test
    public void testDetermineMaxCryptoTradingCryptoCoins() {

        Float maxTradingCryptoCoins =  wallet.determineMaxTradingCryptoCoins(50F);

        assertEquals(10F, maxTradingCryptoCoins);
    }

    @Test
    public void testGetTotalValue() {

        assertEquals(2478F, wallet.getTotalValue());
    }
}
