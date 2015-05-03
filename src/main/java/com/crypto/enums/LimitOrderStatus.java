package com.crypto.enums;

/**
 * Created by Jan Wicherink on 3-5-15.
 */
public enum LimitOrderStatus {

    OPEN("OPEN"),
    EXECUTED("EXECUTED"),
    NOFONDS("NOFUNDS"),
    STOPLOSS("STOPLOSS");

    private String code;

    LimitOrderStatus(final String code) {
        this.code = code;
    }

    @Override
    public String toString () {
        return this.code;
    }
}
