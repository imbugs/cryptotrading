package com.crypto.trading;

import com.crypto.calculator.bulk.CryptoCoinHistoryTrendCalculator;
import com.crypto.dao.CryptocoinTrendDao;
import com.crypto.entities.TradePair;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;

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
     * @param tradePair the trade pair
     */
    public void calculateTrendLines(final TradePair tradePair) {

        cryptoCoinHistoryTrendCalculator.init(tradePair);
        cryptoCoinHistoryTrendCalculator.recalculate();
    }
}

