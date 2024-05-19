package dal.db;

import be.CreateTeamMapping;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Team mappings.
 */
public class TeamMappingDAO_DB {

    // DatabaseConnector instance for establishing database connections
    private final DatabaseConnector databaseConnector;

    /**
     * Constructs a new TeamMappingDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws IOException if an I/O error occurs when initializing the DatabaseConnector.
     */
    public TeamMappingDAO_DB() throws IOException {
        try {
            databaseConnector = new DatabaseConnector();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * Retrieves all team mappings from the database.
     *
     * @return a list of all team mappings retrieved from the database.
     * @throws DataAccessException if an error occurs while fetching the data from the database.
     */
    public List<CreateTeamMapping> getAllTeamMappings() throws DataAccessException {
        List<CreateTeamMapping> allTeamMappings = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.PersonnelTeamMapping";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Map DB row to CreateTeamMapping object
                int teamId = rs.getInt("TeamId");
                int personnelId = rs.getInt("PersonnelId");
                int mappingId = rs.getInt("MappingId");
                CreateTeamMapping teamMapping = new CreateTeamMapping(personnelId, teamId, mappingId);
                allTeamMappings.add(teamMapping);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all TeamMappings data.", e);
        }
        return allTeamMappings;
    }

    /**
     * Deletes a team mapping by team ID and personnel ID.
     *
     * @param teamId the team ID of the team mapping to delete.
     * @param personnelId the personnel ID of the team mapping to delete.
     * @throws DataAccessException if an error occurs while deleting the team mapping.
     */
    public void deleteTeamMappingByTeamAndPersonnelId(int teamId, int personnelId) throws DataAccessException {
        String sql = "DELETE FROM SparksExamSchneider.dbo.PersonnelTeamMapping WHERE TeamId = ? AND PersonnelId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setInt(2, personnelId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not delete TeamMapping", ex);
        }
    }

    /**
     * Deletes a team mapping by mapping ID.
     *
     * @param mappingId the mapping ID of the team mapping to delete.
     * @throws DataAccessException if an error occurs while deleting the team mapping.
     */
    public void deleteTeamMappingByMappingId(int mappingId) throws DataAccessException {
        String sql = "DELETE FROM SparksExamSchneider.dbo.PersonnelTeamMapping WHERE MappingId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mappingId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not delete TeamMapping", ex);
        }
    }

    /**
     * Creates a new team mapping.
     *
     * @param createTeamMapping the team mapping object to create.
     * @return the created team mapping object with the generated mapping ID.
     * @throws DataAccessException if an error occurs while creating the team mapping.
     */
    public CreateTeamMapping createTeamMapping(CreateTeamMapping createTeamMapping) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.PersonnelTeamMapping (TeamId, PersonnelId) VALUES (?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, createTeamMapping.getTeamId());
            stmt.setInt(2, createTeamMapping.getPersonnelId());
            stmt.executeUpdate();

            // Retrieve auto-generated keys
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    createTeamMapping.setMappingId(rs.getInt(1)); // Set the generated ID to the CreateTeamMapping object
                }
            }
            return createTeamMapping;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not add TeamMapping | Database Class", ex);
        }
    }

    /**
     * Deletes team mappings by personnel ID.
     *
     * @param personnelId the personnel ID of the team mappings to delete.
     * @throws DataAccessException if an error occurs while deleting the team mappings.
     */
    public void deleteTeamMappingsByPersonnelId(int personnelId) throws DataAccessException {
        String sql = "DELETE FROM SparksExamSchneider.dbo.PersonnelTeamMapping WHERE PersonnelId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not delete TeamMappings by PersonnelId", ex);
        }
    }

    /**
     * Retrieves team mappings.
     *
     * @return a list of team mappings retrieved from the database.
     * @throws DataAccessException if an error occurs while fetching the data from the database.
     */
    public List<CreateTeamMapping> getTeamMappings() throws DataAccessException {
        List<CreateTeamMapping> teamMappings = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.PersonnelTeamMapping";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int teamId = rs.getInt("TeamId");
                int personnelId = rs.getInt("PersonnelId");
                int mappingId = rs.getInt("MappingId");
                CreateTeamMapping teamMapping = new CreateTeamMapping(personnelId, teamId, mappingId);
                teamMappings.add(teamMapping);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve TeamMappings", ex);
        }
        return teamMappings;
    }
}
