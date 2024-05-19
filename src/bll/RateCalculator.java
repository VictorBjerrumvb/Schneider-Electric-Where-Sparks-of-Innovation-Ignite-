package bll;

import be.EmployeeProfile;
import dal.db.DataAccessException;
import dal.db.EmployeeProfileDAO_DB;
import dal.db.GeographyDAO_DB;
import dal.db.TeamDAO_DB;

import java.io.IOException;
import java.util.List;

/**
 * Calculates rates and performs related operations based on employee profiles.
 */
public class RateCalculator {
    private final EmployeeProfileDAO_DB employeeProfileDAO;
    private final GeographyDAO_DB geographyDAO;
    private final TeamDAO_DB teamDAO;

    /**
     * Constructs a RateCalculator and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public RateCalculator() throws IOException, DataAccessException {
        this.employeeProfileDAO = new EmployeeProfileDAO_DB();
        this.geographyDAO = new GeographyDAO_DB();
        this.teamDAO = new TeamDAO_DB();
    }

    /**
     * Calculates the daily rate based on the provided employee profile.
     *
     * @param employeeProfile The EmployeeProfile object for rate calculation.
     * @return The calculated daily rate.
     */
    public double calculateRate(EmployeeProfile employeeProfile) {
        double rate = 0.0;
        // Calculate rate based on the employee profile
        if (employeeProfile != null) {
            // Retrieve relevant data from the employee profile
            double annualSalary = employeeProfile.getAnnualSalary();
            double overheadMultiplier = employeeProfile.getOverheadMultiplier();
            double fixedAnnualAmount = employeeProfile.getFixedAnnualAmount();
            double annualEffectiveWorkingHours = employeeProfile.getAnnualEffectiveWorkingHours();
            double utilizationPercentage = employeeProfile.getUtilizationPercentage();

            // Calculate hourly rate
            double hourlyRate = (annualSalary / annualEffectiveWorkingHours) * (utilizationPercentage / 100);
            // Apply overhead
            hourlyRate = hourlyRate * (1 + (overheadMultiplier / 100));
            // Apply fixed amount
            hourlyRate += fixedAnnualAmount / annualEffectiveWorkingHours;

            // Calculate daily rate (assuming 8 working hours per day)
            double dailyRate = hourlyRate * 8;

            // Optionally, you can apply more logic here, such as grouping rates based on geography and team

            rate = dailyRate;
        }
        return rate;
    }

    /**
     * Applies markup multiplier to the given rate.
     *
     * @param rate   The rate to which markup is applied.
     * @param markup The markup percentage.
     * @return The rate after applying markup.
     */
    public double applyMarkupMultiplier(double rate, double markup) {
        // Apply markup multiplier to the rate
        return rate * (1 + (markup / 100));
    }

    /**
     * Applies GM multiplier to the given rate.
     *
     * @param rate The rate to which GM is applied.
     * @param gm   The GM percentage.
     * @return The rate after applying GM.
     */
    public double applyGMMultiplier(double rate, double gm) {
        // Apply GM multiplier to the rate
        return rate * (1 + (gm / 100));
    }

    /**
     * Groups rates based on geography and team.
     *
     * @return A list of employee profiles grouped by geography and team.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public List<EmployeeProfile> groupRatesByGeographyAndTeam() throws DataAccessException {
        try {
            // Retrieve employee profiles from the database
            List<EmployeeProfile> employeeProfiles = employeeProfileDAO.getAllEmployeeProfiles();

            // Group rates based on geography and team
            // Implement logic to group rates

            return employeeProfiles;
        } catch (DataAccessException e) {
            throw new DataAccessException("Error while grouping rates by geography and team", e);
        }
    }
}
