package com.crypto.enums;

/**
 * The Logging Level
 *
 * Created by Jan Wicherink on 18-4-15.
 */
public enum LoggingLevel {

    DEBUG("DEBUG"),
    INFO("INFO"),
    WARNING("WARNING"),
    ERROR("ERROR");

    final private String code;

    /**
     * Constructor
     * @param code the logging level code
     */
    LoggingLevel(final String code) {
        this.code = code;
    }

    @Override
    public String toString () {
        return this.code;
    }
}
