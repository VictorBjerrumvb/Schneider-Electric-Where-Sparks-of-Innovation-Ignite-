package gui.model;

import be.CreateTeamMapping;
import be.Personnel;
import be.Team;
import bll.TeamManager;
import bll.TeamMappingManager;
import dal.db.DataAccessException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Model class for managing Personnel data in the GUI.
 */
public class TeamMappingModel {
    private ObservableList<CreateTeamMapping> allTeamMappings;
    private TeamMappingManager teamMappingManager;
    private Map<Integer, Set<Integer>> teamToPersonnelMap = new HashMap<>();

    public TeamMappingModel() {
        try {
            teamMappingManager = new TeamMappingManager();
            allTeamMappings = FXCollections.observableArrayList();
            allTeamMappings.addAll(teamMappingManager.getAllTeamMappings());
            preprocessTeamMappings(allTeamMappings);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ObservableList<CreateTeamMapping> getAllTeamMappings() {
        return allTeamMappings;
    }

    private Map<Integer, Set<Integer>> getTeamToPersonnelMap() {
        return teamToPersonnelMap;
    }

    private void preprocessTeamMappings(List<CreateTeamMapping> allTeamMappings) {
        for (CreateTeamMapping c : allTeamMappings) {
            teamToPersonnelMap.computeIfAbsent(c.getTeamId(), k -> new HashSet<>()).add(c.getPersonnelId());
        }
    }

    // Method to get all team members
    public ObservableList<Personnel> getAllTeamMembers(Team team) throws DataAccessException {

        ObservableList<Personnel> allTeamMembers = FXCollections.observableArrayList();

        Map<Integer, Set<Integer>> teamToPersonnelMap = getTeamToPersonnelMap();

        Set<Integer> personnelIds = teamToPersonnelMap.get(team.getId());
        if (personnelIds != null) {
            PersonnelModel personnelModel = new PersonnelModel();
            for (int personnelId : personnelIds) {
                Personnel personnel = personnelModel.getPersonnelById(personnelId);
                if (personnel != null) {
                    allTeamMembers.add(personnel);
                }
            }
        }

        return allTeamMembers;
    }


    public ObservableList<Team> getPersonnelTeam(Personnel personnel) {
        ObservableList<Team> allPersonnelTeams = FXCollections.observableArrayList();

        Map<Integer, List<Integer>> personnelToTeamMap = new HashMap<>();

        // Preprocess team mappings data
        List<CreateTeamMapping> allTeamMappings = getAllTeamMappings();
        for (CreateTeamMapping c : allTeamMappings) {
            personnelToTeamMap.computeIfAbsent(c.getPersonnelId(), k -> new ArrayList<>()).add(c.getTeamId());
        }

        // Retrieve the team IDs associated with the personnel
        List<Integer> teamIds = personnelToTeamMap.get(personnel.getId());
        if (teamIds != null) {
            TeamModel teamModel = new TeamModel();
            for (Integer teamId : teamIds) {
                Team team = teamModel.getTeamById(teamId);
                if (team != null) {
                    allPersonnelTeams.add(team);
                }
            }
        }

        return allPersonnelTeams;
    }


    /**
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param createTeamMapping The Personnel data to delete.
     */
    public void deleteTeamMappingWithMappingId(CreateTeamMapping createTeamMapping) {
        try {
            teamMappingManager.deleteTeamMappingWithMappingId(createTeamMapping);
            allTeamMappings.remove(createTeamMapping);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param createTeamMapping The Personnel data to delete.
     */
    public void deleteTeamMappingWithPersonnelId(CreateTeamMapping createTeamMapping) {
        try {
            teamMappingManager.deleteTeamMappingWithPersonnelId(createTeamMapping);
            allTeamMappings.remove(createTeamMapping);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param createTeamMapping The Personnel data to delete.
     */
    public void deleteTeamMappingWithTeamNPersonnelId(CreateTeamMapping createTeamMapping) {
        try {
            teamMappingManager.deleteTeamMappingWithTeamNPersonnelId(createTeamMapping);
            for (CreateTeamMapping c : getAllTeamMappings()) {
                if (c.getPersonnelId() == createTeamMapping.getPersonnelId() &&
                    c.getTeamId() == createTeamMapping.getTeamId()) {
                    allTeamMappings.remove(c);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Creates new Personnel data using the provided information and adds it to the manager and the observable list.
     *
     * @param createTeamMapping The Personnel data to create.
     */
    public void createTeam(CreateTeamMapping createTeamMapping) {
        try {
            // Create the Team data through the manager
            CreateTeamMapping t = teamMappingManager.createTeamMapping(createTeamMapping);
            // Add the created Team data to the observable list
            allTeamMappings.add(t);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
