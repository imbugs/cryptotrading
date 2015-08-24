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

    private Boolean calculationStarted = false;

    /**
     * Constructor
     * @param name the name of the calculation
     */
    public CalculationProgress(String name) {
        this.name = name;
        this.nrCalculationsCompleted = 0;
        this.nrOfCalculationsToExecute = 0;
        this.status = "";
    }

    public void setTotalCalculations(final Integer totalCalculations) {
        this.totalCalculations = totalCalculations;
    }

    public void incrementCalculation() {
        this.nrCalculationsCompleted = this.nrCalculationsCompleted + 1;
    }

    public void setNrOfCalculationsToExecute(Integer nrOfCalculationsToExecute) {
        this.nrOfCalculationsToExecute = nrOfCalculationsToExecute;
    }

    public void calculationStart() {
        this.calculationStarted = true;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * The progress of the calculation in percentages
     * @return the progress
     */
    public Integer getProgress() {

       if (calculationStarted) {

           if (nrOfCalculationsToExecute == 0) {
               return 100;
           }
           return (this.nrCalculationsCompleted / (this.totalCalculations * this.nrOfCalculationsToExecute)) * 100;
       }
       else {
           return 0;
       }
    }

    /**
     * The status of the calculation
     * @return the status
     */
    public String getStatus() {
        return status;
    }
}
