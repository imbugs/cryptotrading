package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;
import org.jboss.arquillian.transaction.api.annotation.Transactional;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
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
    public CryptocoinHistory getCryptoCoinHistoryByIndex(final TradePair tradePair, final Integer indx) {
        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() + " AND c.indx=" + indx);

        return (CryptocoinHistory) query.getSingleResult();
    }

    @Override
    public Integer getLastIndex(final TradePair tradePair) {
        final Query query = em.createQuery("SELECT MAX(c.indx) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Integer) query.getSingleResult();
    }

    @Override
    public Integer getStartIndex(final TradePair tradePair) {
        final Query query = em.createQuery("SELECT MIN(c.indx) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Integer) query.getSingleResult();
    }

    @Override
    public List<CryptocoinHistory> getAll(final TradePair tradePair) {
        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return query.getResultList();
    }

    @Override
    public CryptocoinHistory getLast(final TradePair tradePair) {
        final Query query = em.createQuery("SELECT c1 FROM CryptocoinHistory c1 WHERE c1.tradePair.id = " + tradePair.getId() +
                                            " AND c1.indx = (SELECT MAX (c2.indx) FROM CryptocoinHistory c2 WHERE c2.tradePair.id =" + tradePair.getId() + ")");
        return (CryptocoinHistory) query.getSingleResult();
    }

    @Override
    public CryptocoinHistory getCryptoCoinHistoryByTimestamp(final TradePair tradePair, final Timestamp timestamp) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
        final String timestampString = dateFormat.format (timestamp).toString();

        LOG.info("Get cryptocoin history for tradepair : " + tradePair.getId() + " with timestamp: " + timestampString);

        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() + " AND c.timestamp='" + timestampString + "'");

        return (CryptocoinHistory) query.getSingleResult();
    }

    @Override
    public List<CryptocoinHistory> getCryptoCoinHistorySinceDate(final TradePair tradePair, final Date sinceDate) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE);
        final String timestampString = dateFormat.format(sinceDate);

        LOG.info("Get cryptocoin history for tradepair : " + tradePair.getId() + " with timestamp later than: " + timestampString);

        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() + " AND c.timestamp >='" + timestampString + "'");

        return (List<CryptocoinHistory>) query.getResultList();
    }

    @Override
    public List<CryptocoinHistory> getCryptoCoinHistorySinceIndex(final TradePair tradePair, final Integer index) {
        LOG.info("Get cryptocoin history for tradepair : " + tradePair.getId() + " with index greater than: " + index);

        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() + " AND c.indx  >= " + index);

        return (List<CryptocoinHistory>) query.getResultList();
    }

    @Override
    public List<CryptocoinHistory> getCryptoCoinHistoryRangeIndex(final TradePair tradePair, final Integer fromIndex, final Integer toIndex) {
        LOG.info("Get cryptocoin history for tradepair : " + tradePair.getId() + " with index greater than: " + fromIndex + " and smaller than : " + toIndex);

        final Query query = em.createQuery("SELECT c FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() + " AND c.indx  >= " + fromIndex + " AND c.indx <= " + toIndex);

        return (List<CryptocoinHistory>) query.getResultList();
    }

    @Override
    public Timestamp  getEarliestDate(final TradePair tradePair) {
        LOG.info("Get cryptocoin history for tradepair : " + tradePair.getId() + " with the earliest date");

        final Query query = em.createQuery("SELECT MIN(c.timestamp) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Timestamp) query.getSingleResult();
    }

    @Override
    public Timestamp  getLatestDate(final TradePair tradePair) {
        LOG.info("Get cryptocoin history for tradepair : " + tradePair.getId() + " with the latest date");

        final Query query = em.createQuery("SELECT MAX(c.timestamp) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Timestamp) query.getSingleResult();
    }

    @Override
    public Float getSumCryptoCoinRate(final Integer index, final Integer period, final TradePair tradePair) {
        LOG.info("Get total sum of cryptocoin currency rate of tradepair : " + tradePair.getId() + " starting with index " + index + " for a period of " + period);

        final Integer fromIndex = index - period;

        final Query query = em.createQuery("SELECT SUM(c.close) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId() +
        " AND c.indx <=" + index +  " AND c.indx > " + fromIndex);

        final Double result = (Double) query.getSingleResult();

        return new Float(result);
    }

    @Override
    public void deleteBeforeDate(Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(CryptocoinHistory.TIMESTAMP_FORMAT_DATE_AND_TIME);
        final String timestampString = dateFormat.format(date);

        LOG.info("Delete cryptocoin currency before date: "+ timestampString);

        final Query query = em.createQuery("DELETE FROM CryptocoinHistory c WHERE c.timestamp < '" + timestampString + "'");

        query.executeUpdate();
    }
}
