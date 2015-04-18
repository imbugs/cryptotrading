package com.crypto.dao;

import com.crypto.entities.FundHistory;
import com.crypto.entities.Trading;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jan Wicherink on 18-4-15.
 */
public interface FundHistoryDao extends Serializable{

    /**
     * Persist the fund history
     * @param fundHistory the fund history
     */
    public void persist (final FundHistory fundHistory);

    /**
     * Get all fund histories of a trading
     * @param trading the trading
     * @return the fund histories
     */
    public List<FundHistory> getAll(final Trading trading);
}
