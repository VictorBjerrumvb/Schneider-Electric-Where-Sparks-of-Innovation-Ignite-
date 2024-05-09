package bll;

import be.Personnel;
import be.Team;
import dal.db.DataAccessException;
import dal.db.EmployeeProfileDAO_DB;
import dal.db.PersonnelDAO_DB;
import dal.db.TeamDAO_DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class TeamManager {
    private TeamDAO_DB teamDAO_db;

    /**
     * Constructs a PersonnelManager and initializes DAOs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public TeamManager() throws IOException {
        try {
            teamDAO_db = new TeamDAO_DB();
        } catch (IOException e) {
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
    public List<Team> getAllTeams() throws DataAccessException {
        return teamDAO_db.getAllTeams();
    }

    /**
     * Creates a new Personnel record.
     *
     * @param newTeam The Personnel object to create.
     * @return The created Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Team createTeam(Team newTeam) throws DataAccessException {
        return teamDAO_db.createTeam(newTeam);
    }

    /**
     * Deletes a Personnel record.
     *
     * @param team The Personnel object to delete.
     * @return The deleted Personnel object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Team deleteTeam(Team team) throws Exception {
        teamDAO_db.deleteTeam(team);
        return team;
    }

    public void updateTeam(Team selectedTeam) throws DataAccessException {
        teamDAO_db.updateTeam(selectedTeam);
    }
}
