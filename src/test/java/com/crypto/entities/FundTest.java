package com.crypto.entities;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jan Wicherink on 16-4-15.
 */
public class FundTest {

    final Currency dollar = new Currency("DLR", "Dollar", "$");
    final CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin", "LTC");
    final TradingSite tradingSite = new TradingSite("BTCE", "BTC-e", "www.btc-e.com");

    final Integer id = new Integer(2);
    final TradePair tradePair = new TradePair(id, tradingSite, dollar, litecoin, 0.1F);


    @Test
    public void testAddCoins() {
        final Fund fund = new Fund(tradePair,100F, dollar);

        fund.addCoins(200F);

        assertEquals(300F, fund.getCoins());
    }

    @Test
    public void testSubstractCoins() {
        final Fund fund = new Fund(tradePair,300F, dollar);

        fund.subtractCoins(200F);

        assertEquals(100F, fund.getCoins());
    }
}
