package com.crypto.util;

import com.crypto.calculator.bulk.CryptoCoinHistoryTrendCalculator;
import com.crypto.entities.Trading;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.concurrent.ExecutionException;

/**
 * Utilities
 *
 * Created by Jan Wicherink on 27-5-15.
 */
@Stateless
public class Utils {

    @EJB
    private CryptoCoinHistoryTrendCalculator cryptoCoinHistoryTrendCalculator;

    /**
     * Calculate all trend lines for a given trade pair
     *
     * @param trading the util.
     */
    public void calculateTrendLines(final Trading trading) throws ExecutionException, InterruptedException {

        cryptoCoinHistoryTrendCalculator.init(trading);
        cryptoCoinHistoryTrendCalculator.recalculateInParallel();
    }
}

