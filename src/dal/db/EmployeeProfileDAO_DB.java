package dal.db;

import be.EmployeeProfile;
import be.Geography;
import be.Team;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to EmployeeProfile entities.
 */
public class EmployeeProfileDAO_DB {

    // DatabaseConnector instance for establishing database connections
    private final DatabaseConnector databaseConnector;

    /**
     * Constructs a new EmployeeProfileDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws IOException if an I/O error occurs when initializing the DatabaseConnector.
     */
    public EmployeeProfileDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * Retrieves all employee profiles from the database.
     *
     * @return a list of EmployeeProfile objects representing all employee profiles in the database.
     * @throws DataAccessException if an error occurs while fetching employee profiles from the database.
     */
    public List<EmployeeProfile> getAllEmployeeProfiles() throws DataAccessException {
        List<EmployeeProfile> employeeProfiles = new ArrayList<>();
        try (Connection connection = databaseConnector.getConnection();
             Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM employee_profile";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                EmployeeProfile employeeProfile = extractEmployeeProfileFromResultSet(rs);
                employeeProfiles.add(employeeProfile);
            }
        } catch (SQLException | IOException e) {
            throw new DataAccessException("Error while fetching employee profiles", e);
        }
        return employeeProfiles;
    }

    /**
     * Inserts a new employee profile into the database.
     *
     * @param employeeProfile the EmployeeProfile object to be inserted.
     * @return the inserted EmployeeProfile object with its ID set.
     * @throws DataAccessException if an error occurs while creating the employee profile in the database.
     */
    public EmployeeProfile createEmployeeProfile(EmployeeProfile employeeProfile) throws DataAccessException {
        String sql = "INSERT INTO employee_profile (annual_salary, overhead_multiplier, fixed_annual_amount, " +
                "geography_id, team_id, annual_effective_working_hours, utilization_percentage, is_overhead) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameter values for the prepared statement
            pstmt.setDouble(1, employeeProfile.getAnnualSalary());
            pstmt.setDouble(2, employeeProfile.getOverheadMultiplier());
            pstmt.setDouble(3, employeeProfile.getFixedAnnualAmount());
            pstmt.setInt(4, employeeProfile.getGeography().getId());
            pstmt.setInt(5, employeeProfile.getTeam().getId());
            pstmt.setDouble(6, employeeProfile.getAnnualEffectiveWorkingHours());
            pstmt.setDouble(7, employeeProfile.getUtilizationPercentage());
            pstmt.setBoolean(8, employeeProfile.isOverhead());

            // Execute the update and retrieve the generated keys
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating employee profile failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employeeProfile.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating employee profile failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while creating employee profile", e);
        }
        return employeeProfile;
    }

    public void updateEmployeeProfile(EmployeeProfile employeeProfile) throws DataAccessException {
        String sql = "UPDATE employee_profile SET annual_salary=?, overhead_multiplier=?, fixed_annual_amount=?, " +
                "geography_id=?, team_id=?, annual_effective_working_hours=?, utilization_percentage=?, is_overhead=? " +
                "WHERE id=?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, employeeProfile.getAnnualSalary());
            pstmt.setDouble(2, employeeProfile.getOverheadMultiplier());
            pstmt.setDouble(3, employeeProfile.getFixedAnnualAmount());
            pstmt.setInt(4, employeeProfile.getGeography().getId());
            pstmt.setInt(5, employeeProfile.getTeam().getId());
            pstmt.setDouble(6, employeeProfile.getAnnualEffectiveWorkingHours());
            pstmt.setDouble(7, employeeProfile.getUtilizationPercentage());
            pstmt.setBoolean(8, employeeProfile.isOverhead());
            pstmt.setInt(9, employeeProfile.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating employee profile failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while updating employee profile", e);
        }
    }

    public void deleteEmployeeProfile(EmployeeProfile employeeProfile) throws DataAccessException {
        String sql = "DELETE FROM employee_profile WHERE id=?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeProfile.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deleting employee profile failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting employee profile", e);
        }
    }

    private EmployeeProfile extractEmployeeProfileFromResultSet(ResultSet rs) throws SQLException, IOException, DataAccessException {
        int id = rs.getInt("id");
        double annualSalary = rs.getDouble("annual_salary");
        double overheadMultiplier = rs.getDouble("overhead_multiplier");
        double fixedAnnualAmount = rs.getDouble("fixed_annual_amount");
        int geographyId = rs.getInt("geography_id");
        int teamId = rs.getInt("team_id");
        double annualEffectiveWorkingHours = rs.getDouble("annual_effective_working_hours");
        double utilizationPercentage = rs.getDouble("utilization_percentage");
        boolean isOverhead = rs.getBoolean("is_overhead");

        GeographyDAO_DB geographyDAO = new GeographyDAO_DB();
        Geography geography = geographyDAO.getGeographyById(geographyId);

        TeamDAO_DB teamDAO = new TeamDAO_DB();
        Team team = teamDAO.getTeamById(teamId);

        EmployeeProfile employeeProfile = new EmployeeProfile();
        employeeProfile.setId(id);
        employeeProfile.setAnnualSalary(annualSalary);
        employeeProfile.setOverheadMultiplier(overheadMultiplier);
        employeeProfile.setFixedAnnualAmount(fixedAnnualAmount);
        employeeProfile.setGeography(geography);
        employeeProfile.setTeam(team);
        employeeProfile.setAnnualEffectiveWorkingHours(annualEffectiveWorkingHours);
        employeeProfile.setUtilizationPercentage(utilizationPercentage);
        employeeProfile.setOverhead(isOverhead);

        return employeeProfile;
    }
}
