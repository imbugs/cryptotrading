package com.crypto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Trade condition type
 *
 * Created by Jan Wicherink on 6-5-15.
 */
@Entity (name="TRADE_CONDITION_TYPES")
public class TradeConditionType {

    @Id
    @Column (name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String decsription;

    @Column (name="MESSAGE_FORMAT")
    private String messageFormat;

    /**
     * Trade condition type
     * @param name the name of the trade condition type
     * @param description decription of the type
     * @param messageFormat message format specifier
     */
    public TradeConditionType(String name, String description, String messageFormat) {
        this.name = name;
        this.decsription = description;
        this.messageFormat = messageFormat;
    }

    /**
     * Default constructor
     */
    public TradeConditionType () {

    }

    public String getName() {
        return name;
    }

    public String getDecsription() {
        return decsription;
    }

    public String getMessageFormat() {
        return messageFormat;
    }
}
