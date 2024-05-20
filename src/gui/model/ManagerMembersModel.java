package gui.model;

import be.CreateTeamMapping;
import be.ManagerMembers;
import be.Personnel;
import be.Team;
import bll.ManagerMembersManager;
import dal.db.DataAccessException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Model class for managing Personnel data in the GUI.
 */
public class ManagerMembersModel {
    private ObservableList<ManagerMembers> allManagerMembers;
    private ManagerMembersManager managerMembersManager;
    private Map<Integer, Set<Integer>> managerToPersonnelMap = new HashMap<>();
    private Map<Integer, Set<Integer>> managerToTeamMap = new HashMap<>();

    public ManagerMembersModel() {
        try {
            managerMembersManager = new ManagerMembersManager();
            allManagerMembers = FXCollections.observableArrayList();
            allManagerMembers.addAll(managerMembersManager.getAllManagerMembers());
            preprocessManagerMembers(allManagerMembers);
            preprocessManagerTeams(allManagerMembers);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ObservableList<ManagerMembers> getAllManagerMembers() {
        return allManagerMembers;
    }

    private Map<Integer, Set<Integer>> getManagerToPersonnelMap() {
        return managerToPersonnelMap;
    }
    private Map<Integer, Set<Integer>> getManagerToTeamMap() {
        return managerToTeamMap;
    }

    private void preprocessManagerMembers(List<ManagerMembers> allManagerMembers) {
        for (ManagerMembers m : allManagerMembers) {
            managerToPersonnelMap.computeIfAbsent(m.getManagerId(), k -> new HashSet<>()).add(m.getPersonnelId());
        }
    }
    private void preprocessManagerTeams(List<ManagerMembers> allManagerMembers) {
        for (ManagerMembers m : allManagerMembers) {
            managerToTeamMap.computeIfAbsent(m.getManagerId(), k -> new HashSet<>()).add(m.getTeamId());
        }
    }

    // Method to get all team members
    public ObservableList<Personnel> getAllManagerMembers(Personnel personnel) throws DataAccessException {
        ObservableList<Personnel> allManagerMembers = FXCollections.observableArrayList();

        if (personnel != null) { // Check if personnel is not null
            Map<Integer, Set<Integer>> managerToPersonnelMap = getManagerToPersonnelMap();

            Set<Integer> personnelIds = managerToPersonnelMap.get(personnel.getId());
            if (personnelIds != null) {
                PersonnelModel personnelModel = new PersonnelModel();
                for (int personnelId : personnelIds) {
                    Personnel managerMember = personnelModel.getPersonnelById(personnelId);
                    if (managerMember != null) {
                        allManagerMembers.add(managerMember);
                    }
                }
            }
        }

        return allManagerMembers;
    }

    public ObservableList<Team> getAllManagerTeams(Personnel personnel) throws DataAccessException {
        TeamModel teamModel = new TeamModel();
        ObservableList<Team> allManagerTeams = FXCollections.observableArrayList();

        Map<Integer, Set<Integer>> managerToTeamMap = getManagerToTeamMap();

        Set<Integer> teamIds = managerToTeamMap.get(personnel.getId());
        if (teamIds != null) {
            for (int teamId : teamIds) {
                Team managerTeam = teamModel.getTeamById(teamId);
                if (managerTeam != null) {
                    allManagerTeams.add(managerTeam);
                }
            }
        }

        return allManagerTeams;
    }


    public ObservableList<Personnel> getPersonnelManagers(Personnel personnel) {
        ObservableList<Personnel> allPersonnelManagers = FXCollections.observableArrayList();

        Map<Integer, List<Integer>> personnelToManagerMap = new HashMap<>();

        // Preprocess manager members data
        List<ManagerMembers> allManagerMembers = getAllManagerMembers();
        for (ManagerMembers mm : allManagerMembers) {
            personnelToManagerMap.computeIfAbsent(mm.getPersonnelId(), k -> new ArrayList<>()).add(mm.getManagerId());
        }

        // Retrieve the manager IDs for the specified personnel
        List<Integer> managerIds = personnelToManagerMap.get(personnel.getId());
        if (managerIds != null) {
            PersonnelModel personnelModel = new PersonnelModel();
            for (Integer managerId : managerIds) {
                Personnel manager = personnelModel.getPersonnelById(managerId);
                if (manager != null) {
                    allPersonnelManagers.add(manager);
                }
            }
        }


        return allPersonnelManagers;
    }

    public ObservableList<Personnel> getTeamManagers(Team team) {
        ObservableList<Personnel> allPersonnelManagers = FXCollections.observableArrayList();

        Map<Integer, List<Integer>> teamaToManagerMap = new HashMap<>();

        // Preprocess manager members data
        List<ManagerMembers> allManagerMembers = getAllManagerMembers();
        for (ManagerMembers mm : allManagerMembers) {
            teamaToManagerMap.computeIfAbsent(mm.getTeamId(), k -> new ArrayList<>()).add(mm.getManagerId());
        }

        // Retrieve the manager IDs for the specified personnel
        List<Integer> managerIds = teamaToManagerMap.get(team.getId());
        if (managerIds != null) {
            PersonnelModel personnelModel = new PersonnelModel();
            for (Integer managerId : managerIds) {
                Personnel manager = personnelModel.getPersonnelById(managerId);
                if (manager != null) {
                    allPersonnelManagers.add(manager);
                }
            }
        }


        return allPersonnelManagers;
    }




    /**
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param managerMembers The Personnel data to delete.
     */
    public void deleteManagerMembers(ManagerMembers managerMembers) {
        try {
            managerMembersManager.deleteManagerMember(managerMembers);
            allManagerMembers.remove(managerMembers);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteManagerManager(ManagerMembers managerMembers) {
        try {
            managerMembersManager.deleteManagerManager(managerMembers);
            allManagerMembers.remove(managerMembers);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteManagerTeam(ManagerMembers managerMembers) {
        try {
            managerMembersManager.deleteManagerMember(managerMembers);
            allManagerMembers.remove(managerMembers);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Creates new Personnel data using the provided information and adds it to the manager and the observable list.
     *
     * @param managerMembers The Personnel data to create.
     */
    public void createManagerMembers(ManagerMembers managerMembers) {
        try {
            // Create the Team data through the manager
            ManagerMembers m = managerMembersManager.createManagerMembers(managerMembers);
            // Add the created Team data to the observable list
            allManagerMembers.add(m);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
