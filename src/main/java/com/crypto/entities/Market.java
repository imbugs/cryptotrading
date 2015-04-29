package com.crypto.entities;

/**
 * Signal type indicating of the market is in a bull or bear situation
 *
 * Created by Jan Wicherink on 29-4-2015.
 */
public enum Market {

    BEAR("BEAR"), BULL("BULL");

    final private String signalType;

    /**
     * Constructor
     *
     * @param signalType the signal type
     */
    Market(final String signalType) {

        this.signalType = signalType;
    }

    @Override
    public String toString () {
        return signalType;
    }
}
