package com.crypto.entities;

import com.crypto.enums.ChartType;

import javax.persistence.*;

/**
 * Chart
 *
 * Created by Jan Wicherink on 3-9-15.
 */
@Entity
@Table(name="CHARTS")
public class Chart {

    @Id
    private Integer id;

    @Column(name="TITLE")
    private String title;

    @Column(name="TYPE")
    @Enumerated(EnumType.STRING)
    private ChartType type;

    /**
     * Default constructor
     */
    public Chart() {

    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ChartType getType() {
        return type;
    }
}
