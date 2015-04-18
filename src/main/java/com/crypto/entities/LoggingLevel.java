package com.crypto.entities;

/**
 * Created by Jan Wicheirnk on 18-4-15.
 */
public enum LoggingLevel {

    DEBUG("DEBUG"), INFO("INFO"), WARNING ("WARNING"), ERROR("ERROR");

    private String code;

    LoggingLevel(String code) {

        this.code = code;
    }

    @Override
    public String toString () {

        return this.code;
    }
}
