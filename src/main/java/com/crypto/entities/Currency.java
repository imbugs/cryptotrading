package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Currency represents a country's currency
 *
 * Created by Jan Wicherink on 25-3-2015.
 */
@Entity
@Table(name = "CURRENCIES")
@DiscriminatorValue("CUR")
public class Currency implements Serializable{

    private static final long serialVersionUID = 6916833154440481595L;

    @Id
    @Column (name="CODE")
    private String code;

    @Column (name="DESCRIPTION")
    private String description;

    @Column (name="SYMBOL")
    private String symbol;

    /**
     * Default constructor
     */
    public Currency() {
    }

    /**
     * @param code the code representing the currency
     * @param description description of the currency
     * @param symbol the symbol representing the currency
     */
    public Currency (String code, String description, String symbol) {
        this.code = code;
        this.description = description;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setCode(String code) {
        this.code = code;
    }
}