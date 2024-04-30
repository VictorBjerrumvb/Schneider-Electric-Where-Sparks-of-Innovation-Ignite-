package dal.db;

/**
 * Custom exception class for database access errors.
 */
public class DataAccessException extends Exception {

    /**
     * Constructs a new DataAccessException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new DataAccessException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     *                A null value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
