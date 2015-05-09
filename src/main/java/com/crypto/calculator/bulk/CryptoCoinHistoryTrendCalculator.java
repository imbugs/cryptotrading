package com.crypto.calculator.bulk;

import com.crypto.calculator.MovingAverageCalculator;
import com.crypto.dataprovider.MovingAverageDataProvider;
import com.crypto.dataprovider.impl.CryptoCoinHistoryBulkDataProvider;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a crypto coin history
 *
 * Created by Jan Wicherink on 8-5-15.
 */
public class CryptoCoinHistoryTrendCalculator extends BulkCalculator<CryptocoinHistory> {


    /**
     * Constructor
     *
     * @param tradePair
     */
    public CryptoCoinHistoryTrendCalculator(TradePair tradePair) {

        super();

        CryptoCoinHistoryBulkDataProvider dataProvider = new CryptoCoinHistoryBulkDataProvider();
        MovingAverageCalculator calculator = new MovingAverageCalculator((MovingAverageDataProvider) dataProvider,0);

        setDataProvider(dataProvider);
        setCalculator(calculator);
        setTradePair(tradePair);
    }

    /**
     * Calculate the last cryptocoin history
     */
    public void calculateLast() {
        final CryptocoinHistory cryptocoinHistory = (CryptocoinHistory) getDataProvider().getLast();

        getCalculator().setIndex(cryptocoinHistory.getIndex());
        getCalculator().calculate();
    }
}
