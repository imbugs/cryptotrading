package com.crypto.dao;

import com.crypto.entities.Parameter;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Jan Wicherink on 28-4-2015.
 */
@Stateful
public class ParameterDaoImpl implements ParameterDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Parameter get(String name) {

        return em.find(Parameter.class, name);
    }

    @Override
    public void update(Parameter parameter) {

        em.merge(parameter);
    }
}
