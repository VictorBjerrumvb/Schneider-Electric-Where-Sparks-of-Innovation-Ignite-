package gui.model;

import be.CreateTeamMapping;
import be.Personnel;
import be.Team;
import bll.TeamMappingManager;
import dal.db.DataAccessException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Model class for managing Team mapping data in the GUI.
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

    private void preprocessTeamMappings(List<CreateTeamMapping> allTeamMappings) {
        for (CreateTeamMapping c : allTeamMappings) {
            teamToPersonnelMap.computeIfAbsent(c.getTeamId(), k -> new HashSet<>()).add(c.getPersonnelId());
        }
    }

    public ObservableList<Personnel> getAllTeamMembers(Team team) throws DataAccessException {
        ObservableList<Personnel> allTeamMembers = FXCollections.observableArrayList();
        Set<Integer> personnelIds = teamToPersonnelMap.getOrDefault(team.getId(), Collections.emptySet());
        if (!personnelIds.isEmpty()) {
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
        List<CreateTeamMapping> allTeamMappings = getAllTeamMappings();
        for (CreateTeamMapping c : allTeamMappings) {
            if (c.getPersonnelId() == personnel.getId()) {
                TeamModel teamModel = new TeamModel();
                Team team = teamModel.getTeamById(c.getTeamId());
                if (team != null) {
                    allPersonnelTeams.add(team);
                }
            }
        }
        return allPersonnelTeams;
    }

    public void deleteTeamMappingWithMappingId(CreateTeamMapping createTeamMapping) {
        try {
            teamMappingManager.deleteTeamMappingWithMappingId(createTeamMapping);
            allTeamMappings.remove(createTeamMapping);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteTeamMappingWithPersonnelId(CreateTeamMapping createTeamMapping) {
        try {
            teamMappingManager.deleteTeamMappingWithPersonnelId(createTeamMapping);
            allTeamMappings.remove(createTeamMapping);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteTeamMappingWithTeamNPersonnelId(CreateTeamMapping createTeamMapping) {
        try {
            teamMappingManager.deleteTeamMappingWithTeamNPersonnelId(createTeamMapping);
            allTeamMappings.removeIf(c -> c.getPersonnelId() == createTeamMapping.getPersonnelId() &&
                    c.getTeamId() == createTeamMapping.getTeamId());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createTeamMapping(CreateTeamMapping createTeamMapping) {
        try {
            CreateTeamMapping t = teamMappingManager.createTeamMapping(createTeamMapping);
            allTeamMappings.add(t);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
