package gui.controller;

import be.CreateTeamMapping;
import be.ManagerMembers;
import be.Personnel;
import be.Team;
import dal.db.DataAccessException;
import gui.helperclases.ShowImageClass;
import gui.model.ManagerMembersModel;
import gui.model.PersonnelModel;
import gui.model.TeamMappingModel;
import gui.model.TeamModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
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

public class CreateTeamController {
    @FXML
    private ImageView imgLogo, imgProfileIcon;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private Label lblCreateTeam, lblTeamMembers, lblSelectedPersonnel;
    @FXML
    private MFXTextField txtCreateTeam;
    @FXML
    private MFXButton btnCreateTeam, btnReload;
    @FXML
    private ListView listTeam, listPersonnel, listTeamMembers, listPersonnelTeams;
    @FXML
    private TextField txtSearch;

    private Personnel operator,selectedPersonnel = new Personnel(),dragPersonnel = new Personnel();
    private ShowImageClass showImageClass = new ShowImageClass();
    private TeamModel teamModel;
    private PersonnelModel personnelModel;
    private TeamMappingModel teamMappingModel;
    private Team selectedTeam = new Team();
    private String stringCreate = "Create New Team Enter Name Below",StringUpdate = "Update the current Team name | Click me to make a new Team again",
            stringCreateTeam = "Create New Team",StringUpdateTeam = "Update Name",stringTeamMembers = "Selected Team Members",stringSelectedPersonnel = "Teams the Selected Personnel is part of";



    /**
     * Initializes the controller with necessary models and sets up initial state.
     */
    public CreateTeamController() {
        // Initialize models
        teamModel = new TeamModel();
        personnelModel = new PersonnelModel();
        teamMappingModel = new TeamMappingModel();
    }

