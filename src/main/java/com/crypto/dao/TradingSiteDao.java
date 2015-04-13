package com.crypto.dao;

import com.crypto.entities.TradingSite;

import java.io.Serializable;

/**
 * Trading site DAO
 *
 * Created by Jan Wicherink on 13-4-2015.
 */
public interface TradingSiteDao extends Serializable {

    public void persist (TradingSite tradingSite);

    public TradingSite get (String code);

    public void update (TradingSite tradingSite);
}
