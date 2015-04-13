package com.crypto.dao;

import com.crypto.entities.CryptocoinHistory;
import com.crypto.entities.TradePair;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

/**
 * Created by Jan Wicherink on 13-4-2015.
 */
@Stateful
public class CryptocoinHistoryDaoImpl implements CryptocoinHistoryDao{

    private static final long serialVersionUID = -2060460657264169995L;

    @PersistenceContext(unitName = "CryptoDS")
    EntityManager em;

    @Override
    public void persist(final CryptocoinHistory cryptocoinHistory) {
        final Date currentDate = new Date();

        cryptocoinHistory.setTimestamp(currentDate);

        em.persist(cryptocoinHistory);
    }

    @Override
    public Integer getStartIndex(TradePair tradePair) {

        final Query query = em.createQuery("SELECT MIN(c.indx) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Integer) query.getSingleResult();
    }

    @Override
    public Integer getLastIndex(TradePair tradePair) {
        final Query query = em.createQuery("SELECT MAX(c.indx) FROM CryptocoinHistory c WHERE c.tradePair.id = " + tradePair.getId());

        return (Integer) query.getSingleResult();
    }
}
