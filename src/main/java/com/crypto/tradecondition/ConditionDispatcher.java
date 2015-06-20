package com.crypto.tradecondition;

import com.crypto.entities.TradeCondition;
import com.crypto.entities.Trading;
import com.crypto.enums.TradeConditionType;
import com.crypto.tradecondition.evaluator.Macd.MacdNegative;
import com.crypto.tradecondition.evaluator.Macd.MacdPositive;
import com.crypto.tradecondition.evaluator.PercentageTrend.BTCGreaterThanPercentageTrend;
import com.crypto.tradecondition.evaluator.PercentageTrend.BTCLessThanPercentageTrend;
import com.crypto.tradecondition.evaluator.Trend.BTCGreaterThanTrend;
import com.crypto.tradecondition.evaluator.Trend.BTCLessThanTrend;
import com.crypto.tradecondition.evaluator.TrendChange.NegTrendChange;
import com.crypto.tradecondition.evaluator.TrendChange.PosTrendChange;

import javax.ejb.EJB;

import static com.crypto.enums.TradeConditionType.*;

/**
 * Dispatches a condition evaluation
 * <p/>
 * Created by jan on 10-6-15.
 */
public class ConditionDispatcher {

    @EJB
    private MacdPositive macdPositive;

    @EJB
    private MacdNegative macdNegative;

    @EJB
    private PosTrendChange posTrendChange;

    @EJB
    private NegTrendChange negTrendChange;

    @EJB
    private BTCGreaterThanTrend btcGreaterThanTrend;

    @EJB
    private BTCLessThanTrend btcLessThanTrend;

    @EJB
    private BTCGreaterThanPercentageTrend btcGreaterThanPercentageTrend;

    @EJB
    private BTCLessThanPercentageTrend btcLessThanPercentageTrend;

    private TradeCondition tradeCondition;

    private Integer index;

    private Trading trading;

    private Boolean log = false;

    public ConditionDispatcher(final Integer index, final Trading trading, final TradeCondition tradeCondition, final Boolean log) {

        this.index = index;
        this.trading = trading;
        this.tradeCondition = tradeCondition;
        this.log = log;
    }

    /**
     * Evaluate a trade condition
     *
     * @return true when the condition is true at a given index, false otherwise
     */
    public Boolean evaluate() throws RuntimeException {

        Boolean evaluation = false;

        macdPositive.setIndex(index);
        macdPositive.setTrading(trading);
        macdPositive.setTradeCondition(tradeCondition);

        switch (this.tradeCondition.getTradeConditionType()) {
            case MACD_POSITIVE:
                evaluation = macdPositive.evaluate();
                break;

            case MACD_NEGATIVE:
                evaluation = macdNegative.evaluate();
                break;

            case POS_TREND_CHANGE:
                evaluation = negTrendChange.evaluate();
                break;

            case NEG_TREND_CHANGE:
                evaluation = negTrendChange.evaluate();
                break;

            case BTC_GT_RATE:
                evaluation = btcGreaterThanTrend.evaluate();
                break;

            case BTC_LT_RATE:
                evaluation = btcLessThanTrend.evaluate();
                break;

            case BTC_GT_PERC_TREND:
                evaluation = btcGreaterThanPercentageTrend.evaluate();
                break;

            case BTC_LT_PERC_TREND:
                evaluation = btcLessThanPercentageTrend.evaluate();
                break;

            default:
                throw (new RuntimeException("Not implemented trade condition"));
        }
        return evaluation;
    }
}
