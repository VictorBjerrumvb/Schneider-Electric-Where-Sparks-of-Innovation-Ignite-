package bll;

import dal.db.GeographyDAO_DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

class CountryManagerTest {

    private GeographyDAO_DB geographyDAO;
    private CountryManager countryManager;

    @BeforeEach
    void setUp() {
        try {
            geographyDAO = mock(GeographyDAO_DB.class);
            countryManager = new CountryManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GeographyDAO_DB mock(Class<GeographyDAO_DB> geographyDAODbClass) {
        return null;
    }

    @AfterEach
    void tearDown() {
        geographyDAO = null;
        countryManager = null;
    }
    /*
    @Test
    void getAllGeography() {
        try {
            List<Geography> expectedGeographies = new ArrayList<>();
            expectedGeographies.add(new Geography(1, "Country 1"));
            expectedGeographies.add(new Geography(2, "Country 2"));

            when(geographyDAO.getAllGeography()).then(expectedGeographies);

            List<Geography> actualGeographies = countryManager.getAllGeography();

            assertEquals(expectedGeographies.size(), actualGeographies.size());
            for (int i = 0; i < expectedGeographies.size(); i++) {
                assertEquals(expectedGeographies.get(i), actualGeographies.get(i));
            }
        } catch (DataAccessException e) {
            fail("Failed to retrieve geographies: " + e.getMessage());
        }
    }

    @Test
    void createGeography() {
        try {
            Geography geography = new Geography(1, "Country 1");
            when(geographyDAO.createGeography(geography)).thenReturn(geography);

            Geography createdGeography = countryManager.createGeography(geography);
            assertEquals(geography, createdGeography);
        } catch (DataAccessException e) {
            fail("Failed to create geography: " + e.getMessage());
        }
    }

    @Test
    void deleteGeography() {
        try {
            Geography geography = new Geography(1, "Country 1");
            doNothing().when(geographyDAO).deleteGeography(geography);

            Geography deletedGeography = countryManager.deleteGeography(geography);
            assertEquals(geography, deletedGeography);
        } catch (Exception e) {
            fail("Failed to delete geography: " + e.getMessage());
        }
    }

    @Test
    void updateGeography() {
        try {
            Geography geography = new Geography(1, "Country 1");
            doNothing().when(geographyDAO).updateGeography(geography);

            assertDoesNotThrow(() -> countryManager.updateGeography(geography));
        } catch (DataAccessException e) {
            fail("Failed to update geography: " + e.getMessage());
        }
    }

    @Test
    void updateGeographyName() {
        try {
            Geography geography = new Geography(1, "Country 1");
            doNothing().when(geographyDAO).updateGeographyName(geography);

            assertDoesNotThrow(() -> countryManager.updateGeographyName(geography));
        } catch (DataAccessException e) {
            fail("Failed to update geography name: " + e.getMessage());
        }
    }

     */
}
