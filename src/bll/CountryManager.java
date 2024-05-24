package bll;

import be.Geography;
import be.Team;
import dal.db.DataAccessException;
import dal.db.GeographyDAO_DB;
import dal.db.TeamDAO_DB;

import java.io.IOException;
import java.util.List;

public class CountryManager {
    private final GeographyDAO_DB geographyDAO_db;

    /**
     * Constructs a TeamManager and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public CountryManager() throws IOException {
        try {
            geographyDAO_db = new GeographyDAO_DB();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all teams.
     *
     * @return A list of Team objects.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public List<Geography> getAllGeography() throws DataAccessException {
        return geographyDAO_db.getAllGeography();
    }

    /**
     * Creates a new team.
     *
     * @param geography The Team object to create.
     * @return The created Team object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Geography createGeography(Geography geography) throws DataAccessException {
        return geographyDAO_db.createGeography(geography);
    }

    /**
     * Deletes a team.
     *
     * @param geography The Team object to delete.
     * @return The deleted Team object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Geography deleteGeography(Geography geography) throws Exception {
        geographyDAO_db.deleteGeography(geography);
        return geography;
    }

    /**
     * Updates a team.
     *
     * @param geography The Team object to update.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public void updateGeography(Geography geography) throws DataAccessException {
        geographyDAO_db.updateGeography(geography);
    }

    public void updateGeographyName(Geography geography) throws DataAccessException {
        geographyDAO_db.updateGeographyName(geography);
    }
}
