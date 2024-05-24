package dal.Interface;

import be.Geography;
import dal.db.DataAccessException;

import java.util.List;

/**
 * Interface for performing database operations related to Geography entities.
 */
public interface IGeography {

    /**
     * Retrieves all Geography entities from the database.
     *
     * @return a list containing all Geography entities.
     * @throws Exception if an error occurs during the database operation.
     */
    List<Geography> getAllGeography() throws Exception;

    /**
     * Deletes a Geography entity from the database.
     *
     * @param geography the Geography entity to delete.
     * @return the deleted Geography entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Geography deleteGeography(Geography geography) throws Exception;

    /**
     * Creates a new Geography entity in the database.
     *
     * @param geography the Geography entity to create.
     * @return the created Geography entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Geography createGeography(Geography geography) throws Exception;

    /**
     * Updates the name of an existing Geography entity in the database.
     *
     * @param geography the Geography entity to update.
     * @return the updated Geography entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Geography updateGeographyName(Geography geography) throws Exception;

    /**
     * Updates an existing Geography entity in the database.
     *
     * @param geography the Geography entity to update.
     * @return the updated Geography entity.
     * @throws DataAccessException if an error occurs during the database operation.
     */
    Geography updateGeography(Geography geography) throws DataAccessException;
}
