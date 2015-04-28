package com.crypto.dao;

import com.crypto.entities.Parameter;

/**
 * Created by Jan Wicherink on 28-4-2015.
 */
public interface ParameterDao {

    public Parameter get(final String name);

    public void update (final Parameter parameter);
}
