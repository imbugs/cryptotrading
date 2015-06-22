package com.crypto.calculator;

import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.Signal;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;
import com.crypto.enums.MarketTrend;
import com.crypto.tradecondition.ConditionDispatcher;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Calculates a signal, wheter a bull or bull situation exists depending on the trade rules
 * <p/>
 * Created by Jan Wicherink on 9-6-15.
 */
@Stateless
public class SignalCalculator implements Calculator<Signal> {

    private SignalBulkDataHandler signalBulkDataHandler;

    private Boolean log = false;

    private Boolean alternate = false;

    private Trading trading;

    private Integer index = null;

    // The calculated signal by the Signal calculator
    private Signal calculatedValue = null;


    /**
     * Default constructor
     */
    public SignalCalculator() {

    }

    /**
     * Constructor
     *
     * @param signalBulkDataHandler the data provider for signal calculations.
     */
    public SignalCalculator(final SignalBulkDataHandler signalBulkDataHandler, Trading trading) {
        this.signalBulkDataHandler = signalBulkDataHandler;
        this.trading = trading;
    }

    /**
     * Checks whether if all of the conditions of a traderule are true.
     *
     * @param tradeRule the trade rule of the trade conditions
     * @return true when the conditions are met, false otherwise
     */
    private Boolean checkConditions(final TradeRule tradeRule) {

        List<TradeCondition> tradeConditions = signalBulkDataHandler.getAllTradeConditions(tradeRule);

        for (TradeCondition tradeCondition : tradeConditions) {

            final ConditionDispatcher dispatcher = new ConditionDispatcher(this.signalBulkDataHandler, this.index, this.trading, tradeCondition);

            // Evaluate the condition
            if (!dispatcher.evaluate()) {
                // No need to further evaluate, since one condition is already false
                return false;
            }
        }

        return true;
    }

    @Override
    public void calculate() {

        List<TradeRule> tradeRules;
        Signal previousSignal;

        if (this.alternate) {

            previousSignal = signalBulkDataHandler.getLastSignal(this.trading);

            if (previousSignal == null) {
                tradeRules = this.trading.getTradeRules();
            } else {
                if (previousSignal.getTradeSignal().equals(MarketTrend.BEAR)) {
                    // Only fetch BULL trade rules since a BULL signal must follow the last BEAR signal
                    tradeRules = (List<TradeRule>) this.trading.getTradeRules().stream().filter((tradeRule) -> tradeRule.getMarketTrend().equals(MarketTrend.BULL));
                } else {
                    // Only fetch BEAR trade rules singe a BEAR signal must follow the last BULL signal
                    tradeRules = (List<TradeRule>) this.trading.getTradeRules().stream().filter((tradeRule) -> tradeRule.getMarketTrend().equals(MarketTrend.BEAR));
                }
            }
        } else {
            tradeRules = this.trading.getTradeRules();
        }


        if (tradeRules != null && tradeRules.size() > 0) {

            // Check all conditions of all the active trade rules
            tradeRules.forEach((tradeRule) -> {

                final Boolean conditionsTrue = checkConditions(tradeRule);

                if (conditionsTrue && tradeRule.getMarketTrend().equals(MarketTrend.BULL)) {
                    this.calculatedValue = new Signal(MarketTrend.BULL, this.index, tradeRule, this.trading);
                }

                if (conditionsTrue && tradeRule.getMarketTrend().equals(MarketTrend.BEAR)) {
                    this.calculatedValue = new Signal(MarketTrend.BEAR, this.index, tradeRule, this.trading);
                }
            });
        }
    }

    @Override
    public Signal getCalculatedValue() {
        return calculatedValue;
    }

    @Override
    public Integer getIndex() {
        return this.index;
    }

    @Override
    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setTrading(Trading trading) {
        this.trading = trading;
    }
}
