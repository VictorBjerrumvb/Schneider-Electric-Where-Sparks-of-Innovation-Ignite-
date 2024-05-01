package bll;

import be.EmployeeProfile;
import be.Personnel;
import dal.db.DataAccessException;
import dal.db.EmployeeProfileDAO_DB;
import dal.db.PersonnelDAO_DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Manages operations related to Personnel and employee profiles.
 */
public class PersonnelManager {
    private static final Logger logger = LoggerFactory.getLogger(PersonnelManager.class);
    private PersonnelDAO_DB personnelDAO_db;
    private EmployeeProfileDAO_DB employeeProfileDAO_db;

    /**
     * Constructs a PersonnelManager and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public PersonnelManager() throws IOException {
        try {
            personnelDAO_db = new PersonnelDAO_DB();
            employeeProfileDAO_db = new EmployeeProfileDAO_DB();
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
    public List<Personnel> getAllPersonnel() throws DataAccessException {
        return personnelDAO_db.getAllPersonnel();
    }

    /**
     * Creates a new Personnel record.
     *
     * @param newPersonnel The Personnel object to create.
     * @return The created Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Personnel createPersonnel(Personnel newPersonnel) throws DataAccessException {
        return personnelDAO_db.createPersonnel(newPersonnel);
    }

    /**
     * Deletes a Personnel record.
     *
     * @param worker The Personnel object to delete.
     * @return The deleted Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Personnel deletePersonnel(Personnel worker) throws DataAccessException {
        personnelDAO_db.deletePersonnel(worker);
        return worker;
    }

    /**
     * Updates an existing Personnel record.
     *
     * @param selectedPersonnel The Personnel object to update.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public void updatePersonnel(Personnel selectedPersonnel) throws DataAccessException {
        personnelDAO_db.updatePersonnel(selectedPersonnel);
    }

    /**
     * Validates a Personnel with the provided username and password.
     *
     * @param userName The username to validate.
     * @param password The password to validate.
     * @return The validated Personnel object, or null if validation fails.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Personnel validatePersonnel(String userName, String password) throws DataAccessException {
        return personnelDAO_db.validateUser(userName, password);
    }

    // EmployeeProfile-related methods

    public List<EmployeeProfile> getAllEmployeeProfiles() throws DataAccessException {
        return employeeProfileDAO_db.getAllEmployeeProfiles();
    }

    public EmployeeProfile createEmployeeProfile(EmployeeProfile employeeProfile) throws DataAccessException {
        return employeeProfileDAO_db.createEmployeeProfile(employeeProfile);
    }

    public void updateEmployeeProfile(EmployeeProfile employeeProfile) throws DataAccessException {
        employeeProfileDAO_db.updateEmployeeProfile(employeeProfile);
    }

    public void deleteEmployeeProfile(EmployeeProfile employeeProfile) throws DataAccessException {
        employeeProfileDAO_db.deleteEmployeeProfile(employeeProfile);
    }

    // Rate-related methods (if applicable)

    // Add methods for calculating rates, handling rate configurations, etc.

}
