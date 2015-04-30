package com.crypto.dao;

import com.crypto.entities.Logging;
import com.crypto.enums.LoggingLevel;
import com.crypto.entities.Trading;

import java.util.Date;
import java.util.List;

/**
 * Logging Dao
 *
 * Created by Jan Wicherink on 23-4-15.
 */
public interface LoggingDao {

    /**
     * Persist the logging to the database
     * @param logging the logging
     */
    public void persist(final Logging logging);

    /**
     * Get a logging record
     * @param index the index of the logging
     * @return the logging
     */
    public Logging get (final Integer index);

    /**
     * Get all logging of a trading
     * @param trading the trading
     * @return the list of loggings of a trading
     */
    public List<Logging> getAll(final Trading trading, final LoggingLevel level);

    /**
     * Delete all logging before a given date
     * @param beforeDate the date before which all logging is deleted
     */
    public void deleteBeforeDate (final Date beforeDate);

}
