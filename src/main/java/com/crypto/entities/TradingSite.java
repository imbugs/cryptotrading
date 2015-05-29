package com.crypto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A crypto coin trading site
 *
 * Created by Jan Wicherink on 7-4-2015.
 */
@Entity
@Table(name="TRADING_SITES")
public class TradingSite implements Serializable {

    private static final long serialVersionUID = -2609436472397103930L;

    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "URL")
    private String url;

    /**
     * Default constructor
     */
    public TradingSite() {
    }

    public TradingSite(String code, String description, String url) {
        this.code = code;
        this.description = description;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
