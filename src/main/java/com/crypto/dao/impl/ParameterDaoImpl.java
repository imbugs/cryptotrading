package com.crypto.dao.impl;

import com.crypto.dao.ParameterDao;
import com.crypto.entities.Parameter;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Jan Wicherink on 28-4-2015.
 */
@Stateless
public class ParameterDaoImpl implements ParameterDao {

    @PersistenceContext(unitName = "CryptoDS")
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
