package com.crypto.dao;

import com.crypto.entities.Currency;
import com.crypto.entities.Trading;
import com.crypto.entities.Withdrawal;

import java.io.Serializable;
import java.util.List;

/**
 * Withdrawal Dao
 *
 * Created by Jan Wicherink on 7-5-15.
 */
public interface WithdrawalDao extends Serializable{

    /**
     * Get a withdrawal
     * @param trading the trading of the withdrawal
     * @param currency the currency of the withdrawal
     * @return the withdrawal
     */
    public Withdrawal get(final Trading trading, final Currency currency);

    /**
     * Get all withdrawals
     * @return the withdrawals
     */
    public List<Withdrawal> getAll();


    /**
     * Get the witdrawals of a trading
     * @param trading the trading
     * @return
     */
    public List<Withdrawal> getWithdrawalsOfTrading(final Trading trading);

    /**
     * Remove a withdrawal
     * @param withdrawal the withdrawal to be removed.
     */
    public void remove(final Withdrawal withdrawal);


    /**
     * Update a withdrawal
     * @param withdrawal the withdrawal to be updated
     */
    public void update(final Withdrawal withdrawal);


    /**
     * Persist a withdrawal
     * @param withdrawal the withdrawal to be persisted.
     */
    public void persist (final Withdrawal withdrawal);
}
