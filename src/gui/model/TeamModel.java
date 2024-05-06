package gui.model;

import be.Personnel;
import be.Team;
import bll.TeamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class for managing Personnel data in the GUI.
 */
public class TeamModel {
    private ObservableList<Team> allTeams;
    private TeamManager teamManager;

    /**
     * Constructs a new PersonnelModel instance.
     */
    public TeamModel() {
        try {
            teamManager = new TeamManager();
            allTeams = FXCollections.observableArrayList();
            // Fetch all Personnel data from the manager and add it to the observable list
            allTeams.addAll(teamManager.getAllTeams());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves the observable list of all Personnel data.
     *
     * @return The observable list of Personnel data.
     */
    public ObservableList<Team> getAllTeams() {
        return allTeams;
    }

    /**
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param team The Personnel data to delete.
     */
    public void deleteTeam(Team team) {
        try {
            teamManager.deleteTeam(team);
            allTeams.remove(team);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Creates new Personnel data using the provided information and adds it to the manager and the observable list.
     *
     * @param team The Personnel data to create.
     */
    public void createTeam(Team team) {
        try {
            // Create the Team data through the manager
            Team t = teamManager.createTeam(team);
            // Add the created Team data to the observable list
            allTeams.add(t);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
