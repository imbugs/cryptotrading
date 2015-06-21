package com.crypto.tradecondition.evaluator.PercentageTrend;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.enums.TradeConditionType;

import javax.ejb.Stateful;
import java.util.function.BiPredicate;

/**
 * Evaluates if a cryptocoin exchange rate is more than a percentage of a trend value.
 * Created by Jan Wicherink  on 19-6-15.
 */
@Stateful
public class BTCGreaterThanPercentageTrend extends PercentageTrendEvaluator {

    /**
     * Constructor
     */
    public BTCGreaterThanPercentageTrend() {
        super();

        BiPredicate<CryptocoinHistory, Float> expression = (c, l) -> c.getClose() > l;

        this.setExpression(expression);
    }

    @Override
    public TradeConditionType getImplementedTradeConditionType() {
        return TradeConditionType.BTC_GT_PERC_TREND;
    }
}

