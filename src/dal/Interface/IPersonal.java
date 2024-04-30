package dal.Interface;

import be.Personal;
import dal.db.DataAccessException;

import java.util.List;

/**
 * Interface for performing database operations related to Personal entities.
 */
public interface IPersonal {

    /**
     * Retrieves all Personal entities from the database.
     *
     * @return a list containing all Personal entities.
     * @throws Exception if an error occurs during the database operation.
     */
    List<Personal> getAllPersonal() throws Exception;

    /**
     * Creates a new Personal entity in the database.
     *
     * @param personal the Personal entity to create.
     * @return the created Personal entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Personal createPersonal(Personal personal) throws Exception;

    /**
     * Deletes a Personal entity from the database.
     *
     * @param personal the Personal entity to delete.
     * @return the deleted Personal entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Personal deletePersonal(Personal personal) throws Exception;

    /**
     * Updates an existing Personal entity in the database.
     *
     * @param personal the Personal entity to update.
     * @return the updated Personal entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Personal updatePersonal(Personal personal) throws Exception;

    /**
     * Validates a user based on the provided username and password.
     *
     * @param userName the username of the user to validate.
     * @param password the password of the user to validate.
     * @return the validated Personal entity if the user is found, null otherwise.
     * @throws DataAccessException if an error occurs during the database operation.
     */
    Personal validateUser(String userName, String password) throws DataAccessException;
}
