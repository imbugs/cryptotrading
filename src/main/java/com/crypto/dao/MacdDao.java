package com.crypto.dao;

import com.crypto.entities.Macd;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jan Wicherink on 4-5-15.
 */
public interface MacdDao extends Serializable {

    /**
     * Get all Macd's
     * @return the available Macd's
     */
    public List<Macd> getAll();

    public Macd get(final Integer id);
}
