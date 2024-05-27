package dal.db;

import be.CreateTeamMapping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamMappingDAO_DBTest {

    private TeamMappingDAO_DB teamMappingDAO;

    @BeforeEach
    void setUp() {
        try {
            teamMappingDAO = new TeamMappingDAO_DB();
        } catch (IOException e) {
            fail("Failed to initialize TeamMappingDAO_DB");
        }
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllTeamMappings() {
        try {
            List<CreateTeamMapping> allTeamMappings = teamMappingDAO.getAllTeamMappings();
            assertNotNull(allTeamMappings);
            assertFalse(allTeamMappings.isEmpty());
        } catch (DataAccessException e) {
            fail("Failed to retrieve all team mappings: " + e.getMessage());
        }
    }
    @Test
    void deleteTeamMappingsByPersonnelId() {
        // Assuming there's at least one team mapping record to delete for testing
        try {
            List<CreateTeamMapping> allTeamMappings = teamMappingDAO.getAllTeamMappings();
            if (!allTeamMappings.isEmpty()) {
                // Delete team mappings for the first personnel ID
                int personnelId = allTeamMappings.get(0).getPersonnelId();
                teamMappingDAO.deleteTeamMappingsByPersonnelId(personnelId);

                // Check if the team mappings are deleted by trying to retrieve them
                allTeamMappings = teamMappingDAO.getAllTeamMappings();
                assertTrue(allTeamMappings.stream().noneMatch(mapping -> mapping.getPersonnelId() == personnelId));
            } else {
                fail("No team mapping record found for deletion");
            }
        } catch (DataAccessException e) {
            fail("Failed to delete team mappings: " + e.getMessage());
        }
    }

    @Test
    void getTeamMappings() {
        try {
            List<CreateTeamMapping> teamMappings = teamMappingDAO.getTeamMappings();
            assertNotNull(teamMappings);
            assertFalse(teamMappings.isEmpty());
        } catch (DataAccessException e) {
            fail("Failed to retrieve team mappings: " + e.getMessage());
        }
    }
}
