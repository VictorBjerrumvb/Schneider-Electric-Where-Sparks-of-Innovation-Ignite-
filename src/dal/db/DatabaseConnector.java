package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A utility class for establishing connections to a SQL Server database.
 */
public class DatabaseConnector {

    // Path to the database configuration file
    private static final String PROP_FILE = "config/config.settings";

    // SQLServerDataSource instance for managing database connections
    private SQLServerDataSource dataSource;

    /**
     * Constructs a new DatabaseConnector and initializes the SQLServerDataSource with database properties.
     *
     * @throws IOException if an I/O error occurs when reading the database properties file.
     */
    public DatabaseConnector() throws IOException {
        try {
            // Load database properties from the configuration file
            Properties databaseProperties = new Properties();
            databaseProperties.load(new FileInputStream(new File(PROP_FILE)));

            // Initialize SQLServerDataSource with database properties
            dataSource = new SQLServerDataSource();
            dataSource.setServerName(databaseProperties.getProperty("Server"));
            dataSource.setDatabaseName(databaseProperties.getProperty("Database"));
            dataSource.setUser(databaseProperties.getProperty("User"));
            dataSource.setPassword(databaseProperties.getProperty("Password"));
            dataSource.setPortNumber(1433); // Default SQL Server port
            dataSource.setTrustServerCertificate(true); // Trust the server certificate for SSL
        } catch (IOException ex) {
            // Log or handle the exception appropriately
            throw ex;
        }
    }

    /**
     * Gets a connection to the SQL Server database.
     *
     * @return a Connection object representing a connection to the database.
     * @throws SQLServerException if a database access error occurs.
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    /**
     * A simple main method to test the DatabaseConnector class by establishing a connection and checking if it's open.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Create a new instance of DatabaseConnector
            DatabaseConnector databaseConnector = new DatabaseConnector();

            // Try-with-resources to automatically close the connection after use
            try (Connection connection = databaseConnector.getConnection()) {
                // Print whether the connection is open or closed
                System.out.println("Is the connection open? " + !connection.isClosed());
            }
        } catch (IOException | SQLException ex) {
            // Handle exceptions appropriately
            ex.printStackTrace();
        }
    }
}
