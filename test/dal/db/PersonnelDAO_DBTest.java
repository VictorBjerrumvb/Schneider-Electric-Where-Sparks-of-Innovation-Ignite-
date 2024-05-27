package dal.db;

import be.Personnel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelDAO_DBTest {

    private PersonnelDAO_DB personnelDAO;

    @BeforeEach
    void setUp() {
        try {
            personnelDAO = new PersonnelDAO_DB();
        } catch (IOException e) {
            fail("Failed to initialize PersonnelDAO_DB");
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllPersonnel() {
        try {
            List<Personnel> allPersonnel = personnelDAO.getAllPersonnel();
            assertNotNull(allPersonnel);
            assertFalse(allPersonnel.isEmpty());
        } catch (DataAccessException e) {
            fail("Failed to retrieve all personnel records: " + e.getMessage());
        }
    }

    @Test
    void createPersonnel() {
        Personnel newPersonnel = new Personnel(0, "JohnDoe", "password", 1, "Admin", 50000.0, "profile.jpg");
        try {
            personnelDAO.createPersonnel(newPersonnel);
            List<Personnel> allPersonnel = personnelDAO.getAllPersonnel();
            assertTrue(allPersonnel.contains(newPersonnel));
        } catch (DataAccessException e) {
            fail("Failed to create personnel: " + e.getMessage());
        }
    }
    @Test
    void updatePersonnel() {
        try {
            List<Personnel> allPersonnel = personnelDAO.getAllPersonnel();
            if (!allPersonnel.isEmpty()) {
                Personnel personnelToUpdate = allPersonnel.get(0);
                personnelToUpdate.setUsername("UpdatedUsername");
                personnelDAO.updatePersonnel(personnelToUpdate);
                allPersonnel = personnelDAO.getAllPersonnel();
                assertTrue(allPersonnel.contains(personnelToUpdate));
            } else {
                fail("No personnel record found for updating");
            }
        } catch (DataAccessException e) {
            fail("Failed to update personnel: " + e.getMessage());
        }
    }

    @Test
    void validateUser() {
        try {
            List<Personnel> allPersonnel = personnelDAO.getAllPersonnel();
            if (!allPersonnel.isEmpty()) {
                Personnel existingPersonnel = allPersonnel.get(0);
                Personnel validatedPersonnel = personnelDAO.validateUser(existingPersonnel.getUsername(), existingPersonnel.getPassword());
                assertNotNull(validatedPersonnel);
            } else {
                fail("No personnel record found for validating user");
            }
        } catch (DataAccessException e) {
            fail("Failed to validate user: " + e.getMessage());
        }
    }
}
