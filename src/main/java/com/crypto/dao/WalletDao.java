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

    public Wallet get (final Trading trading);


}
