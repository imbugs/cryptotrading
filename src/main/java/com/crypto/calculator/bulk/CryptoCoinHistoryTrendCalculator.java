package com.crypto.calculator.bulk;

import com.crypto.calculator.*;
import com.crypto.datahandler.impl.CryptoCoinHistoryBulkDataHandler;
import com.crypto.datahandler.impl.MacdBulkDataHandler;
import com.crypto.datahandler.impl.SignalBulkDataHandler;
import com.crypto.entities.*;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 * <p/>
 * Created by Jan Wicherink on 8-5-15.
 */
@Stateful
public class CryptoCoinHistoryTrendCalculator {

    private static final Logger LOG = Logger.getLogger(CryptoCoinHistoryTrendCalculator.class.getName());

    @Resource
    private ManagedExecutorService executor;

    @EJB
    private CryptoCoinHistoryBulkDataHandler dataProvider;

    @EJB
    private MacdBulkDataHandler macdDataProvider;

    @EJB
    private SignalBulkDataHandler signalBulkDataHandler;

    private BulkCalculator<CryptocoinHistory, TrendValue> maCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> emaCalculator;

    private BulkCalculator<CryptocoinHistory, TrendValue> smaCalculator;

    private BulkCalculator<CryptocoinHistory, MacdValue> macdCalculator;

    private BulkCalculator<CryptocoinHistory, Signal> signalCalculator;

    /**
     * Default constructor
     */
    public CryptoCoinHistoryTrendCalculator() {

    }

    /**
     * Initialises the trend calculator.
     *
     * @param trading the util
     */
    public void init(final Trading trading) {
        this.dataProvider.setTrading(trading);
        this.macdDataProvider.setTrading(trading);

        final Integer startIndex = dataProvider.getStartIndex();

        final MovingAverageCalculator maCalculator = new MovingAverageCalculator(this.dataProvider);
        final ExponentialMovingAverageCalculator emaCalculator = new ExponentialMovingAverageCalculator(this.dataProvider, startIndex);
        final SmoothingMovingAverageCalculator smaCalculator = new SmoothingMovingAverageCalculator(this.dataProvider);
        final MacdValueCalculator macdValueCalculator = new MacdValueCalculator(this.macdDataProvider);
        final SignalCalculator signalCalculator = new SignalCalculator(this.signalBulkDataHandler, trading);

        this.maCalculator = new BulkCalculator<>(maCalculator, this.dataProvider, this.dataProvider, trading);
        this.emaCalculator = new BulkCalculator<>(emaCalculator, this.dataProvider, this.dataProvider, trading);
        this.smaCalculator = new BulkCalculator<>(smaCalculator, this.dataProvider, this.dataProvider, trading);
        this.macdCalculator = new BulkCalculator<>(macdValueCalculator, this.macdDataProvider, this.macdDataProvider, trading);
        this.signalCalculator = new BulkCalculator<>(signalCalculator, this.signalBulkDataHandler, this.signalBulkDataHandler, trading);


        LOG.info("Initialise for tradepair : " + trading.getTradePair().getId());
    }

    /**
     * Calculate all moving average trend data.
     */
    private void calculateMovingAverageTrends() {

        try {
            this.dataProvider.getAllMovingAverageTrends().stream().forEach((trend) -> {

                LOG.info("Calculator for MA trend : " + trend.getName());
                dataProvider.setTrend(trend);
                ((TrendCalculator) maCalculator.getCalculator()).setTrend(trend);
                maCalculator.calculate();
            });

            dataProvider.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate all exponential moving average trend data.
     */
    private void calculateExponentialMovingAverageTrends() {

        try {
            this.dataProvider.getAllExponentialMovingAverageTrends().stream().forEach((trend) -> {

                LOG.info("Calculator for EMA trend : " + trend.getName());

                dataProvider.setTrend(trend);
                ((TrendCalculator) emaCalculator.getCalculator()).setTrend(trend);
                emaCalculator.calculate();
            });

            dataProvider.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Calculate all smoothing moving average trend data.
     */
    private void calculateSmoothingMovingAverageTrends() {

        try {
            this.dataProvider.getAllSmoothingMovingAverageTrends().stream().forEach((trend) -> {

                LOG.info("Calculator for SMA trend : " + trend.getName());

                dataProvider.setTrend(trend);
                ((TrendCalculator) smaCalculator.getCalculator()).setTrend(trend);
                smaCalculator.calculate();
            });

            dataProvider.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Calculate all Macd trends.
     */
    private void calculateMacdTrends() {

        try {
            this.macdDataProvider.getAllMacds().stream().forEach((macd) -> {
                // Calculate for every availble macd the macd value
                LOG.info("Calculator for MACD  : " + macd.getId());

                this.macdDataProvider.setMacd(macd);
                ((MacdCalculator) macdCalculator.getCalculator()).setMacd(macd);
                macdCalculator.calculate();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate the signals.
     */
    private void calculateSignals() {

        try {
            signalCalculator.calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Calculate all the trend values for all available trends: MA, EMA and SMA trends, next calculate Macd's and
     * finally calculate the signals. Calculation performed in parallel.
     */
    public void recalculateInParallel() throws ExecutionException, InterruptedException {

        // Truncate all data before recalculating
        this.dataProvider.truncateTrendValueData();
        this.signalBulkDataHandler.truncateSignalData();

        // Run in parallel, ma calculation, ema calculation
        final CompletableFuture maCalculation = CompletableFuture.runAsync(this::calculateMovingAverageTrends, this.executor);

        final CompletableFuture emaCalculation = CompletableFuture.runAsync(this::calculateExponentialMovingAverageTrends, this.executor);

        // Start Sma calculation after completion of ma calucation, ema calculation
        final CompletableFuture smaCalculation = CompletableFuture.allOf(maCalculation, emaCalculation)
                .thenRunAsync(this::calculateSmoothingMovingAverageTrends);

        // Start Macd calculation after completion of ma calucation, ema calculation
        final CompletableFuture macdCalculation = CompletableFuture.allOf(maCalculation, emaCalculation)
                .thenRunAsync(this::calculateMacdTrends);

        // Start signal calculation after completion of macd calculation and sma calculation.
        CompletableFuture.allOf(smaCalculation, macdCalculation)
                .thenRunAsync(this::calculateSignals, this.executor);
    }

    /**
     * Calculate all the trend values for all available trends: MA, EMA and SMA trends, next calculate Macd's and
     * finally calculate the signals. Calculation performed sequentially..
     */
    public void recalculate() {

        // Truncate all data before recalculating
        this.dataProvider.truncateTrendValueData();
        this.signalBulkDataHandler.truncateSignalData();

        // Run sequentially
        calculateMovingAverageTrends();
        calculateExponentialMovingAverageTrends();
        calculateSmoothingMovingAverageTrends();
        calculateMacdTrends();
        calculateSignals();
    }
}
