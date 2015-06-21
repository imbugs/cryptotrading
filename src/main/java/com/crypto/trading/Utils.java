package com.crypto.trading;

import com.crypto.calculator.bulk.CryptoCoinHistoryTrendCalculator;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.Trading;

import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Utilities for the trading environment and usage for crypto trading
 * Created by Jan Wicherink on 27-5-15.
 */
@Stateful
public class Utils {

    @EJB
    private CryptoCoinHistoryTrendCalculator cryptoCoinHistoryTrendCalculator;

    @EJB
    private CryptocoinTrendDao cryptocoinTrendDao;

    /**
     * Calculate all trend lines for a given trade pair
     *
     * @param trading the trading.
     */
    public void calculateTrendLines(final Trading trading) {

        cryptoCoinHistoryTrendCalculator.init(trading);
        cryptoCoinHistoryTrendCalculator.recalculate();
    }
}

