package com.crypto.dao.impl;

import com.crypto.dao.ChartDao;
import com.crypto.entities.Chart;
import com.crypto.enums.ChartType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 * Chart dao implementation
 * Created by Jan Wicherink on 3-9-15.
 */
@Stateless
public class ChartDaoImpl implements ChartDao {

    private static final long serialVersionUID = 6441267185771121762L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public Chart getChart(final ChartType type) {

      final TypedQuery<Chart> query = (TypedQuery<Chart>) em.createQuery("SELECT c FROM Chart c WHERE c.type=:type");
      query.setParameter("type", type);

      return query.getSingleResult();
    }
}
