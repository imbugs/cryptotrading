package com.crypto.util;

import com.crypto.entities.TradeCondition;
import com.crypto.enums.TradeConditionType;

import static com.crypto.enums.TradeConditionType.*;

/**
 * Trade condition formatter, formats a trade condition into a readable format
 * Created by Jan Wicherink on 11-9-2015.
 */
public class TradeConditionFormatter {

    private TradeCondition tradeCondition;

    private String readableTradeCondition;

    /**
     * Constructor
     * @param tradeCondition the trade condition
     */
    public TradeConditionFormatter(TradeCondition tradeCondition) {
        this.tradeCondition = tradeCondition;
    }

    /**
     * Formats the trade condition to a readable format
     */
    public void format () {

        // TODO: Implement all trade conditions
        switch (this.tradeCondition.getTradeConditionType()) {
            case BTC_LT_RATE:
                this.readableTradeCondition = String.format(BTC_LT_RATE.getMessageFormat(), tradeCondition.getRate(), tradeCondition.getPeriod());
                break;
            case BTC_GT_RATE:
                this.readableTradeCondition = String.format(BTC_GT_RATE.getMessageFormat(), tradeCondition.getRate(), tradeCondition.getPeriod());
                break;
            default :
                this.readableTradeCondition= "Not implemented yet";
                break;
        }
    }

    public String getReadableTradeCondition() {
        return readableTradeCondition;
    }
}
