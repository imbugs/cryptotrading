package com.crypto.calculator.bulk;

/**
 * Maintains the progress of calculations.
 *
 * Created by Jan Wicherink on 24-8-15.
 */
public class CalculationProgress {

    private String name;

    private String status;

    private Integer totalCalculations;

    private Integer nrCalculationsCompleted;

    private Integer nrOfCalculationsToExecute;

    private Integer progress;

    private Boolean calculationStarted = false;

    private Boolean calculationFinished = false;

    /**
     * Constructor
     * @param name the name of the calculation
     */
    public CalculationProgress(String name) {
        this.name = name;
        this.nrCalculationsCompleted = 0;
        this.nrOfCalculationsToExecute = 0;
        this.status = "";
        this.progress = 0;
    }

    public void setTotalCalculations(final Integer totalCalculations) {
        this.totalCalculations = totalCalculations;
    }

    /**
     * Increment the number of completed calculations and calculate the progress.
     */
    public void incrementCalculation() {
        this.nrCalculationsCompleted = this.nrCalculationsCompleted + 1;

        if (this.calculationStarted) {

            if (nrOfCalculationsToExecute == 0) {
                this.progress = 100;
            }
            this.progress = (100 * this.nrCalculationsCompleted) / (this.totalCalculations * this.nrOfCalculationsToExecute);
        }
        else {
            if (nrOfCalculationsToExecute == 0) {
                this.progress = 100;
            }
            else {
                this.progress = 0;
            }
        }
    }

    public void setNrOfCalculationsToExecute(Integer nrOfCalculationsToExecute) {
        this.nrOfCalculationsToExecute = nrOfCalculationsToExecute;
    }


    public void calculationStart() {
        this.calculationStarted = true;
    }

    public void calculationFinish() {
        this.calculationFinished = true;

        if (this.nrOfCalculationsToExecute == 0) {
            this.progress = 100;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * The status of the calculation
     * @return the status
     */
    public String getStatus() {
        return status;
    }
}
