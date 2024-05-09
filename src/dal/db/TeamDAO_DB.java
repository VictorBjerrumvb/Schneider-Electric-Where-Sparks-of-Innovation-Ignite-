package dal.db;

import be.Personnel;
import be.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Team entities.
 */
public class TeamDAO_DB {

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

    /**
     * Retrieves a Team from the database based on its ID.
     *
     * @param id the ID of the Team to retrieve.
     * @return the retrieved Team object, or null if no Team is found with the given ID.
     * @throws DataAccessException if an error occurs while fetching the Team from the database.
     */
    public Team getTeamById(int id) throws DataAccessException {
        String sql = "SELECT * FROM team WHERE id = ?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Extract data from ResultSet and construct Team object
                return extractTeamFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while fetching team by ID", e);
        }
        return null; // Return null if no Team object found with the given ID
    }

    public List<Team> getAllTeams() throws DataAccessException {
        List<Team> allTeams = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Team";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                // Map DB row to Song object
                int id = rs.getInt("TeamId");
                String name = rs.getString("TeamName");


                Team team = new Team (id,name);
                allTeams.add(team);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all Team data.", e);
        }
        return allTeams;
    }

    public Team deleteTeam(Team team) throws Exception {

        String sql="DELETE FROM SparksExamSchneider.dbo.Team WHERE TeamId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,team.getId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Team", ex);
        }
        return team;
    }

    public Team createTeam(Team team) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.Team (TeamName) VALUES (?);";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the prepared statement
            stmt.setString(1, team.getName());
            stmt.executeUpdate();

            // Retrieve auto-generated keys
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    team.setId(rs.getInt(1)); // Set the generated ID to the Personnel object
                }
            }
            return team;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not add Team", ex);
        }
    }

    /**
     * Extracts data from the ResultSet and constructs a Team object.
     *
     * @param rs the ResultSet containing the data.
     * @return the constructed Team object.
     * @throws SQLException if a SQL exception occurs while extracting data from the ResultSet.
     */
    private Team extractTeamFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        // You may need to extract more fields depending on your Team class

        // Construct and return the Team object
        return new Team(id, name); // Assuming you have a constructor for the Team class
    }
    public Team updateTeam(Team team) throws DataAccessException {
        String sql = "UPDATE SparksExamSchneider.dbo.Team SET TeamName = ? WHERE TeamId = ?;";
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
