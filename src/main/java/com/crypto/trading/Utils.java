package com.crypto.trading;

import com.crypto.calculator.bulk.CryptoCoinHistoryTrendCalculator;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.TradePair;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Utilities for the trading environment and usage for crypto trading
 * Created by Jan Wicherink on 27-5-15.
 */
@Stateless
public class Utils {

    @Inject
    private CryptoCoinHistoryTrendCalculator cryptoCoinHistoryTrendCalculator;

    @Inject
    private CryptocoinTrendDao cryptocoinTrendDao;

    /**
     * Calculate all trend lines for a given trade pair
     *
     * @param tradePair the trade pair
     */
    public void calculateTrendLines(final TradePair tradePair) {

        cryptoCoinHistoryTrendCalculator.init(tradePair);
        cryptoCoinHistoryTrendCalculator.calculate();
    }
}

