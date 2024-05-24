package dal.Interface;

import be.CreateTeamMapping;
import dal.db.DataAccessException;

import java.util.List;

/**
 * Interface for performing database operations related to Team Mappings.
 */
public interface ITeamMapping {

    /**
     * Retrieves all Team Mappings from the database.
     *
     * @return a list containing all Team Mappings.
     * @throws Exception if an error occurs during the database operation.
     */
    List<CreateTeamMapping> getAllTeamMappings() throws Exception;

    /**
     * Deletes a Team Mapping by team and personnel ID from the database.
     *
     * @param teamId      the ID of the team.
     * @param personnelId the ID of the personnel.
     * @throws Exception if an error occurs during the database operation.
     */
    void deleteTeamMappingByTeamAndPersonnelId(int teamId, int personnelId) throws Exception;

    /**
     * Deletes a Team Mapping by mapping ID from the database.
     *
     * @param mappingId the ID of the mapping to delete.
     * @throws Exception if an error occurs during the database operation.
     */
    void deleteTeamMappingByMappingId(int mappingId) throws Exception;

    /**
     * Creates a new Team Mapping entity in the database.
     *
     * @param createTeamMapping the Team Mapping entity to create.
     * @return the created Team Mapping entity.
     * @throws Exception if an error occurs during the database operation.
     */
    CreateTeamMapping createTeamMapping(CreateTeamMapping createTeamMapping) throws Exception;

    /**
     * Deletes Team Mappings by personnel ID from the database.
     *
     * @param id the ID of the personnel.
     * @throws DataAccessException if an error occurs during the database operation.
     */
    void deleteTeamMappingsByPersonnelId(int id) throws DataAccessException;

    /**
     * Retrieves all Team Mappings from the database.
     *
     * @return a list containing all Team Mappings.
     * @throws Exception if an error occurs during the database operation.
     */
    List<CreateTeamMapping> getTeamMappings() throws Exception;
}
