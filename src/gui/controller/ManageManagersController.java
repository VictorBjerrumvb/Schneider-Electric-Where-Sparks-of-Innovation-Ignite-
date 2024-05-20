package gui.controller;

import be.CreateTeamMapping;
import be.ManagerMembers;
import be.Personnel;
import be.Team;
import dal.db.DataAccessException;
import gui.helperclases.ShowImageClass;
import gui.model.ManagerMembersModel;
import gui.model.PersonnelModel;
import gui.model.TeamModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.io.IOException;

public class ManageManagersController {
    public Label lblManagerTeams1;
    public ListView listProcentTeams;
    @FXML
    private ImageView imgLogo, imgProfileIcon;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private TextField txtSearch;
    @FXML
    private MFXButton btnReload;
    @FXML
    private ListView listManagers, listPersonnel, listManagerMembers, listPersonnelManagers ,listManagerTeams ,listTeams;
    @FXML
    private Label lblManagerMembers, lblSelectedPersonnel,lblManagerTeams;


    private final ShowImageClass showImageClass = new ShowImageClass();
    private final PersonnelModel personnelModel;
    private Personnel operator, selectedPersonnel = new Personnel(), dragPersonnel = new Personnel(), selectedManager = new Personnel();
    private Team selectedTeam = new Team(), dragTeam = new Team();
    private final String stringManagerMembers = "Personnel That The Selected Manager Manage";
    private final String stringSelectedPersonnel = "Managers that the Selected Personnel Responds to";
    private final String stringManagerTeams = "Teams That The Selected Manager Manage";
    private final ManagerMembersModel managerMembersModel;
    private final TeamModel teamModel;

    public ManageManagersController() {
        // Initialize models
        personnelModel = new PersonnelModel();
        managerMembersModel = new ManagerMembersModel();
        teamModel = new TeamModel();
    }

