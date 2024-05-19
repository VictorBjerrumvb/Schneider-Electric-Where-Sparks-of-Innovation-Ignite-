package bll;

import be.CreateTeamMapping;
import dal.db.DataAccessException;
import dal.db.TeamMappingDAO_DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Manages team mappings by interacting with the data access layer.
 */
public class TeamMappingManager {
    private static final Logger logger = LoggerFactory.getLogger(TeamMappingManager.class);
    private final TeamMappingDAO_DB teamMappingDAO;

    /**
     * Constructs a TeamMappingManager and initializes the DAO.
     *
     * @throws IOException If an I/O error occurs.
     */
    public TeamMappingManager() throws IOException {
        try {
            teamMappingDAO = new TeamMappingDAO_DB();
        } catch (IOException e) {
            logger.error("Failed to initialize the DAO: ", e);
            throw new RuntimeException("Failed to initialize the DAO", e);
        }
    }

    /**
     * Retrieves all team mappings.
     *
     * @return A list of team mappings.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public List<CreateTeamMapping> getAllTeamMappings() throws DataAccessException {
        return teamMappingDAO.getTeamMappings();
    }

    /**
     * Creates a new team mapping.
     *
     * @param createTeamMapping The team mapping object to create.
     * @return The created team mapping object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public CreateTeamMapping createTeamMapping(CreateTeamMapping createTeamMapping) throws DataAccessException {
        return teamMappingDAO.createTeamMapping(createTeamMapping);
    }

    /**
     * Deletes a team mapping with the specified mapping ID.
     *
     * @param mappingId The mapping ID of the team mapping to delete.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public void deleteTeamMappingByMappingId(int mappingId) throws Exception {
        try {
            teamMappingDAO.deleteTeamMappingByMappingId(mappingId);
        } catch (Exception e) {
            logger.error("Failed to delete team mapping with mapping ID {}: {}", mappingId, e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes team mappings associated with the specified personnel ID.
     *
     * @param personnelId The personnel ID of the team mappings to delete.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public void deleteTeamMappingsByPersonnelId(int personnelId) throws DataAccessException {
        teamMappingDAO.deleteTeamMappingsByPersonnelId(personnelId);
    }

    /**
     * Deletes team mappings associated with the specified team ID and personnel ID.
     *
     * @param teamId      The team ID of the team mappings to delete.
     * @param personnelId The personnel ID of the team mappings to delete.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public void deleteTeamMappingsByTeamAndPersonnelId(int teamId, int personnelId) throws DataAccessException {
        teamMappingDAO.deleteTeamMappingByTeamAndPersonnelId(teamId, personnelId);
    }

    /**
     * Deletes a team mapping with the specified mapping ID.
     *
     * @param createTeamMapping The CreateTeamMapping object containing the mapping ID to delete.
     */
    public void deleteTeamMappingWithMappingId(CreateTeamMapping createTeamMapping) {
        // Extract mapping ID and call the appropriate DAO method to delete the mapping
        int mappingId = createTeamMapping.getMappingId();
        try {
            teamMappingDAO.deleteTeamMappingByMappingId(mappingId);
        } catch (Exception e) {
            logger.error("Failed to delete team mapping with mapping ID {}: {}", mappingId, e.getMessage());
            // Handle the exception as needed, possibly rethrow or handle in some other way
        }
    }

    /**
     * Deletes team mappings associated with the specified personnel ID.
     *
     * @param createTeamMapping The CreateTeamMapping object containing the personnel ID to delete mappings for.
     */
    public void deleteTeamMappingWithPersonnelId(CreateTeamMapping createTeamMapping) throws DataAccessException {
        // Extract personnel ID and call the appropriate DAO method to delete mappings
        int personnelId = createTeamMapping.getPersonnelId();
        teamMappingDAO.deleteTeamMappingsByPersonnelId(personnelId);
    }

    /**
     * Deletes team mappings associated with the specified team ID and personnel ID.
     *
     * @param createTeamMapping The CreateTeamMapping object containing the team ID and personnel ID to delete mappings for.
     */
    public void deleteTeamMappingWithTeamNPersonnelId(CreateTeamMapping createTeamMapping) throws DataAccessException {
        // Extract team ID and personnel ID and call the appropriate DAO method to delete mappings
        int teamId = createTeamMapping.getTeamId();
        int personnelId = createTeamMapping.getPersonnelId();
        teamMappingDAO.deleteTeamMappingByTeamAndPersonnelId(teamId, personnelId);
    }
}
