package com.crypto.tradecondition.evaluator.Trend;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.MacdValue;
import com.crypto.entities.TrendValue;

import javax.ejb.Stateful;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Evaluates if a cryptocoin exchange rate is greater than a trend value.
 * Created by Jan Wicherink  on 19-6-15.
 */
@Stateful
public class BTCGreaterThanTrend extends TrendEvaluator {

    /**
     * Constructor
     */
    public BTCGreaterThanTrend() {
        super();

        BiPredicate<CryptocoinHistory, TrendValue> expression = (c, t) -> c.getClose() > t.getValue();

        this.setExpression(expression);
    }
}


