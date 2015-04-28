package com.crypto.dao;

import com.crypto.entities.Trading;
import com.crypto.entities.TradingSite;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jan Wicherink on 25-4-15.
 */
public interface TradingDao extends Serializable {

    public Trading get(final Integer Id);

    public List<Trading> getAll ();

    public List<Trading> getActiveTradings();

    public List<Trading> getActiveTradingsOfTradingSite (final TradingSite tradingSite);

    public void update (final Trading trading);
}
