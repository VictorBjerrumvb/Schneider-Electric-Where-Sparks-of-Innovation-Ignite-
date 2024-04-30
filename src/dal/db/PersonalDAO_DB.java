package dal.db;

import be.Personal;
import dal.Interface.IPersonal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Personal entities.
 */
public class PersonalDAO_DB implements IPersonal {

    // Logger for logging errors and messages
    private static final Logger logger = LoggerFactory.getLogger(PersonalDAO_DB.class);

    // DatabaseConnector instance for establishing database connections
    private DatabaseConnector databaseConnector;

    /**
     * Constructs a new PersonalDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws IOException if an I/O error occurs when initializing the DatabaseConnector.
     */
    public PersonalDAO_DB() throws IOException {
        try {
            databaseConnector = new DatabaseConnector();
        } catch (IOException ex) {
            logger.error("Error initializing database connector", ex);
            throw ex;
        }
    }

    /**
     * Retrieves all Personal records from the database.
     *
     * @return a list of all Personal records.
     * @throws DataAccessException if an error occurs while fetching Personal records from the database.
     */
    @Override
    public List<Personal> getAllPersonal() throws DataAccessException {
        List<Personal> allPersonal = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Personal";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                allPersonal.add(mapResultSetToPersonal(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all personal records", e);
            throw new DataAccessException("Error retrieving all personal data.", e);
        }
        return allPersonal;
    }

    /**
     * Inserts a new Personal record into the database.
     *
     * @param personal the Personal object to create.
     * @return the created Personal object.
     * @throws DataAccessException if an error occurs while creating the Personal record.
     */
    @Override
    public Personal createPersonal(Personal personal) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.Personal (PersonalName, PersonalPassword, PersonalRole, PersonalRoleId, PersonalSalary) VALUES (?, ?, ?, ?, ?);";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the prepared statement
            stmt.setString(1, personal.getUsername());
            stmt.setString(2, personal.getPassword());
            stmt.setString(3, personal.getRole());
            stmt.setInt(4, personal.getRoleId());
            stmt.setDouble(5, personal.getSalary());
            stmt.executeUpdate();

            // Retrieve auto-generated keys
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    personal.setId(rs.getInt(1)); // Set the generated ID to the Personal object
                }
            }
            return personal;
        } catch (SQLException ex) {
            logger.error("Could not add personal", ex);
            throw new DataAccessException("Could not add Personal", ex);
        }
    }

    @Override
    public Personal deletePersonal(Personal personal) throws DataAccessException {
        String deleteSql = "DELETE FROM SparksExamSchneider.dbo.Personal WHERE PersonalId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, personal.getId());
            deleteStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Could not delete Personal", ex);
            throw new DataAccessException("Could not delete Personal", ex);
        }
        return personal;
    }

    @Override
    public Personal updatePersonal(Personal personal) throws DataAccessException {
        String sql = "UPDATE SparksExamSchneider.dbo.Personal SET PersonalName = ?, PersonalPassword = ?, PersonalRole = ?, PersonalRoleId = ?, PersonalPicture = ?, PersonalSalary = ? WHERE PersonalId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personal.getUsername());
            stmt.setString(2, personal.getPassword());
            stmt.setString(3, personal.getRole());
            stmt.setInt(4, personal.getRoleId());
            stmt.setString(5, personal.getPicture());
            stmt.setDouble(6, personal.getSalary());
            stmt.setInt(7, personal.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Could not update Personal", ex);
            throw new DataAccessException("Could not update Personal", ex);
        }
        return personal;
    }

    private Personal mapResultSetToPersonal(ResultSet rs) throws SQLException {
        int id = rs.getInt("PersonalId");
        int roleId = rs.getInt("PersonalRoleId");
        String role = rs.getString("PersonalRole");
        double salary = rs.getDouble("PersonalSalary");
        String picture = rs.getString("PersonalPicture");
        String personalName = rs.getString("PersonalName");
        String personalPassword = rs.getString("PersonalPassword");
        return new Personal(id, personalName, personalPassword, roleId, role, salary, picture);
    }
    @Override
    public Personal validateUser(String userName, String password) throws DataAccessException {
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Personal WHERE PersonalName = ? AND PersonalPassword = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // User with provided credentials found
                    return mapResultSetToPersonal(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error validating user", ex);
            throw new DataAccessException("Error validating user", ex);
        }
        // User with provided credentials not found
        return null;
    }
}