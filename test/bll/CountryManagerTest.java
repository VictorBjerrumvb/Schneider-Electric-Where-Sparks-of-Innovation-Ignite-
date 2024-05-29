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
}
