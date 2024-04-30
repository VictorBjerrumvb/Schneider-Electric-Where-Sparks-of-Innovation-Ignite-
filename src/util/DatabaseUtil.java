package util;

import java.sql.*;

/**
 * Utility class for querying a database.
 */
public class DatabaseUtil {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/databaseName";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    /**
     * Executes a SQL query on the specified table with optional column selection.
     *
     * @param tableName The name of the table to query.
     * @param columns   Optional: The columns to select. If not specified, all columns are selected.
     */
    public static void queryDatabase(String tableName, String... columns) {
        // Construct the query
        String query = "SELECT ";
        if (columns.length == 0) {
            query += "*"; // If no columns specified, select all columns
        } else {
            for (int i = 0; i < columns.length; i++) {
                query += columns[i];
                if (i < columns.length - 1) {
                    query += ", ";
                }
            }
        }
        query += " FROM " + tableName;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Iterate over the result set
            while (rs.next()) {
                // Process each row
                int id = rs.getInt("id");
                String data = rs.getString("dataColumn");
                // Output the retrieved data
                System.out.println("ID: " + id + ", Data: " + data);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
    }
}
