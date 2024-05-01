package dal.db;

import be.Personnel;
import dal.Interface.IPersonnel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Personnel entities.
 */
public class PersonnelDAO_DB implements IPersonnel {

    // Logger for logging errors and messages
    private static final Logger logger = LoggerFactory.getLogger(PersonnelDAO_DB.class);

    // DatabaseConnector instance for establishing database connections
    private DatabaseConnector databaseConnector;

    /**
     * Constructs a new PersonnelDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws IOException if an I/O error occurs when initializing the DatabaseConnector.
     */
    public PersonnelDAO_DB() throws IOException {
        try {
            databaseConnector = new DatabaseConnector();
        } catch (IOException ex) {
            logger.error("Error initializing database connector", ex);
            throw ex;
        }
    }

    /**
     * Retrieves all Personnel records from the database.
     *
     * @return a list of all Personnel records.
     * @throws DataAccessException if an error occurs while fetching Personnel records from the database.
     */
    @Override
    public List<Personnel> getAllPersonnel() throws DataAccessException {
        List<Personnel> allPersonnel = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Personnel";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                allPersonnel.add(mapResultSetToPersonnel(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all Personnel records", e);
            throw new DataAccessException("Error retrieving all Personnel data.", e);
        }
        return allPersonnel;
    }

    /**
     * Inserts a new Personnel record into the database.
     *
     * @param personnel the Personnel object to create.
     * @return the created Personnel object.
     * @throws DataAccessException if an error occurs while creating the Personnel record.
     */
    @Override
    public Personnel createPersonnel(Personnel personnel) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.Personnel (PersonnelName, PersonnelPassword, PersonnelRole, PersonnelRoleId, PersonnelSalary) VALUES (?, ?, ?, ?, ?);";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the prepared statement
            stmt.setString(1, personnel.getUsername());
            stmt.setString(2, personnel.getPassword());
            stmt.setString(3, personnel.getRole());
            stmt.setInt(4, personnel.getRoleId());
            stmt.setDouble(5, personnel.getSalary());
            stmt.executeUpdate();

            // Retrieve auto-generated keys
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    personnel.setId(rs.getInt(1)); // Set the generated ID to the Personnel object
                }
            }
            return personnel;
        } catch (SQLException ex) {
            logger.error("Could not add Personnel", ex);
            throw new DataAccessException("Could not add Personnel", ex);
        }
    }

    @Override
    public Personnel deletePersonnel(Personnel personnel) throws DataAccessException {
        String deleteSql = "DELETE FROM SparksExamSchneider.dbo.Personnel WHERE PersonnelId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, personnel.getId());
            deleteStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Could not delete Personnel", ex);
            throw new DataAccessException("Could not delete Personnel", ex);
        }
        return personnel;
    }

    @Override
    public Personnel updatePersonnel(Personnel personnel) throws DataAccessException {
        String sql = "UPDATE SparksExamSchneider.dbo.Personnel SET PersonnelName = ?, PersonnelPassword = ?, PersonnelRole = ?, PersonnelRoleId = ?, PersonnelPicture = ?, PersonnelSalary = ? WHERE PersonnelId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personnel.getUsername());
            stmt.setString(2, personnel.getPassword());
            stmt.setString(3, personnel.getRole());
            stmt.setInt(4, personnel.getRoleId());
            stmt.setString(5, personnel.getPicture());
            stmt.setDouble(6, personnel.getSalary());
            stmt.setInt(7, personnel.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Could not update Personnel", ex);
            throw new DataAccessException("Could not update Personnel", ex);
        }
        return personnel;
    }

    private Personnel mapResultSetToPersonnel(ResultSet rs) throws SQLException {
        int id = rs.getInt("PersonnelId");
        int roleId = rs.getInt("PersonnelRoleId");
        String role = rs.getString("PersonnelRole");
        double salary = rs.getDouble("PersonnelSalary");
        String picture = rs.getString("PersonnelPicture");
        String PersonnelName = rs.getString("PersonnelName");
        String PersonnelPassword = rs.getString("PersonnelPassword");
        return new Personnel(id, PersonnelName, PersonnelPassword, roleId, role, salary, picture);
    }
    @Override
    public Personnel validateUser(String userName, String password) throws DataAccessException {
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Personnel WHERE PersonnelName = ? AND PersonnelPassword = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // User with provided credentials found
                    return mapResultSetToPersonnel(rs);
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