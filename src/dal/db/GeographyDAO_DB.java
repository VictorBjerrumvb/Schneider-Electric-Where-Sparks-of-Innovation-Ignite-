package dal.db;

import be.Geography;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for performing database operations related to Geography entities.
 */
public class GeographyDAO_DB {

    // DatabaseConnector instance for establishing database connections
    private final DatabaseConnector databaseConnector;

    /**
     * Constructs a new GeographyDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws IOException if an I/O error occurs when initializing the DatabaseConnector.
     */
    public GeographyDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * Retrieves a Geography object from the database based on its ID.
     *
     * @param id the ID of the Geography object to retrieve.
     * @return the Geography object with the specified ID, or null if not found.
     * @throws DataAccessException if an error occurs while fetching the geography from the database.
     */
    public Geography getGeographyById(int id) throws DataAccessException {
        String sql = "SELECT * FROM geography WHERE id = ?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set the ID parameter for the prepared statement
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Extract data from ResultSet and construct Geography object
                return extractGeographyFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while fetching geography by ID", e);
        }
        return null; // Return null if no Geography object found with the given ID
    }

    // Helper method to construct a Geography object from a ResultSet
    private Geography extractGeographyFromResultSet(ResultSet rs) throws SQLException {
        // Extract column values from the ResultSet
        int id = rs.getInt("id");
        String name = rs.getString("name");
        // You may need to extract more fields depending on your Geography class

        // Construct and return the Geography object
        return new Geography(id, name); // Assuming you have a constructor for Geography class
    }
}
