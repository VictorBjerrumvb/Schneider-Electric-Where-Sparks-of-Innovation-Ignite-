package bll;

import be.Personnel;
import dal.db.DataAccessException;
import dal.db.PersonnelDAO_DB;
import org.mindrot.jbcrypt.BCrypt;
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

    /**
     * Constructs a PersonnelManager and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public PersonnelManager() throws IOException {
        try {
            personnelDAO_db = new PersonnelDAO_DB();
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
     * @param userPassword The password to validate.
     * @return The validated Personnel object, or null if validation fails.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Personnel validatePersonnel(String userName, String userPassword) throws DataAccessException {
        // Fetch the personnel from the database based on the username
        Personnel personnel = getPersonnelByUserName(userName);

        if (personnel != null) {
            // Get the stored hashed password
            String storedHashedPassword = personnel.getPassword();

            // Check if the provided password matches the stored hashed password
            if (BCrypt.checkpw(userPassword, storedHashedPassword)) {
                return personnel; // Password is correct
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("User not found.");
        }

        return null; // Return null if validation fails
    }

    private Personnel getPersonnelByUserName(String userName) throws DataAccessException {
        List<Personnel> allPersonnel = getAllPersonnel(); // Implement this method to fetch all personnel from your data source
        for (Personnel p : allPersonnel) {
            if (p.getUsername().equalsIgnoreCase(userName)) {
                return p; // Return the personnel object if the username matches
            }
        }
        return null; // Return null if the user is not found
    }
}
