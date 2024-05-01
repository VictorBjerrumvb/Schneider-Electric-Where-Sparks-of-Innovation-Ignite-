package dal.Interface;

import be.Personnel;
import dal.db.DataAccessException;

import java.util.List;

/**
 * Interface for performing database operations related to Personnel entities.
 */
public interface IPersonnel {

    /**
     * Retrieves all Personnel entities from the database.
     *
     * @return a list containing all Personnel entities.
     * @throws Exception if an error occurs during the database operation.
     */
    List<Personnel> getAllPersonnel() throws Exception;

    /**
     * Creates a new Personnel entity in the database.
     *
     * @param personnel the Personnel entity to create.
     * @return the created Personnel entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Personnel createPersonnel(Personnel personnel) throws Exception;

    /**
     * Deletes a Personnel entity from the database.
     *
     * @param personnel the Personnel entity to delete.
     * @return the deleted Personnel entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Personnel deletePersonnel(Personnel personnel) throws Exception;

    /**
     * Updates an existing Personnel entity in the database.
     *
     * @param personnel the Personnel entity to update.
     * @return the updated Personnel entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Personnel updatePersonnel(Personnel personnel) throws Exception;

    /**
     * Validates a user based on the provided username and password.
     *
     * @param userName the username of the user to validate.
     * @param password the password of the user to validate.
     * @return the validated Personnel entity if the user is found, null otherwise.
     * @throws DataAccessException if an error occurs during the database operation.
     */
    Personnel validateUser(String userName, String password) throws DataAccessException;
}
