package com.crypto.tradecondition;

import com.crypto.entities.TradeCondition;
import com.crypto.entities.Trading;
import com.crypto.tradecondition.evaluator.ConditionEvaluator;
import com.crypto.tradecondition.evaluator.Macd.MacdNegative;
import com.crypto.tradecondition.evaluator.Macd.MacdPositive;
import com.crypto.tradecondition.evaluator.MacdChange.MacdNegChange;
import com.crypto.tradecondition.evaluator.MacdChange.MacdPosChange;
import com.crypto.tradecondition.evaluator.MacdGradualChange.MacdDecrease;
import com.crypto.tradecondition.evaluator.MacdGradualChange.MacdIncrease;
import com.crypto.tradecondition.evaluator.PercentageTrend.BTCGreaterThanPercentageTrend;
import com.crypto.tradecondition.evaluator.PercentageTrend.BTCLessThanPercentageTrend;
import com.crypto.tradecondition.evaluator.Trend.BTCGreaterThanTrend;
import com.crypto.tradecondition.evaluator.Trend.BTCLessThanTrend;
import com.crypto.tradecondition.evaluator.TrendChange.NegTrendChange;
import com.crypto.tradecondition.evaluator.TrendChange.PosTrendChange;
import com.crypto.tradecondition.evaluator.TrendDelta.NegTrend;
import com.crypto.tradecondition.evaluator.TrendDelta.PosTrend;
import com.crypto.tradecondition.evaluator.TrendGradualChange.TrendDecrease;
import com.crypto.tradecondition.evaluator.TrendGradualChange.TrendIncrease;

import javax.ejb.EJB;

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

    @EJB
    private MacdPosChange macdPosChange;

    @EJB
    private MacdNegChange macdNegChange;

    @EJB
    private PosTrend posTrend;

    @EJB
    private NegTrend negTrend;

    @EJB
    private MacdIncrease macdIncrease;

    @EJB
    private MacdDecrease macdDecrease;

    @EJB
    private TrendIncrease trendIncrease;

    @EJB
    private TrendDecrease trendDecrease;


    private TradeCondition tradeCondition;

    private Integer index;

    private Trading trading;

    public ConditionDispatcher(final Integer index, final Trading trading, final TradeCondition tradeCondition) {

        this.index = index;
        this.trading = trading;
        this.tradeCondition = tradeCondition;
    }

    /**
     * Evaluate a trade condition
     *
     * @return true when the condition is true at a given index, false otherwise
     */
    public Boolean evaluate() throws RuntimeException {

        ConditionEvaluator evaluator;

         switch (this.tradeCondition.getTradeConditionType()) {
            case MACD_POSITIVE:
                evaluator = macdPositive;
                break;

            case MACD_NEGATIVE:
                evaluator = macdNegative;
                break;

            case POS_TREND_CHANGE:
                evaluator= negTrendChange;
                break;

            case NEG_TREND_CHANGE:
                evaluator = negTrendChange;
                break;

            case BTC_GT_RATE:
                evaluator = btcGreaterThanTrend;
                break;

            case BTC_LT_RATE:
                evaluator = btcLessThanTrend;
                break;

            case BTC_GT_PERC_TREND:
                evaluator= btcGreaterThanPercentageTrend;
                break;

            case BTC_LT_PERC_TREND:
                evaluator = btcLessThanPercentageTrend;
                break;

            case POS_MACD_CHANGE:
                evaluator = macdPosChange;
                break;

            case NEG_MACD_CHANGE:
                evaluator = macdNegChange;
                break;

            case POS_TREND:
                evaluator = posTrend;
                break;

            case NEG_TREND:
                evaluator = negTrend;
                break;

            case MACD_DECREASE:
                evaluator = macdDecrease;
                break;

            case MACD_INCREASE:
                evaluator = macdIncrease;
                break;

            case TREND_INCREASE:
                evaluator = trendIncrease;
                break;

            case TREND_DECREASE:
                evaluator = trendDecrease;
                break;

            default:
                throw (new RuntimeException("Not implemented trade condition"));
        }

        return evaluator.evaluate();
    }
}
