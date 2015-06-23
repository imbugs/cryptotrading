package com.crypto.dao;

import com.crypto.entities.Signal;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;

import java.io.Serializable;

/**
 * Signal Dao
 *
 * Created by Jan Wicherink on 29-4-15.
 */
public interface SignalDao extends Serializable{

    /**
     * Persist a signal
     * @param signal the signal to be persisted
     */
    public void persist (final Signal signal);

    /**
     * Get a signal
     * @param indx the index of the signal
     * @param tradeRule the trade rule of the signal
     * @param trading the trading of the signal
     * @return the signal
     */
    public Signal get (final Integer indx, final TradeRule tradeRule, final Trading trading);


    /**
     * Get the last signal
     * @param trading the trading of the signal
     * @return the last signal
     */
    public Signal getLast (final Trading trading);


    /**
     * Delete all signal data of trading.
     * @param trading the trading of which the signal data is to be removed.
     */
    public void truncateSignalData(final Trading trading);
}
