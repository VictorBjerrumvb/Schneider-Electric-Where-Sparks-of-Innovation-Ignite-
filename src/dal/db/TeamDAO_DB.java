package dal.db;

import be.Team;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        databaseConnector = new DatabaseConnector();
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
}
