package com.crypto.entities;

/**
 * Signal type indicating of the market is in a bull or bear situation
 *
 * Created by Jan Wicherink on 29-4-2015.
 */
public enum MarketType {

    BEAR("BEAR"), BULL("BULL");

    private String signalType;

    MarketType(final String signalType) {
        this.signalType = signalType;
    }

    public String toString () {
        return signalType;
    }
}