    /**
     * Sets up the initial state of the controller.
     */
    public void setup() {
        // Populate list views and set initial text and labels
        listPersonnel.setItems(personnelModel.getAllPersonnel());
        listTeam.setItems(teamModel.getAllTeams());
        txtCreateTeam.setText("");
        btnCreateTeam.setText(stringCreateTeam);
        lblCreateTeam.setText(stringCreate);
        lblTeamMembers.setText(stringTeamMembers);
        lblSelectedPersonnel.setText(stringSelectedPersonnel);

        // Add listener to handle search in text field
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch();
        });
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
        if (operator != null && operator.getPicture() != null && !operator.getPicture().isEmpty()) {
            imgProfileIcon.setImage(showImageClass.showImage(operator.getPicture()));
        } else {
            imgProfileIcon.setImage(showImageClass.showImage("profileimages/image.png"));
        }
    }


    /**
     * Handles the search functionality when text in the search field changes.
     */
    private void handleSearch() {
        // If update mode is active, reset it
        if (lblCreateTeam.getText().equals(StringUpdate)) {
            lblCreateTeam.setText(stringCreate);
            btnCreateTeam.setText(stringCreateTeam);
            txtCreateTeam.setText("");
        }

        // Get the search query
        String query = txtSearch.getText().toLowerCase().trim();

        // Filter personnel based on search query
        ObservableList<Personnel> filteredPersonnel = personnelModel.getAllPersonnel().filtered(personnel ->
                personnel.getUsername().toLowerCase().contains(query) ||
                        personnel.getRole().toLowerCase().contains(query)
        );
        listPersonnel.setItems(filteredPersonnel);

        // Filter teams based on search query
        ObservableList<Team> filteredTeams = teamModel.getAllTeams().filtered(team ->
                team.getName().toLowerCase().contains(query)
        );
        listTeam.setItems(filteredTeams);
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
     * Handles the key press event when a team is selected.
     * @param keyEvent The key event triggered.
     */
    @FXML
    private void handleKeyPressedTeam(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Team team = (Team) listTeam.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (team != null) {
                teamModel.deleteTeam(team);
                listTeam.setItems(teamModel.getAllTeams());
            }
        }
    }

    /**
     * Handles the action of selecting a team.
     * @param mouseEvent The mouse event triggered by the selection.
     * @throws DataAccessException Thrown if there is an issue accessing data.
     */
    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) throws DataAccessException {
        selectedTeam = (Team) listTeam.getSelectionModel().getSelectedItem();
        txtCreateTeam.setText(selectedTeam.getName());
        lblCreateTeam.setText(StringUpdate);
        btnCreateTeam.setText(StringUpdateTeam);
        listTeamMembers.setItems(teamMappingModel.getAllTeamMembers(selectedTeam));
        lblTeamMembers.setText(stringTeamMembers + " | " + selectedTeam.getName());

    }

    /**
     * Handles the drag event when personnel is dragged.
     * @param mouseEvent The mouse event triggered by the drag.
     */
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

    /**
     * Handles the action of selecting personnel.
     * @param mouseEvent The mouse event triggered by the selection.
     */
    @FXML
    private void handleSelectedPersonnel(MouseEvent mouseEvent) {
        selectedPersonnel = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();
        listPersonnelTeams.setItems(teamMappingModel.getPersonnelTeam(selectedPersonnel));
        lblSelectedPersonnel.setText(selectedPersonnel.getUsername() + " | Is apart of this(these) teams");
    }

    /**
     * Handles the drag event when personnel is dropped onto team members.
     * @param dragEvent The drag event triggered by the drop.
     * @throws DataAccessException Thrown if there is an issue accessing data.
     */
    @FXML
    private void handleOnDragDroppedTeamMembers(DragEvent dragEvent) throws DataAccessException {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString() && db.getString().equals("personnel")) {
            // Use dragPersonnel instead of retrieving from Dragboard
            Personnel personnel = dragPersonnel;
            CreateTeamMapping createTeamMapping = new CreateTeamMapping();
            createTeamMapping.setTeamId(selectedTeam.getId());
            createTeamMapping.setPersonnelId(personnel.getId());
            teamMappingModel.createTeamMapping(createTeamMapping);

            // Update the items of listTeamMembers
            listTeamMembers.getItems().add(personnel);

            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    /**
     * Handles the drag event when personnel is dragged over team members.
     * @param dragEvent The drag event triggered.
     */
    @FXML
    private void handleOnDragOverTeamMembers(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != listTeamMembers && dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }

    /**
     * Handles the key press event when a team member is selected.
     * @param keyEvent The key event triggered.
     * @throws DataAccessException Thrown if there is an issue accessing data.
     */
    @FXML
    private void handleTeamMembersKeyPressed(KeyEvent keyEvent) throws DataAccessException {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Personnel personnel = (Personnel) listTeamMembers.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (personnel != null) {
                CreateTeamMapping createTeamMapping = new CreateTeamMapping();
                createTeamMapping.setPersonnelId(personnel.getId());
                createTeamMapping.setTeamId(selectedTeam.getId());
                teamMappingModel.deleteTeamMappingWithTeamNPersonnelId(createTeamMapping);
                listTeamMembers.getItems().remove(personnel);
            }
        }
    }

    /**
     * Handles the key press event when personnel teams are selected.
     * @param keyEvent The key event triggered.
     */
    @FXML
    private void handlePersonnelTeams(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Team team = (Team) listPersonnelTeams.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (team != null) {
                CreateTeamMapping createTeamMapping = new CreateTeamMapping();
                ManagerMembersModel managerMembersModel = new ManagerMembersModel();

                createTeamMapping.setPersonnelId(selectedPersonnel.getId());
                createTeamMapping.setTeamId(team.getId());

                ManagerMembers managerMembers = new ManagerMembers();
                managerMembers.setTeamId(team.getId());
                if (createTeamMapping.getTeamId() != 0) {
                    teamMappingModel.deleteTeamMappingWithTeamNPersonnelId(createTeamMapping);
                }
                if (managerMembers.getTeamId() != 0) {
                    managerMembersModel.deleteManagerTeam(managerMembers);
                }
                teamModel.deleteTeam(team);
                listPersonnelTeams.getItems().remove(team);
                setup();
            }
        }
    }

    /**
     * Handles the action of creating or updating a team.
     * @param actionEvent The action event triggered by the click.
     */
    @FXML
    private void handleCreateTeam(ActionEvent actionEvent) {
        if (btnCreateTeam.getText().equals(stringCreateTeam)) {
            Team t = new Team();
            t.setName(txtCreateTeam.getText());
            teamModel.createTeam(t);
            setup();
        }
        if (btnCreateTeam.getText().equals(StringUpdateTeam)) {
            selectedTeam.setName(txtCreateTeam.getText());
            teamModel.updateTeam(selectedTeam);
            setup();
        }
    }

    /**
     * Handles the action of reloading the view.
     * @param actionEvent The action event triggered by the click.
     * @throws IOException Thrown if there is an issue loading the view.
     */
    @FXML
    private void handleReload(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTeamView.fxml"));
        Parent secondWindow = loader.load();
        Scene scene = btnReload.getScene();
        scene.setRoot(secondWindow);
        CreateTeamController controller = loader.getController();
        controller.setup();
        controller.setOperator(operator);
    }
}
