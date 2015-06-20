package com.crypto.tradecondition.evaluator.PercentageTrend;

import com.crypto.entities.CryptocoinHistory;

import javax.ejb.Stateful;
import java.util.function.BiPredicate;

/**
 * Evaluates if a cryptocoin exchange rate is less than a percentage of a trend value.
 * Created by Jan Wicherink  on 19-6-15.
 */
@Stateful
public class BTCLessThanPercentageTrend extends PercentageTrendEvaluator {

    /**
     * Constructor
     */
    public BTCLessThanPercentageTrend() {
        super();

        BiPredicate<CryptocoinHistory, Float> expression = (c, l) -> c.getClose() < l;

        this.setExpression(expression);
    }
}


