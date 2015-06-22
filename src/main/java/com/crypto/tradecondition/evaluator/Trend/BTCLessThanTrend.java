package com.crypto.tradecondition.evaluator.Trend;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TrendValue;
import com.crypto.enums.TradeConditionType;

import java.util.function.BiPredicate;

/**
 * Evaluates if a cryptocoin exchange rate is less than a trend value.
 * Created by Jan Wicherink  on 19-6-15.
 */
public class BTCLessThanTrend extends TrendEvaluator {

    /**
     * Constructor
     * @param signalBulkDataHandler the signal evaluator data provider
     */
    public BTCLessThanTrend(final SignalBulkDataHandler signalBulkDataHandler) {
        super(signalBulkDataHandler);

        final BiPredicate<CryptocoinHistory, TrendValue> expression = (c, t) -> c.getClose() < t.getValue();

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.BTC_LT_TREND;
    }
}


