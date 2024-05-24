package dal.Interface;

import be.ManagerMembers;
import dal.db.DataAccessException;

import java.util.List;

/**
 * Interface for performing database operations related to ManagerMembers entities.
 */
public interface IManagerMember {

    /**
     * Retrieves all ManagerMembers entities from the database.
     *
     * @return a list containing all ManagerMembers entities.
     * @throws Exception if an error occurs during the database operation.
     */
    List<ManagerMembers> getManagerMembers() throws Exception;

    /**
     * Deletes a ManagerMembers entity from the database.
     *
     * @param managerMembers the ManagerMembers entity to delete.
     * @return the deleted ManagerMembers entity.
     * @throws Exception if an error occurs during the database operation.
     */
    ManagerMembers deleteManagerMember(ManagerMembers managerMembers) throws Exception;

    /**
     * Deletes a manager from the database.
     *
     * @param managerMembers the manager entity to delete.
     * @return the deleted manager entity.
     * @throws Exception if an error occurs during the database operation.
     */
    ManagerMembers deleteManagerManager(ManagerMembers managerMembers) throws Exception;

    /**
     * Deletes a manager's team from the database.
     *
     * @param managerMembers the manager's team entity to delete.
     * @return the deleted manager's team entity.
     * @throws Exception if an error occurs during the database operation.
     */
    ManagerMembers deleteManagerTeam(ManagerMembers managerMembers) throws Exception;

    /**
     * Creates a new ManagerMembers entity in the database.
     *
     * @param managerMembers the ManagerMembers entity to create.
     * @return the created ManagerMembers entity.
     * @throws DataAccessException if an error occurs during the database operation.
     */
    ManagerMembers createManagerMember(ManagerMembers managerMembers) throws DataAccessException;
}
