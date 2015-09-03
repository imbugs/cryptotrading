package com.crypto.dao.impl;

import com.crypto.dao.ChartTrendDao;
import com.crypto.entities.Chart;
import com.crypto.entities.ChartTrend;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of the trend chart dao
 *
 * Created by Jan Wicherink  on 3-9-15.
 */
@Stateless
public class ChartTrendDaoImpl implements ChartTrendDao {

    private static final long serialVersionUID = 8083040621332546918L;

    @PersistenceContext(unitName = "CryptoDS")
    private EntityManager em;

    @Override
    public List<ChartTrend> getChartTrends(Chart chart) {

        final TypedQuery <ChartTrend> query = (TypedQuery<ChartTrend>) em.createQuery("SELECT c FROM ChartTrend c WHERE c.chart=:chart");
        query.setParameter("chart", chart);

        return query.getResultList();
    }
}
