package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.Logging;
import com.crypto.enums.LoggingLevel;
import com.crypto.entities.Trading;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jan Wicherink on 18-4-15.
 */
@Stateful
public class LoggingDaoImpl implements LoggingDao {


    private static final long serialVersionUID = 6177946802223896971L;

    @PersistenceContext
    EntityManager em;

    private static final Logger LOG = Logger.getLogger(LoggingDaoImpl.class.getName());


    @Override
    public void persist(final Logging logging) {

        LOG.info ("Persisting logging id:" + logging.getIndex());
        LOG.info ("Level :" + logging.getLevel());
        LOG.info ("Trading id:" + logging.getTrading().getId());
        LOG.info ("Timestamp:" + logging.getTimestamp());

        em.persist(logging);
    }


    @Override
    public Logging get(Integer index) {
        final TypedQuery<Logging> query = (TypedQuery<Logging>) em.createQuery("SELECT l FROM Logging l WHERE l.index= :index");
        query.setParameter("index", index);

        return query.getSingleResult();
    }


    @Override
    public List<Logging> getAll(final Trading trading, final LoggingLevel level) {

        List<String> inClause = new ArrayList<String>();

        switch (level) {
            case DEBUG:
                inClause.add("DEBUG");
                inClause.add("INFO");
                inClause.add("WARNING");
                inClause.add("ERROR");
                break;
            case INFO:
                inClause.add("INFO");
                inClause.add("WARNING");
                inClause.add("ERROR");
                break;
            case WARNING:
                inClause.add("WARNING");
                inClause.add("ERROR");
                break;
            case ERROR:
                inClause.add("ERROR");
                break;
            default:
                inClause.add("INFO");
                inClause.add("WARNING");
                inClause.add("ERROR");
                break;
        }

        LOG.info("Trading id:" + trading.getId());
        LOG.info ("Level:" + level.toString());

        final TypedQuery<Logging> query = (TypedQuery<Logging>) em.createQuery("SELECT l FROM Logging l WHERE l.trading= :trading AND l.level IN (:inClause)");
        query.setParameter("trading", trading);
        query.setParameter("inClause", inClause);

        return query.getResultList();
    }

    @Override
    public void deleteBeforeDate(Date beforeDate) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
        final String timestampString = dateFormat.format(beforeDate);

        final Query query = em.createQuery("DELETE FROM Logging l WHERE l.timestamp < '" + timestampString + "'");

        query.executeUpdate();
    }
}