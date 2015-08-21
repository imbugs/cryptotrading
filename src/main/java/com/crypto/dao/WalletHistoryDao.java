package com.crypto.dao;

import com.crypto.entities.Trading;
import com.crypto.entities.WalletHistory;

import java.io.Serializable;
import java.util.List;

/**
 * Wallet history Dao
 *
 * Created by JanWal Wicherink on 7-5-15.
 */
public interface WalletHistoryDao extends Serializable{

    /**
     * Get all wallet histories
     * @param trading the util
     *
     * @return the wallet histories
     */
    public List<WalletHistory> getAll(final Trading trading);


    /**
     * Get last wallet history of a util
     *
     * @param trading the util
     * @return last wallet history of the util
     */
    public WalletHistory getLast(final Trading trading);


    /**
     * Persist the wallet history
     *
     * @param walletHistory the wallet history to be persisted
     */
    public void persist(final WalletHistory walletHistory);
}
