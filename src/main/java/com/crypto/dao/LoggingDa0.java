package com.crypto.dao;

import com.crypto.entities.Logging;
import com.crypto.entities.LoggingLevel;
import com.crypto.entities.Trading;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jan Wicherink on 18-4-15.
 */
public interface LoggingDa0 extends Serializable {

    public void persist(final Logging logging);

    public List<Logging> getAll(final Trading trading, LoggingLevel level);
 }
