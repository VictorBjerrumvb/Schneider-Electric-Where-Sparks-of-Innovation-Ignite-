package bll;

import be.CreateTeamMapping;
import be.ManagerMembers;
import dal.db.DataAccessException;
import dal.db.ManagerMembersDAO_DB;
import dal.db.TeamMappingDAO_DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ManagerMembersManager {
    private static final Logger logger = LoggerFactory.getLogger(ManagerMembersManager.class);
    private ManagerMembersDAO_DB managerMembersDAO_db;

    /**
     * Constructs a PersonnelManager and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public ManagerMembersManager() throws IOException {
        try {
            managerMembersDAO_db = new ManagerMembersDAO_DB();
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
    public List<ManagerMembers> getAllManagerMembers() throws DataAccessException {
        return managerMembersDAO_db.getManagerMembers();
    }

    /**
     * Creates a new Personnel record.
     *
     * @param managerMembers The Personnel object to create.
     * @return The created Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public ManagerMembers createManagerMembers(ManagerMembers managerMembers) throws DataAccessException {
        return managerMembersDAO_db.createManagerMember(managerMembers);
    }

    /**
     * Deletes a Personnel record.
     *
     * @param managerMembers The Personnel object to delete.
     * @return The deleted Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public ManagerMembers deleteManagerMember(ManagerMembers managerMembers) throws Exception {
        managerMembersDAO_db.deleteManagerMember(managerMembers);
        return managerMembers;
    }

    public ManagerMembers deleteManagerManager(ManagerMembers managerMembers) throws Exception {
        managerMembersDAO_db.deleteManagerManager(managerMembers);
        return managerMembers;
    }
}
