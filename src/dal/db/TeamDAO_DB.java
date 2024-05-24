package dal.db;

import be.Team;
import dal.Interface.ITeam;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Team entities.
 */
public class TeamDAO_DB implements ITeam {

    // DatabaseConnector instance for establishing database connections
    private final DatabaseConnector databaseConnector;

    /**
     * Constructs a new TeamDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws IOException if an I/O error occurs when initializing the DatabaseConnector.
     */
    public TeamDAO_DB() throws IOException {
        try {
            databaseConnector = new DatabaseConnector();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public List<Team> getAllTeams() throws DataAccessException {
        List<Team> allTeams = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Team";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Map DB row to Team object
                int id = rs.getInt("TeamId");
                String name = rs.getString("TeamName");
                Team team = new Team(id, name);
                allTeams.add(team);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all Team data.", e);
        }
        return allTeams;
    }

    public Team deleteTeam(Team team) throws DataAccessException {
        String sql = "DELETE FROM SparksExamSchneider.dbo.Team WHERE TeamId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, team.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not delete Team", ex);
        }
        return team;
    }

    public Team createTeam(Team team) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.Team (TeamName) VALUES (?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the prepared statement
            stmt.setString(1, team.getName());
            stmt.executeUpdate();

            // Retrieve auto-generated keys
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    team.setId(rs.getInt(1)); // Set the generated ID to the Team object
                }
            }
            return team;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not add Team", ex);
        }
    }

    public Team updateTeam(Team team) throws DataAccessException {
        String sql = "UPDATE SparksExamSchneider.dbo.Team SET TeamName = ? WHERE TeamId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, team.getName());
            stmt.setInt(2, team.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not update Team", ex);
        }
        return team;
    }
}
