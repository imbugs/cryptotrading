package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jan Wicherink on 13-4-2015.
 */
@Stateful
public class CryptocoinHistoryDaoImpl implements CryptocoinHistoryDao{

    private static final long serialVersionUID = -2060460657264169995L;

    private static final Logger LOG = Logger.getLogger( CryptocoinHistoryDaoImpl.class.getName() );

    @PersistenceContext(unitName = "CryptoDS")
    EntityManager em;

    @Override
    public void persist(final CryptocoinHistory cryptocoinHistory) {

        Calendar calendar = Calendar.getInstance();
        final Timestamp currentDate = new Timestamp(calendar.getTime().getTime());

        LOG.info ("Persist cryptocoin history for tradepair : " + cryptocoinHistory.getTradePair().getId() + " with current time: " + currentDate.getTime());

        cryptocoinHistory.setTimestamp(currentDate);
        em.persist(cryptocoinHistory);
    }

    @Override
    public CryptocoinHistory getCryptoCoinHistoryByIndex(TradePair tradePair, Integer indx) {
        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() + " AND c.indx=" + indx);

        return (CryptocoinHistory) query.getSingleResult();
    }

    @Override
    public Integer getLastIndex(TradePair tradePair) {
        final Query query = em.createQuery("SELECT MAX(c.indx) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Integer) query.getSingleResult();
    }

    @Override
    public Integer getStartIndex(TradePair tradePair) {
        final Query query = em.createQuery("SELECT MIN(c.indx) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Integer) query.getSingleResult();
    }

    @Override
    public List<CryptocoinHistory> getAll(TradePair tradePair) {
        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return query.getResultList();
    }

    @Override
    public CryptocoinHistory getLast(TradePair tradePair) {
        final Query query = em.createQuery("SELECT c1 FROM CryptocoinHistory c1 WHERE c1.tradePair.id = " + tradePair.getId() +
                                            " AND c1.indx = (SELECT MAX (c2.indx) FROM CryptocoinHistory c2 WHERE c2.tradePair.id =" + tradePair.getId() + ")");
        return (CryptocoinHistory) query.getSingleResult();
    }

    @Override
    public CryptocoinHistory getCryptoCoinHistoryByTimestamp(TradePair tradePair, Timestamp timestamp) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT);
        final String timestampString = dateFormat.format (timestamp).toString();

        LOG.info("Get cryptocoin history for tradepair : " + tradePair.getId() + " with timestamp: " + timestampString);

        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() + " AND c.timestamp='" + timestampString + "'");

        return (CryptocoinHistory) query.getSingleResult();
    }
}
