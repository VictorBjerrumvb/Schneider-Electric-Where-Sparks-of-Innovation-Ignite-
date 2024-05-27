package dal.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

    private DatabaseConnector databaseConnector;

    @BeforeEach
    void setUp() {
        try {
            databaseConnector = new DatabaseConnector();
        } catch (IOException ex) {
            fail("Failed to initialize DatabaseConnector: " + ex.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getConnection_Success() {
        try {
            Connection connection = databaseConnector.getConnection();
            assertNotNull(connection);
            assertFalse(connection.isClosed());
            connection.close();
        } catch (SQLException ex) {
            fail("Failed to get connection: " + ex.getMessage());
        }
    }
}
