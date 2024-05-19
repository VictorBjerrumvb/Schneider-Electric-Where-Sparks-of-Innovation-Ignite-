package bll;

import be.Team;
import dal.db.DataAccessException;
import dal.db.TeamDAO_DB;

import java.io.IOException;
import java.util.List;

public class TeamManager {
    private final TeamDAO_DB teamDAO_db;

    /**
     * Constructs a TeamManager and initializes DAOs.
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

    /**
     * Retrieves all teams.
     *
     * @return A list of Team objects.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public List<Team> getAllTeams() throws DataAccessException {
        return teamDAO_db.getAllTeams();
    }

    /**
     * Creates a new team.
     *
     * @param newTeam The Team object to create.
     * @return The created Team object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Team createTeam(Team newTeam) throws DataAccessException {
        return teamDAO_db.createTeam(newTeam);
    }

    /**
     * Deletes a team.
     *
     * @param team The Team object to delete.
     * @return The deleted Team object.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public Team deleteTeam(Team team) throws Exception {
        teamDAO_db.deleteTeam(team);
        return team;
    }

    /**
     * Updates a team.
     *
     * @param selectedTeam The Team object to update.
     * @throws DataAccessException If an error occurs while accessing the data.
     */
    public void updateTeam(Team selectedTeam) throws DataAccessException {
        teamDAO_db.updateTeam(selectedTeam);
    }
}
