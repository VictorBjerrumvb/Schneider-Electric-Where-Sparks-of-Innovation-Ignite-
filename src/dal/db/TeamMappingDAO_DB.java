package dal.db;

import be.CreateTeamMapping;
import be.Team;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Team entities.
 */
public class TeamMappingDAO_DB {

    // DatabaseConnector instance for establishing database connections
    private final DatabaseConnector databaseConnector;

    /**
     * Constructs a new TeamDAO_DB object and initializes the DatabaseConnector.
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
     * Retrieves a Team from the database based on its ID.
     *
     * @return the retrieved Team object, or null if no Team is found with the given ID.
     * @throws DataAccessException if an error occurs while fetching the Team from the database.
     */
    public List<CreateTeamMapping> getTeamMappings() throws DataAccessException {
        List<CreateTeamMapping> allTeamMappings = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.PersonnelTeamMapping";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                // Map DB row to Song object
                int teamId = rs.getInt("TeamId");
                int personnelId = rs.getInt("PersonnelId");
                int mappingId = rs.getInt("MappingId");


                CreateTeamMapping teamMapping = new CreateTeamMapping (personnelId, teamId ,mappingId);
                allTeamMappings.add(teamMapping);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all TeamMappings data.", e);
        }
        return allTeamMappings;
    }

    public CreateTeamMapping deleteTeamMappingWithTeamNPersonnelId (CreateTeamMapping createTeamMapping) throws Exception {
        String sql = "DELETE FROM SparksExamSchneider.dbo.PersonnelTeamMapping WHERE TeamId = ? AND PersonnelId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,createTeamMapping.getTeamId());
            stmt.setInt(2,createTeamMapping.getPersonnelId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete TeamMapping", ex);
        }
        return createTeamMapping;
    }

    public CreateTeamMapping deleteTeamMappingWithMappingId (CreateTeamMapping createTeamMapping) throws Exception {
        String sql = "DELETE FROM SparksExamSchneider.dbo.PersonnelTeamMapping WHERE MappingId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,createTeamMapping.getMappingId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete TeamMapping", ex);
        }
        return createTeamMapping;
    }

    public CreateTeamMapping deleteTeamMappingWithPersonnelId (CreateTeamMapping createTeamMapping) throws Exception {
        String sql = "DELETE FROM SparksExamSchneider.dbo.PersonnelTeamMapping WHERE PersonnelId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,createTeamMapping.getPersonnelId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete TeamMapping", ex);
        }
        return createTeamMapping;
    }

    public CreateTeamMapping createTeamMapping(CreateTeamMapping createTeamMapping) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.PersonnelTeamMapping (TeamId, PersonnelId) VALUES (?,?);";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the prepared statement
            stmt.setInt(1, createTeamMapping.getTeamId());
            stmt.setInt(2, createTeamMapping.getPersonnelId());
            stmt.executeUpdate();

            // Retrieve auto-generated keys
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    createTeamMapping.setMappingId(rs.getInt(1)); // Set the generated ID to the Personnel object
                }
            }
            return createTeamMapping;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not add TeamMapping | Database Class", ex);
        }
    }
}
