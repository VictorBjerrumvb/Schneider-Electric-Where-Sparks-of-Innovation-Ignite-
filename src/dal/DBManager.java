package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    // JDBC URL, username, and password for your database
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    private Connection connection;

    public DBManager() {
        // Constructor to initialize the DBManager
    }

    // Method to establish a connection to the database
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // Method to close the database connection
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Other methods for executing queries, transactions, etc.
    // Example:
    // public ResultSet executeQuery(String sql) throws SQLException {
    //     Statement statement = connection.createStatement();
    //     return statement.executeQuery(sql);
    // }
}
