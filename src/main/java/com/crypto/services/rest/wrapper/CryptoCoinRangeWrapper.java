package com.crypto.services.rest.wrapper;

/**
 * Crypto coin range mapper, contains the range of crypto coin history indexes
 * Created by Jan Wicherink on 15-9-2015.
 */
public class CryptoCoinRangeWrapper
{
    private Integer minIndex;

    private Integer maxIndex;

    /**
     * Constructor
     * @param minIndex the minimum index
     * @param maxIndex the maximum index
     */
    public CryptoCoinRangeWrapper(Integer minIndex, Integer maxIndex) {
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
    }

    public Integer getMinIndex() {
        return minIndex;
    }

    public Integer getMaxIndex() {
        return maxIndex;
    }
}