    public void setup() {
        // Populate list views and set initial text and labels
        for (Personnel p : personnelModel.getAllPersonnel()) {
            if (p.getRoleId() == 2) {
                listManagers.getItems().add(p);
            }
            if (p.getRoleId() == 3){
                listPersonnel.getItems().add(p);
            }
        }
        listTeams.setItems(teamModel.getAllTeams());
        lblSelectedPersonnel.setText(stringSelectedPersonnel);

        // Add listener to handle search in text field
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch();
        });
    }

    private void handleSearch() {
        // Get the search query
        String query = txtSearch.getText().toLowerCase().trim();

        // Filter personnel based on search query
        ObservableList<Personnel> filteredPersonnel = personnelModel.getAllPersonnel().filtered(personnel ->
                personnel.getUsername().toLowerCase().contains(query) ||
                        personnel.getRole().toLowerCase().contains(query)
        );
        listPersonnel.setItems(filteredPersonnel);
        listManagers.setItems(filteredPersonnel);
    }

    /**
     * Sets the current operator (personnel) of the application.
     * Updates the profile button text with operator's username and sets the profile image.
     * @param operator The personnel to set as the operator.
     */
    public void setOperator(Personnel operator) {
        this.operator = operator;
        // Set profile button text to operator's username
        btnProfile.setText(operator.getUsername());
        // Set profile image to operator's picture or default image if not available
        if (operator.getPicture() != null && !operator.getPicture().isEmpty()) {
            imgProfileIcon.setImage(showImageClass.showImage(operator.getPicture()));
        } else {
            imgProfileIcon.setImage(showImageClass.showImage("profileimages/image.png"));
        }
    }


    /**
     * Handles the action of clicking on the application logo.
     * @param mouseEvent The mouse event triggered by the click.
     */
    @FXML
    private void handleLogo(MouseEvent mouseEvent) {
        try {
            // Navigate to the main view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = imgLogo.getScene();
            scene.setRoot(secondWindow);
            MainViewController controller = loader.getController();
            controller.setup(operator);
            controller.setOperator(operator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of clicking on the profile button.
     * @param actionEvent The action event triggered by the click.
     */
    @FXML
    private void handleProfile(ActionEvent actionEvent) {
        try {
            // Navigate to the personnel view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonnelView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = btnProfile.getScene();
            scene.setRoot(secondWindow);
            PersonnelController controller = loader.getController();
            controller.setup();
            controller.setOperator(operator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of logging out.
     * @param actionEvent The action event triggered by the click.
     */
    @FXML
    private void handleLogout(ActionEvent actionEvent) {
        try {
            // Navigate to the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = btnProfile.getScene();
            scene.setRoot(secondWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of reloading the view.
     * @param actionEvent The action event triggered by the click.
     * @throws IOException Thrown if there is an issue loading the view.
     */
    @FXML
    private void handleReload(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageManagersView.fxml"));
        Parent secondWindow = loader.load();
        Scene scene = btnReload.getScene(); // Get the current scene
        scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
        ManageManagersController controller = loader.getController();
        controller.setup();
        controller.setOperator(operator);
    }

    @FXML
    private void handleSelectedManager(MouseEvent mouseEvent) throws DataAccessException {
        selectedManager = (Personnel) listManagers.getSelectionModel().getSelectedItem();
        listManagerMembers.setItems(managerMembersModel.getAllManagerMembers(selectedManager));
        listManagerTeams.setItems(managerMembersModel.getAllManagerTeams(selectedManager));
        lblManagerMembers.setText(stringManagerMembers + " | " + selectedManager.getUsername());
        lblManagerTeams.setText(stringManagerTeams + " | " + selectedManager.getUsername());
    }

    @FXML
    private void handleOnDragDetectedPersonnel(MouseEvent mouseEvent) {
        Personnel selectedPersonnel = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();
        if (selectedPersonnel != null) {
            // Set dragPersonnel to the selected Personnel object
            dragPersonnel = selectedPersonnel;
            Dragboard db = listPersonnel.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, "personnel");
            db.setContent(content);
            mouseEvent.consume();
        }
    }

    @FXML
    private void handleSelectedPersonnel(MouseEvent mouseEvent) {
        selectedPersonnel = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();
        listPersonnelManagers.setItems(managerMembersModel.getPersonnelManagers(selectedPersonnel));
        lblSelectedPersonnel.setText("Managers that " + selectedPersonnel.getUsername() + " Reports to");
    }

    @FXML
    private void handleOnDragDroppedManagerMembers(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString() && db.getString().equals("personnel")) {
            // Use dragPersonnel instead of retrieving from Dragboard
            Personnel personnel = dragPersonnel;
            ManagerMembers managerMembers = new ManagerMembers();
            managerMembers.setManagerId(selectedManager.getId());
            managerMembers.setPersonnelId(personnel.getId());
            managerMembersModel.createManagerMembers(managerMembers);

            // Update the items of listTeamMembers
            listManagerMembers.getItems().add(personnel);

            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    @FXML
    private void handleOnDragOverManagerMembers(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != listManagerMembers && dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }

    @FXML
    private void handleManagerMembersKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Personnel personnel = (Personnel) listManagerMembers.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (personnel != null) {
                ManagerMembers managerMembers = new ManagerMembers();
                managerMembers.setPersonnelId(personnel.getId());
                managerMembers.setManagerId(selectedManager.getId());
                managerMembersModel.deleteManagerMembers(managerMembers);
                listManagerMembers.getItems().remove(personnel);
            }
        }
    }

    @FXML
    private void handlePersonnelManagers(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Personnel personnel = (Personnel) listPersonnelManagers.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (personnel != null) {
                ManagerMembers managerMembers = new ManagerMembers();
                managerMembers.setPersonnelId(selectedPersonnel.getId());
                managerMembers.setManagerId(personnel.getId());
                managerMembersModel.deleteManagerMembers(managerMembers);
                listPersonnelManagers.getItems().remove(personnel);
            }
        }
    }

    public void handleOnDragDetectedTeam(MouseEvent mouseEvent) {
        Team selectedItem = (Team) listTeams.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Set dragPersonnel to the selected Personnel object
            dragTeam = selectedItem;
            Dragboard db = listTeams.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, "personnel");
            db.setContent(content);
            mouseEvent.consume();
        }
    }

    public void handleSelectedTeam(MouseEvent mouseEvent) {
        selectedTeam = (Team) listTeams.getSelectionModel().getSelectedItem();
        listPersonnelManagers.setItems(managerMembersModel.getTeamManagers(selectedTeam));
        lblSelectedPersonnel.setText("Managers that " + selectedTeam.getName() + " Reports to");
    }

    public void handleOnDragDroppedManagerTeams(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString() && db.getString().equals("personnel")) {
            // Use dragPersonnel instead of retrieving from Dragboard
            Team team = dragTeam;
            ManagerMembers managerMembers = new ManagerMembers();
            managerMembers.setManagerId(selectedManager.getId());
            managerMembers.setTeamId(team.getId());
            managerMembersModel.createManagerMembers(managerMembers);

            // Update the items of listTeamMembers
            listManagerTeams.getItems().add(team);

            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    public void handleOnDragOverManagerTeams(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != listManagerTeams && dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }

    public void handleManagerTeamsKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Team team = (Team) listManagerTeams.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (team != null) {
                ManagerMembers managerMembers = new ManagerMembers();
                managerMembers.setTeamId(team.getId());
                managerMembers.setManagerId(selectedManager.getId());
                managerMembersModel.deleteManagerTeam(managerMembers);
                listManagerTeams.getItems().remove(team);
            }
        }
    }

    public void handleOnDroppedProcentTeams(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString() && db.getString().equals("personnel")) {
            // Use dragPersonnel instead of retrieving from Dragboard
            Team team = dragTeam;
            ManagerMembers managerMembers = new ManagerMembers();
            managerMembers.setManagerId(selectedManager.getId());
            managerMembers.setTeamId(team.getId());
            managerMembersModel.createManagerMembers(managerMembers);

            // Update the items of listTeamMembers
            listProcentTeams.getItems().add(team);

            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();

    }

    public void handleOnDragOverProcentTeams(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != listProcentTeams && dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }
}
