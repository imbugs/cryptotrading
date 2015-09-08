package com.crypto.dao;

import com.crypto.entities.Signal;
import com.crypto.entities.TradeRule;
import com.crypto.entities.Trading;

import java.io.Serializable;
import java.util.List;

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
     * @param trading the util of the signal
     * @return the signal
     */
    public Signal get (final Integer indx, final TradeRule tradeRule, final Trading trading);

    /**
     * Get a signal
     * @param fromIndx the start index of the signal range
     * @param toIndex the end index of the signal range
     * @param trading the util of the signal
     * @return the signal
     */
    public List<Signal> getAll(final Integer fromIndx, final Integer toIndex, final Trading trading);

    /**
     * Get the last signal
     * @param trading the util of the signal
     * @return the last signal
     */
    public Signal getLast (final Trading trading);


    /**
     * Delete all signal data of util.
     * @param trading the util of which the signal data is to be removed.
     */
    public void truncateSignalData(final Trading trading);


    /**
     * Commit the current transactions.
     */
    public void commit();
}
