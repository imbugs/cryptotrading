package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.Logging;
import com.crypto.entities.LoggingLevel;
import com.crypto.entities.Trading;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.crypto.entities.LoggingLevel.*;

/**
 * Created by Jan Wicherink on 18-4-15.
 */
@Stateful
public class LoggingDaoImpl implements LoggingDa0 {


    private static final long serialVersionUID = 6177946802223896971L;

    @PersistenceContext
    EntityManager em;

    @Override
    public void persist(Logging logging) {
        em.persist(logging);
    }

    @Override
    public List<Logging> getAll(Trading trading, LoggingLevel level) {

        String inClause = null;

        switch (level) {
            case DEBUG:
                inClause = "('DEBUG', 'INFO', 'WARNING', 'ERROR')";
                break;
            case INFO:
                inClause = "('INFO', 'WARNING', 'ERROR')";
                break;
            case WARNING:
                inClause = "('WARNING', 'ERROR')";
                break;
            case ERROR:
                inClause = "('ERROR')";
                break;
            default:
                inClause = "('INFO', 'WARNING', 'ERROR')";
                break;
        }

        final TypedQuery<Logging> query = (TypedQuery<Logging>) em.createQuery("SELECT l FROM Logging l WHERE l.trading= :trading AND l.level IN " + inClause);
        query.setParameter("trading", trading);

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