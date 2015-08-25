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
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Cryptcoin history trend calculator, calculates the bulk on a cryptocoin history data
 * <p/>
 * Created by Jan Wicherink on 8-5-15.
 */
@Stateful
@SessionScoped
public class CryptoCoinHistoryTrendCalculator implements Serializable{

    private static final long serialVersionUID = -6265098499046631206L;

    private static final Logger LOG = Logger.getLogger(CryptoCoinHistoryTrendCalculator.class.getName());

    private static final String MA_CALCULATION = "MaCalculation";
    private static final String EMA_CALCULATION = "EmaCalculation";
    private static final String SMA_CALCULATION = "SmaCalculation";
    private static final String MACD_CALCULATION = "MacdCalculation";
    private static final String SIGNAL_CALCULATION = "SignalCalculation";

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

    private HashMap<String, CalculationProgress> calculationStatus;

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

        this.calculationStatus = new HashMap<>();
        this.calculationStatus.put(MA_CALCULATION, new CalculationProgress(MA_CALCULATION));
        this.calculationStatus.put(EMA_CALCULATION, new CalculationProgress(EMA_CALCULATION));
        this.calculationStatus.put(SMA_CALCULATION, new CalculationProgress(SMA_CALCULATION));
        this.calculationStatus.put(MACD_CALCULATION, new CalculationProgress(MACD_CALCULATION));
        this.calculationStatus.put(SIGNAL_CALCULATION, new CalculationProgress(SIGNAL_CALCULATION));

        this.maCalculator = new BulkCalculator<>(maCalculator, this.dataProvider, this.dataProvider, trading, calculationStatus.get(MA_CALCULATION));
        this.emaCalculator = new BulkCalculator<>(emaCalculator, this.dataProvider, this.dataProvider, trading, calculationStatus.get(EMA_CALCULATION));
        this.smaCalculator = new BulkCalculator<>(smaCalculator, this.dataProvider, this.dataProvider, trading, calculationStatus.get(SMA_CALCULATION));
        this.macdCalculator = new BulkCalculator<>(macdValueCalculator, this.macdDataProvider, this.macdDataProvider, trading, calculationStatus.get(MACD_CALCULATION));
        this.signalCalculator = new BulkCalculator<>(signalCalculator, this.signalBulkDataHandler, this.signalBulkDataHandler, trading, calculationStatus.get(SIGNAL_CALCULATION));

        LOG.info("Initialise for tradepair : " + trading.getTradePair().getId());
    }

    /**
     * Calculate all moving average trend data.
     */
    private void calculateMovingAverageTrends() {

        CalculationProgress progress = calculationStatus.get(MA_CALCULATION);
        progress.setTotalCalculations(this.dataProvider.getAllMovingAverageTrends().size());
        progress.calculationStart();

        try {

            this.dataProvider.getAllMovingAverageTrends().stream().forEach((trend) -> {

                LOG.info("Calculator for MA trend : " + trend.getName());
                progress.setStatus("Berekening MA trend " + trend.getName());

                dataProvider.setTrend(trend);
                ((TrendCalculator) maCalculator.getCalculator()).setTrend(trend);
                maCalculator.calculate();
            });

            dataProvider.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        progress.setStatus("Gereed berekening Moving Average Trends");
        progress.calculationFinish();
    }

    /**
     * Calculate all exponential moving average trend data.
     */
    private void calculateExponentialMovingAverageTrends() {

        CalculationProgress progress = calculationStatus.get(EMA_CALCULATION);
        progress.setTotalCalculations(this.dataProvider.getAllExponentialMovingAverageTrends().size());
        progress.calculationStart();

        try {
            this.dataProvider.getAllExponentialMovingAverageTrends().stream().forEach((trend) -> {

                LOG.info("Calculator for EMA trend : " + trend.getName());
                progress.setStatus("Berekening EMA trend " + trend.getName());

                dataProvider.setTrend(trend);
                ((TrendCalculator) emaCalculator.getCalculator()).setTrend(trend);
                emaCalculator.calculate();
            });

            dataProvider.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        progress.setStatus("Gereed berekening Exponentiele Moving Average Trends");
        progress.calculationFinish();
    }


    /**
     * Calculate all smoothing moving average trend data.
     */
    private void calculateSmoothingMovingAverageTrends() {

        CalculationProgress progress = calculationStatus.get(SMA_CALCULATION);
        progress.setTotalCalculations(this.dataProvider.getAllSmoothingMovingAverageTrends().size());
        progress.calculationStart();

        try {
            this.dataProvider.getAllSmoothingMovingAverageTrends().stream().forEach((trend) -> {

                LOG.info("Calculator for SMA trend : " + trend.getName());
                progress.setStatus("Berekening SMA trend " + trend.getName());

                dataProvider.setTrend(trend);
                ((TrendCalculator) smaCalculator.getCalculator()).setTrend(trend);
                smaCalculator.calculate();
            });

            dataProvider.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        progress.setStatus("Gereed berekening Smooting Moving Average Trends");
        progress.calculationFinish();
    }

    /**
     * Calculate all Macd trends.
     */
    private void calculateMacdTrends() {

        CalculationProgress progress = calculationStatus.get(MACD_CALCULATION);
        progress.setTotalCalculations(this.macdDataProvider.getAllMacds().size());
        progress.calculationStart();

        try {
            this.macdDataProvider.getAllMacds().stream().forEach((macd) -> {
                // Calculate for every availble macd the macd value
                LOG.info("Calculator for MACD  : " + macd.getId());
                progress.setStatus("Berekening MACD :" + macd.getName());

                this.macdDataProvider.setMacd(macd);
                ((MacdCalculator) macdCalculator.getCalculator()).setMacd(macd);
                macdCalculator.calculate();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        progress.setStatus("Gereed berekening Macd Trends");
        progress.calculationFinish();
    }

    /**
     * Calculate the signals.
     */
    private void calculateSignals() {

        CalculationProgress progress = calculationStatus.get(SIGNAL_CALCULATION);
        progress.setStatus("Start berekening van de signalen");
        progress.setTotalCalculations(1);
        progress.calculationStart();

        try {
            signalCalculator.calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progress.setStatus("Gereed berekening Signalen");
        progress.calculationFinish();
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

    public HashMap<String,CalculationProgress> getCalculationProgress() {

        // Update progress data
        calculationStatus.get(MA_CALCULATION).setProgress(maCalculator.getCalculationProgress().getProgress());
        calculationStatus.get(EMA_CALCULATION).setProgress(emaCalculator.getCalculationProgress().getProgress());
        calculationStatus.get(SMA_CALCULATION).setProgress(smaCalculator.getCalculationProgress().getProgress());
        calculationStatus.get(MACD_CALCULATION).setProgress(macdCalculator.getCalculationProgress().getProgress());
        calculationStatus.get(SIGNAL_CALCULATION).setProgress(signalCalculator.getCalculationProgress().getProgress());

        return this.calculationStatus;
    }
}
