package dal.db;

import be.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamDAO_DBTest {

    private TeamDAO_DB teamDAO;

    @BeforeEach
    void setUp() {
        try {
            teamDAO = new TeamDAO_DB();
        } catch (IOException e) {
            fail("Failed to initialize TeamDAO_DB");
        }
    }
    @Test
    void getAllTeams() {
        try {
            List<Team> allTeams = teamDAO.getAllTeams();
            assertNotNull(allTeams);
            assertFalse(allTeams.isEmpty());
        } catch (DataAccessException e) {
            fail("Failed to retrieve all team records: " + e.getMessage());
        }
    }
}
