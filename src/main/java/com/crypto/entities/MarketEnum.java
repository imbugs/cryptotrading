package com.crypto.entities;

/**
 * Signal type indicating of the market is in a bull or bear situation
 *
 * Created by Jan Wicherink on 29-4-2015.
 */
public enum MarketEnum {

    BEAR("BEAR"), BULL("BULL");

    private String signalType;

    MarketEnum(final String signalType) {
        this.signalType = signalType;
    }

    public String toString () {
        return signalType;
    }
}
