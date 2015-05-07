package com.crypto.enums;

/**
 * Evaluation value of a trade condition expression
 *
 * Created by Jan Wicherink on 6-5-15.
 */
public enum TradeConditionExpression {

    TRUE("TRUE"),
    FALSE("FALSE");

    private String code;

    TradeConditionExpression(String code) {
     this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }
}
