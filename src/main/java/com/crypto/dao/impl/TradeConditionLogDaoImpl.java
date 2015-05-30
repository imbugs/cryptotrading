package com.crypto.dao.impl;

import com.crypto.dao.TradeConditionLogDao;
import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradeConditionLog;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Trade condition log Dao implementation
 * <p/>
 * Created by Jan Wicherink on 7-5-15.
 */
@Stateless
public class TradeConditionLogDaoImpl implements TradeConditionLogDao {

    private static final long serialVersionUID = -1516491277528526382L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public void persist(TradeConditionLog tradeConditionLog) {
        em.persist(tradeConditionLog);
    }

    @Override
    public List<TradeConditionLog> getAll() {
        final TypedQuery<TradeConditionLog> query = (TypedQuery<TradeConditionLog>) em.createQuery("SELECT t FROM TradeConditionLog t");

        return query.getResultList();
    }

    @Override
    public void deleteBeforeDate(Date beforeDate) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
        final String timestampString = dateFormat.format(beforeDate);

        final Query query = em.createQuery("DELETE FROM TradeConditionLog t WHERE t.timestamp < '" + timestampString + "'");

        query.executeUpdate();
    }
}

