package com.crypto.dao.impl;

import com.crypto.dao.TradeConditionTypeDao;
import com.crypto.entities.TradeConditionType;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Trade condition type Dao implementation
 * <p/>
 * Created by jan on 6-5-15.
 */
@Stateless
public class TradeConditionTypeDaoImpl implements TradeConditionTypeDao {

    private static final long serialVersionUID = -4092022727984997669L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public TradeConditionType getByName(String name) {

        return em.find(TradeConditionType.class, name);
    }
}
