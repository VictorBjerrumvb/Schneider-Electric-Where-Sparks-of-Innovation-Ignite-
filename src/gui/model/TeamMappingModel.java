package gui.model;

import be.CreateTeamMapping;
import be.Personnel;
import be.Team;
import bll.TeamManager;
import bll.TeamMappingManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Model class for managing Personnel data in the GUI.
 */
public class TeamMappingModel {
    private ObservableList<CreateTeamMapping> allTeamMappings;
    private TeamMappingManager teamMappingManager;
    // Assuming you have a class level variable to store team mappings
    private Map<Integer, Set<Integer>> teamToPersonnelMap = new HashMap<>();


    /**
     * Constructs a new PersonnelModel instance.
     */
    public TeamMappingModel() {
        try {
            teamMappingManager = new TeamMappingManager();
            allTeamMappings = FXCollections.observableArrayList();
            // Fetch all Personnel data from the manager and add it to the observable list
            allTeamMappings.addAll(teamMappingManager.getAllTeamMappings());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves the observable list of all Personnel data.
     *
     * @return The observable list of Personnel data.
     */
    public ObservableList<CreateTeamMapping> getAllTeamMappings() {
        return allTeamMappings;
    }

    // Method to preprocess data
    public void preprocessTeamMappings(List<CreateTeamMapping> allTeamMappings) {
        for (CreateTeamMapping c : allTeamMappings) {
            teamToPersonnelMap.computeIfAbsent(c.getTeamId(), k -> new HashSet<>()).add(c.getPersonnelId());
        }
    }

    // Method to get all team members
    public ObservableList<Personnel> getAllTeamMembers(Team team) {
        ObservableList<Personnel> allTeamMembers = FXCollections.observableArrayList();
        Set<Integer> personnelIds = teamToPersonnelMap.getOrDefault(team.getId(), Collections.emptySet());
        PersonnelModel personnelModel = new PersonnelModel();
        for (int personnelId : personnelIds) {
            Personnel p = personnelModel.getPersonnelById(personnelId); // Assuming a method to get personnel by ID
            if (p != null) {
                allTeamMembers.add(p);
            }
        }
        return allTeamMembers;
    }
    public Team getPersonnelTeam(Personnel personnel) {
        Team pTeam = new Team();
        TeamModel teamModel = new TeamModel();
        // Assuming you have a class level variable to store team mappings
        Map<Integer, Integer> personnelToTeamMap = new HashMap<>();

        // Preprocess team mappings data
        for (CreateTeamMapping c : allTeamMappings) {
            personnelToTeamMap.put(c.getPersonnelId(), c.getTeamId());
        }

        // Retrieve the team ID associated with the personnel
        Integer teamId = personnelToTeamMap.get(personnel.getId());
        if (teamId != null) {
            // Assuming you have a method to get a team by its ID
            pTeam = teamModel.getTeamById(teamId);
        }
        return pTeam;
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
    public void deleteTeamMappingWithTeamNPersonnelId(CreateTeamMapping createTeamMapping) {
        try {
            teamMappingManager.deleteTeamMappingWithTeamNPersonnelId(createTeamMapping);
            allTeamMappings.remove(createTeamMapping);
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
