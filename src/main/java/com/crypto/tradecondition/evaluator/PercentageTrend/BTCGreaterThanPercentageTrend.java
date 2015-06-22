package com.crypto.tradecondition.evaluator.PercentageTrend;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.enums.TradeConditionType;

import java.util.function.BiPredicate;

/**
 * Evaluates if a cryptocoin exchange rate is more than a percentage of a trend value.
 * Created by Jan Wicherink  on 19-6-15.
 */
public class BTCGreaterThanPercentageTrend extends PercentageTrendEvaluator {

    /**
     * Constructor
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public BTCGreaterThanPercentageTrend(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        final BiPredicate<CryptocoinHistory, Float> expression = (c, l) -> c.getClose() > l;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.BTC_GT_PERC_TREND;
    }
}


