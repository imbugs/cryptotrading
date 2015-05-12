package com.crypto.calculator.bulk;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import com.crypto.entities.TrendValue;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public class CryptoCoinHistoryTrendCalculator extends BulkCalculator<CryptocoinHistory, TrendValue> {

    /**
     * Constructor
     *
     * @param tradePair the tradePair of the cryptocoin data
     */
    public CryptoCoinHistoryTrendCalculator(TradePair tradePair) {

        super();

        CryptoCoinHistoryBulkDataHandler dataProvider = new CryptoCoinHistoryBulkDataHandler();
        MovingAverageCalculator calculator = new MovingAverageCalculator(dataProvider,0);

        setDataProvider(dataProvider);
        setCalculator(calculator);
        setTradePair(tradePair);
    }
}
