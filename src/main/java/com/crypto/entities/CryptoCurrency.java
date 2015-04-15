package com.crypto.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * The crypto coin currency
 *
 * Created by Jan Wicherink on 31-3-2015.
 */
@Entity
@DiscriminatorValue("CRC")
public class CryptoCurrency extends Currency implements Serializable {

    private static final long serialVersionUID = 584723921565885143L;

    public CryptoCurrency(String code, String description, String symbol) {
        super(code, description, symbol);
    }

    public CryptoCurrency() {
    }
}
