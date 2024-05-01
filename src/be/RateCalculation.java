package be;

import javax.persistence.*;

/**
 * Represents the calculated rates associated with a Personnel profile.
 */
@Entity
@Table(name = "rate_calculation")
public class RateCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rateId;

    @ManyToOne
    @JoinColumn(name = "Personnel_id")
    private Personnel personnel;

    @Column(nullable = false)
    private double hourlyRate;

    @Column(nullable = false)
    private double dailyRate;

    @Column(nullable = false)
    private double markUp;

    @Column(nullable = false)
    private double grossMargin;

    // Constructors

    /**
     * Constructs a rate calculation object with default values.
     */
    public RateCalculation() {
    }

    /**
     * Constructs a rate calculation object with the specified Personnel profile, hourly rate, daily rate,
     * markup, and gross margin.
     * @param personnel The Personnel profile associated with this rate calculation.
     * @param hourlyRate The hourly rate.
     * @param dailyRate The daily rate.
     * @param markUp The markup percentage.
     * @param grossMargin The gross margin percentage.
     */
    public RateCalculation(Personnel personnel, double hourlyRate, double dailyRate, double markUp, double grossMargin) {
        this.personnel = personnel;
        this.hourlyRate = hourlyRate;
        this.dailyRate = dailyRate;
        this.markUp = markUp;
        this.grossMargin = grossMargin;
    }

    // Getters and Setters

    /**
     * Retrieves the ID of the rate calculation.
     * @return The rate calculation ID.
     */
    public int getRateId() {
        return rateId;
    }

    /**
     * Sets the ID of the rate calculation.
     * @param rateId The rate calculation ID to set.
     */
    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    /**
     * Retrieves the Personnel profile associated with this rate calculation.
     * @return The Personnel profile.
     */
    public Personnel getPersonnel() {
        return personnel;
    }

    /**
     * Sets the Personnel profile associated with this rate calculation.
     * @param personnel The Personnel profile to set.
     */
    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    /**
     * Retrieves the hourly rate.
     * @return The hourly rate.
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Sets the hourly rate.
     * @param hourlyRate The hourly rate to set.
     */
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Retrieves the daily rate.
     * @return The daily rate.
     */
    public double getDailyRate() {
        return dailyRate;
    }

    /**
     * Sets the daily rate.
     * @param dailyRate The daily rate to set.
     */
    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    /**
     * Retrieves the markup percentage.
     * @return The markup percentage.
     */
    public double getMarkUp() {
        return markUp;
    }

    /**
     * Sets the markup percentage.
     * @param markUp The markup percentage to set.
     */
    public void setMarkUp(double markUp) {
        this.markUp = markUp;
    }

    /**
     * Retrieves the gross margin percentage.
     * @return The gross margin percentage.
     */
    public double getGrossMargin() {
        return grossMargin;
    }

    /**
     * Sets the gross margin percentage.
     * @param grossMargin The gross margin percentage to set.
     */
    public void setGrossMargin(double grossMargin) {
        this.grossMargin = grossMargin;
    }
}
