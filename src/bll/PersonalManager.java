package bll;

import be.EmployeeProfile;
import be.Personal;
import dal.db.DataAccessException;
import dal.db.EmployeeProfileDAO_DB;
import dal.db.PersonalDAO_DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Manages operations related to personal and employee profiles.
 */
public class PersonalManager {
    private static final Logger logger = LoggerFactory.getLogger(PersonalManager.class);
    private PersonalDAO_DB personalDAO_db;
    private EmployeeProfileDAO_DB employeeProfileDAO_db;

    /**
     * Constructs a PersonalManager and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public PersonalManager() throws IOException {
        try {
            personalDAO_db = new PersonalDAO_DB();
            employeeProfileDAO_db = new EmployeeProfileDAO_DB();
        } catch (IOException e) {
            logger.error("Failed to initialize the DAOs: ", e);
            throw new RuntimeException("Failed to initialize the DAOs", e);
        }
    }

    // Personal-related methods

    /**
     * Retrieves all personal records.
     *
     * @return A list of Personal objects.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public List<Personal> getAllPersonal() throws DataAccessException {
        return personalDAO_db.getAllPersonal();
    }

    /**
     * Creates a new personal record.
     *
     * @param newPersonal The Personal object to create.
     * @return The created Personal object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Personal createPersonal(Personal newPersonal) throws DataAccessException {
        return personalDAO_db.createPersonal(newPersonal);
    }

    /**
     * Deletes a personal record.
     *
     * @param worker The Personal object to delete.
     * @return The deleted Personal object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Personal deletePersonal(Personal worker) throws DataAccessException {
        personalDAO_db.deletePersonal(worker);
        return worker;
    }

    /**
     * Updates an existing personal record.
     *
     * @param selectedPersonal The Personal object to update.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public void updatePersonal(Personal selectedPersonal) throws DataAccessException {
        personalDAO_db.updatePersonal(selectedPersonal);
    }

    /**
     * Validates a personal with the provided username and password.
     *
     * @param userName The username to validate.
     * @param password The password to validate.
     * @return The validated Personal object, or null if validation fails.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Personal validatePersonal(String userName, String password) throws DataAccessException {
        return personalDAO_db.validateUser(userName, password);
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
