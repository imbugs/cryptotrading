package com.crypto.dao;

import com.crypto.entities.Trading;
import com.crypto.entities.Wallet;

import java.io.Serializable;

/**
 * The wallet Dao
 *
 * Created by Jan Wicherink on 15-4-15.
 */
public interface WalletDao extends Serializable {

    /**
     * Persist the wallet
     * @param wallet the wallet to be persisted
     */
    public void persist (final Wallet wallet);

    /**
     * Get the wallet
     * @param trading the util of the wallet
     * @return the wallet
     */
    public Wallet get (final Trading trading);



}
