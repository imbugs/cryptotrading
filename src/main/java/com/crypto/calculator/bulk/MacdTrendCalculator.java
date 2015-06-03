package com.crypto.calculator.bulk;

import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TrendValue;

import javax.ejb.EJB;
import java.util.logging.Logger;

/**
 * Macd bulk calculator
 *
 * Created by Jan Wicherink on 2-6-15.
 */
public class MacdTrendCalculator {

    private static final Logger LOG = Logger.getLogger(CryptoCoinHistoryTrendCalculator.class.getName());

    @EJB
    private CryptoCoinHistoryBulkDataHandler dataProvider;

    private BulkCalculator<CryptocoinHistory, TrendValue> maCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> emaCalculator;

}
