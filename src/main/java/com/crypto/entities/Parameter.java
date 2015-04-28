package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jan Wicherink on 28-4-2015.
 */
@Entity
@Table(name="PARAMETERS")
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1162570804717107341L;

    @Id
    @Column(name="NAME")
    private String name;

    @Column(name="STRING", nullable=true, updatable=true, insertable = true)
    private String stringValue;

    @Column (name="VALUE", nullable=true, updatable=true, insertable = true )
    private Float floatValue;

    /**
     * Constructor
     * @param name  name of the parameter
     * @param stringValue string value of the parameter
     * @param floatValue floating point value of the parameter
     */
    public Parameter(String name, String stringValue, Float floatValue) {
        this.name = name;
        this.stringValue = stringValue;
        this.floatValue = floatValue;
    }

    /**
     * Default constructor
     */
    public Parameter() {
    }

    public String getName() {
        return name;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }
}
