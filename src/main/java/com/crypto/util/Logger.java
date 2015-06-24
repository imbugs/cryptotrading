package com.crypto.util;

import com.crypto.dao.LoggingDao;
import com.crypto.entities.Logging;
import com.crypto.entities.Trading;
import com.crypto.enums.LoggingLevel;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Logger used to log information to the database.
 * <p/>
 * Created by Jan Wicherink on 24-6-15.
 */
@Stateless
public class Logger {

    @EJB
    private LoggingDao loggingDao;

    /**
     * Default constructor
     */
    public Logger() {

    }

    /**
     * Log a record.
     *
     * @param trading the trading.
     * @param level the currenct log level.
     * @param index the index of the trading.
     * @param message the log message.
     */
    public void LOG(final Trading trading, final LoggingLevel level, final Integer index, final String message) {

        final LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        long epoch = localDateTime.atZone(zoneId).toEpochSecond();

        Timestamp timestamp = new Timestamp(epoch);

        final Logging logging = new Logging(timestamp, trading, index, level, message);
    }
}
