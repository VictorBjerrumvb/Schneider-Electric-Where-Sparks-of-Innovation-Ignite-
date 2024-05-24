package dal.Interface;

import be.Team;
import dal.db.DataAccessException;

import java.util.List;

/**
 * Interface for performing database operations related to Team entities.
 */
public interface ITeam {

    /**
     * Retrieves all Team entities from the database.
     *
     * @return a list containing all Team entities.
     * @throws Exception if an error occurs during the database operation.
     */
    List<Team> getAllTeams() throws Exception;

    /**
     * Deletes a Team entity from the database.
     *
     * @param team the Team entity to delete.
     * @return the deleted Team entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Team deleteTeam(Team team) throws Exception;

    /**
     * Creates a new Team entity in the database.
     *
     * @param team the Team entity to create.
     * @return the created Team entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Team createTeam(Team team) throws Exception;

    /**
     * Updates an existing Team entity in the database.
     *
     * @param team the Team entity to update.
     * @return the updated Team entity.
     * @throws Exception if an error occurs during the database operation.
     */
    Team updateTeam(Team team) throws Exception;
}
