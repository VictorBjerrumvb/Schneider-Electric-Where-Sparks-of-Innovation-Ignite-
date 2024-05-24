package dal.db;

import be.Geography;
import be.Team;
import dal.Interface.IGeography;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Geography entities.
 */
public class GeographyDAO_DB implements IGeography {

    // DatabaseConnector instance for establishing database connections
    private final DatabaseConnector databaseConnector;

    /**
     * Constructs a new GeographyDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws DataAccessException if an error occurs when initializing the DatabaseConnector.
     */
    public GeographyDAO_DB() throws DataAccessException {
        try {
            databaseConnector = new DatabaseConnector();
        } catch (IOException ex) {
            throw new DataAccessException("Error initializing database connector", ex);
        }
    }

    public List<Geography> getAllGeography() throws DataAccessException {
        List<Geography> allGeography = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Geography";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Map DB row to Team object
                int id = rs.getInt("GeographyId");
                String name = rs.getString("CountryOrRegion");
                String gross = rs.getString("CountryGross");
                double margin = rs.getDouble("CountryMargin");

                Geography geography = new Geography(id, name, gross, margin);
                allGeography.add(geography);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all Geography data.", e);
        }
        return allGeography;
    }

    public Geography deleteGeography(Geography geography) throws DataAccessException {
        String sql = "DELETE FROM SparksExamSchneider.dbo.Geography WHERE GeographyId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, geography.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not delete Geography", ex);
        }
        return geography;
    }

    public Geography createGeography(Geography geography) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.Geography (CountryOrRegion) VALUES (?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the prepared statement
            stmt.setString(1, geography.getCountry());
            stmt.executeUpdate();

            // Retrieve auto-generated keys
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    geography.setId(rs.getInt(1)); // Set the generated ID to the Geography object
                }
            }
            return geography;
        } catch (SQLException ex) {
            ex.printStackTrace();  // Print the stack trace to standard error
            throw new DataAccessException("Could not add Geography", ex);
        }
    }



    public Geography updateGeographyName(Geography geography) throws DataAccessException {
        String sql = "UPDATE SparksExamSchneider.dbo.Geography SET CountryOrRegion = ? WHERE GeographyId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, geography.getCountry());
            stmt.setInt(2, geography.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Could not update Geography Name", ex);
        }
        return geography;
    }

    public Geography updateGeography(Geography geography) throws DataAccessException {
        String sql = "UPDATE SparksExamSchneider.dbo.Geography SET CountryOrRegion = ?, CountryGross = ?, CountryMargin = ? WHERE GeographyId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, geography.getCountry());
            stmt.setString(2, geography.getCountryGross());
            stmt.setDouble(3, geography.getCountryMargin());
            stmt.setInt(4, geography.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Could not update Geography", ex);
        }
        return geography;
    }

}
