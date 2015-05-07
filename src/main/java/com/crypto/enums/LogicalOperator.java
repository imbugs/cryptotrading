package com.crypto.enums;

/**
 * Logical operator of a trade condition
 *
 * Created by Jan Wicherink on 6-5-15.
 */
public enum LogicalOperator {

    AND("AND"),
    OR("OR");

    private String code;

    LogicalOperator(String code) {
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
