package com.crypto.dataprovider;

/**
 * Provides the index of a daeta item such as a cryptocoin history or trend value
 * Created by jan Wicherink on 13-5-15.
 */
public interface DataIndexProvider {

    /**
     * Get the index of a given value
     * @return the index of a value
     */
    public Integer getIndex();

}
