package be;

import javax.persistence.*;

/**
 * Represents the rates associated with a Personnel profile.
 */
@Entity
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Personnel_id")
    private Personnel personnel;

    @Column(nullable = false)
    private double hourlyRate;

    @Column(nullable = false)
    private double dailyRate;

    // Constructors

    /**
     * Constructs a rate object with default values.
     */
    public Rate() {
    }

    /**
     * Constructs a rate object with the specified Personnel profile, hourly rate, and daily rate.
     * @param personnel The Personnel profile associated with this rate.
     * @param hourlyRate The hourly rate.
     * @param dailyRate The daily rate.
     */
    public Rate(Personnel personnel, double hourlyRate, double dailyRate) {
        this.personnel = personnel;
        this.hourlyRate = hourlyRate;
        this.dailyRate = dailyRate;
    }

    // Getters and Setters

    /**
     * Retrieves the ID of the rate.
     * @return The rate ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the rate.
     * @param id The rate ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the Personnel profile associated with this rate.
     * @return The Personnel profile.
     */
    public Personnel getPersonnel() {
        return personnel;
    }

    /**
     * Sets the Personnel profile associated with this rate.
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
}
