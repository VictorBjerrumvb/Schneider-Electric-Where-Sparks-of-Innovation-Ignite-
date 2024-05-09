package bll;

import be.CreateTeamMapping;
import be.EmployeeProfile;
import be.Personnel;
import dal.db.DataAccessException;
import dal.db.EmployeeProfileDAO_DB;
import dal.db.PersonnelDAO_DB;
import dal.db.TeamMappingDAO_DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class TeamMappingManager {
    private static final Logger logger = LoggerFactory.getLogger(TeamMappingManager.class);
    private TeamMappingDAO_DB teamMappingDAO_db;

    /**
     * Constructs a PersonnelManager and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public TeamMappingManager() throws IOException {
        try {
            teamMappingDAO_db = new TeamMappingDAO_DB();
        } catch (IOException e) {
            logger.error("Failed to initialize the DAOs: ", e);
            throw new RuntimeException("Failed to initialize the DAOs", e);
        }
    }

    // Personnel-related methods

    /**
     * Retrieves all Personnel records.
     *
     * @return A list of Personnel objects.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public List<CreateTeamMapping> getAllTeamMappings() throws DataAccessException {
        return teamMappingDAO_db.getTeamMappings();
    }

    /**
     * Creates a new Personnel record.
     *
     * @param createTeamMapping The Personnel object to create.
     * @return The created Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public CreateTeamMapping createTeamMapping(CreateTeamMapping createTeamMapping) throws DataAccessException {
        return teamMappingDAO_db.createTeamMapping(createTeamMapping);
    }

    /**
     * Deletes a Personnel record.
     *
     * @param deleteTeamMapping The Personnel object to delete.
     * @return The deleted Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public CreateTeamMapping deleteTeamMappingWithMappingId(CreateTeamMapping deleteTeamMapping) throws Exception {
        teamMappingDAO_db.deleteTeamMappingWithMappingId(deleteTeamMapping);
        return deleteTeamMapping;
    }

    /**
     * Deletes a Personnel record.
     *
     * @param deleteTeamMapping The Personnel object to delete.
     * @return The deleted Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public CreateTeamMapping deleteTeamMappingWithPersonnelId(CreateTeamMapping deleteTeamMapping) throws Exception {
        teamMappingDAO_db.deleteTeamMappingWithPersonnelId(deleteTeamMapping);
        return deleteTeamMapping;
    }

    /**
     * Deletes a Personnel record.
     *
     * @param deleteTeamMapping The Personnel object to delete.
     * @return The deleted Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public CreateTeamMapping deleteTeamMappingWithTeamNPersonnelId(CreateTeamMapping deleteTeamMapping) throws Exception {
        teamMappingDAO_db.deleteTeamMappingWithTeamNPersonnelId(deleteTeamMapping);
        return deleteTeamMapping;
    }
}
