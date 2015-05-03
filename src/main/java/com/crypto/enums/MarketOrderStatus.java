package com.crypto.enums;

/**
 * Created by Jan Wicherink on 3-5-15.
 */
public enum MarketOrderStatus {

    OPEN("OPEN"),
    RETRY("RETRY"),
    EXECUTED("EXECUTED"),
    EXECUTING("EXECUTING"),
    PARTY_EXECUTED("PARTY_EXECUTED"),
    CANCELLED("CANCELLED"),
    ERROR("ERROR");

    private String code;

    MarketOrderStatus(final String code) {
        this.code = code;
    }

    @Override
    public String toString () {
        return this.code;
    }
}
