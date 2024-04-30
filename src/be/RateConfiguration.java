package be;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the configuration for rate calculation, including multipliers, hourly rate inclusion, and maximum hours per day.
 */
public class RateConfiguration {
    // Attributes
    private Map<String, Double> multipliers;
    private boolean includeHourlyRate;
    private int maxHoursPerDay;

    // Constructors

    /**
     * Constructs a rate configuration object with default values.
     */
    public RateConfiguration() {
        this.multipliers = new HashMap<>();
    }

    /**
     * Constructs a rate configuration object with the specified multipliers, hourly rate inclusion, and maximum hours per day.
     * @param multipliers A map containing multipliers for rate calculation.
     * @param includeHourlyRate A boolean indicating whether to include hourly rate calculation.
     * @param maxHoursPerDay The maximum number of hours per day for rate calculation.
     */
    public RateConfiguration(Map<String, Double> multipliers, boolean includeHourlyRate, int maxHoursPerDay) {
        this.multipliers = multipliers;
        this.includeHourlyRate = includeHourlyRate;
        this.maxHoursPerDay = maxHoursPerDay;
    }

    // Getters and Setters

    /**
     * Retrieves the multipliers for rate calculation.
     * @return A map containing multipliers.
     */
    public Map<String, Double> getMultipliers() {
        return multipliers;
    }

    /**
     * Sets the multipliers for rate calculation.
     * @param multipliers A map containing multipliers to set.
     */
    public void setMultipliers(Map<String, Double> multipliers) {
        this.multipliers = multipliers;
    }

    /**
     * Checks if hourly rate is included in rate calculation.
     * @return True if hourly rate is included, otherwise false.
     */
    public boolean isIncludeHourlyRate() {
        return includeHourlyRate;
    }

    /**
     * Sets whether to include hourly rate in rate calculation.
     * @param includeHourlyRate A boolean value indicating whether to include hourly rate.
     */
    public void setIncludeHourlyRate(boolean includeHourlyRate) {
        this.includeHourlyRate = includeHourlyRate;
    }

    /**
     * Retrieves the maximum hours per day for rate calculation.
     * @return The maximum hours per day.
     */
    public int getMaxHoursPerDay() {
        return maxHoursPerDay;
    }

    /**
     * Sets the maximum hours per day for rate calculation.
     * @param maxHoursPerDay The maximum hours per day to set.
     */
    public void setMaxHoursPerDay(int maxHoursPerDay) {
        this.maxHoursPerDay = maxHoursPerDay;
    }

    // Methods

    /**
     * Adds a multiplier to the rate configuration.
     * @param name The name of the multiplier.
     * @param value The value of the multiplier.
     */
    public void addMultiplier(String name, double value) {
        multipliers.put(name, value);
    }

    /**
     * Removes a multiplier from the rate configuration.
     * @param name The name of the multiplier to remove.
     */
    public void removeMultiplier(String name) {
        multipliers.remove(name);
    }

    /**
     * Returns a string representation of the rate configuration.
     * @return A string representation of the rate configuration.
     */
    @Override
    public String toString() {
        return "RateConfiguration{" +
                "multipliers=" + multipliers +
                ", includeHourlyRate=" + includeHourlyRate +
                ", maxHoursPerDay=" + maxHoursPerDay +
                '}';
    }
}
