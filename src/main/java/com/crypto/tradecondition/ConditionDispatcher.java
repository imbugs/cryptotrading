package com.crypto.tradecondition;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.Trading;
import com.crypto.tradecondition.evaluator.Evaluator;
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

/**
 * Dispatches a condition evaluation
 * <p/>
 * Created by jan on 10-6-15.
 */
public class ConditionDispatcher {

    private SignalBulkDataHandler signalBulkDataHandler;

    private TradeCondition tradeCondition;

    private Integer index;

    private Trading trading;

    /**
     * Constructor
     *
     * @param signalBulkDataHandler the signal bulk data provider.
     * @param index                 the index
     * @param trading               the trading
     * @param tradeCondition        the trade condition
     */
    public ConditionDispatcher(final SignalBulkDataHandler signalBulkDataHandler, final Integer index, final Trading trading, final TradeCondition tradeCondition) {

        this.signalBulkDataHandler = signalBulkDataHandler;
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

        Evaluator evaluator;

        switch (this.tradeCondition.getTradeConditionType()) {
            case MACD_POSITIVE:
                evaluator = new MacdPositive(signalBulkDataHandler);
                signalBulkDataHandler.setMacd(tradeCondition.getMacd());
                break;

            case MACD_NEGATIVE:
                evaluator = new MacdNegative(signalBulkDataHandler);
                signalBulkDataHandler.setMacd(tradeCondition.getMacd());
                break;

            case POS_TREND_CHANGE:
                evaluator = new PosTrendChange(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case NEG_TREND_CHANGE:
                evaluator = new NegTrendChange(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case BTC_GT_TREND:
                evaluator = new BTCGreaterThanTrend(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case BTC_LT_TREND:
                evaluator = new BTCLessThanTrend(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case BTC_GT_PERC_TREND:
                evaluator = new BTCGreaterThanPercentageTrend(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case BTC_LT_PERC_TREND:
                evaluator = new BTCLessThanPercentageTrend(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case POS_MACD_CHANGE:
                evaluator = new MacdPosChange(signalBulkDataHandler);
                signalBulkDataHandler.setMacd(tradeCondition.getMacd());
                break;

            case NEG_MACD_CHANGE:
                evaluator = new MacdNegChange(signalBulkDataHandler);
                signalBulkDataHandler.setMacd(tradeCondition.getMacd());
                break;

            case POS_TREND:
                evaluator = new PosTrend(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case NEG_TREND:
                evaluator = new NegTrend(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case MACD_DECREASE:
                evaluator = new MacdDecrease(signalBulkDataHandler);
                signalBulkDataHandler.setMacd(tradeCondition.getMacd());
                break;

            case MACD_INCREASE:
                evaluator = new MacdIncrease(signalBulkDataHandler);
                signalBulkDataHandler.setMacd(tradeCondition.getMacd());
                break;

            case TREND_INCREASE:
                evaluator = new TrendIncrease(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            case TREND_DECREASE:
                evaluator = new TrendDecrease(signalBulkDataHandler);
                signalBulkDataHandler.setTrend(tradeCondition.getTrend());
                break;

            default:
                throw (new RuntimeException("Not implemented trade condition"));
        }

        signalBulkDataHandler.setTrading(trading);

        evaluator.setTradeCondition(this.tradeCondition);
        evaluator.setIndex(this.index);

        return evaluator.evaluate();
    }
}
