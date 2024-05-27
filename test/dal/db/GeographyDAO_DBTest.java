package dal.db;

import be.Geography;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeographyDAO_DBTest {

    private GeographyDAO_DB geographyDAO;

    @BeforeEach
    void setUp() {
        try {
            geographyDAO = new GeographyDAO_DB();
        } catch (DataAccessException e) {
            fail("Failed to initialize GeographyDAO_DB");
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllGeography() {
        try {
            List<Geography> allGeography = geographyDAO.getAllGeography();
            assertNotNull(allGeography);
            assertFalse(allGeography.isEmpty());
        } catch (DataAccessException e) {
            fail("Failed to retrieve all geography data: " + e.getMessage());
        }
    }

    @Test
    void deleteGeography() {
        try {
            List<Geography> allGeography = geographyDAO.getAllGeography();
            if (!allGeography.isEmpty()) {
                // Delete the first geography record
                Geography geographyToDelete = allGeography.get(0);
                geographyDAO.deleteGeography(geographyToDelete);
                allGeography = geographyDAO.getAllGeography();
                assertFalse(allGeography.contains(geographyToDelete));
            } else {
                fail("No geography record found for deletion");
            }
        } catch (DataAccessException e) {
            fail("Failed to delete geography: " + e.getMessage());
        }
    }

    @Test
    void createGeography() {
        Geography newGeography = new Geography(0, "New Country", "New Gross", 0.0);
        try {
            geographyDAO.createGeography(newGeography);
            List<Geography> allGeography = geographyDAO.getAllGeography();
            assertTrue(allGeography.contains(newGeography));
        } catch (DataAccessException e) {
            fail("Failed to create geography: " + e.getMessage());
        }
    }

    @Test
    void updateGeographyName() {
        try {
            List<Geography> allGeography = geographyDAO.getAllGeography();
            if (!allGeography.isEmpty()) {
                Geography geographyToUpdate = allGeography.get(0);
                String updatedName = "Updated Country";
                geographyToUpdate.setCountry(updatedName);
                geographyDAO.updateGeographyName(geographyToUpdate);
                allGeography = geographyDAO.getAllGeography();
                Geography updatedGeography = allGeography.stream()
                        .filter(g -> g.getId() == geographyToUpdate.getId())
                        .findFirst().orElse(null);
                assertNotNull(updatedGeography);
                assertEquals(updatedName, updatedGeography.getCountry());
            } else {
                fail("No geography record found for updating name");
            }
        } catch (DataAccessException e) {
            fail("Failed to update geography name: " + e.getMessage());
        }
    }

    @Test
    void updateGeography() {
        try {
            List<Geography> allGeography = geographyDAO.getAllGeography();
            if (!allGeography.isEmpty()) {
                Geography geographyToUpdate = allGeography.get(0);
                String updatedGross = "Updated Gross";
                double updatedMargin = 10.0;
                geographyToUpdate.setCountryGross(updatedGross);
                geographyToUpdate.setCountryMargin(updatedMargin);
                geographyDAO.updateGeography(geographyToUpdate);
                allGeography = geographyDAO.getAllGeography();
                Geography updatedGeography = allGeography.stream()
                        .filter(g -> g.getId() == geographyToUpdate.getId())
                        .findFirst().orElse(null);
                assertNotNull(updatedGeography);
                assertEquals(updatedGross, updatedGeography.getCountryGross());
                assertEquals(updatedMargin, updatedGeography.getCountryMargin());
            } else {
                fail("No geography record found for updating gross and margin");
            }
        } catch (DataAccessException e) {
            fail("Failed to update geography: " + e.getMessage());
        }
    }
}
