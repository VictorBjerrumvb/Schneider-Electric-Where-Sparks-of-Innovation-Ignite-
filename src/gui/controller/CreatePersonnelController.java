package gui.controller;

import be.Personnel;
import be.Team;
import gui.helperclases.ShowImageClass;
import gui.model.PersonnelModel;
import gui.model.TeamModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CreatePersonnelController {
    @FXML
    private MenuButton btnProfile;
    @FXML
    private ListView<Team> listTeam; // Specify the type parameter for ListView
    @FXML
    private ImageView imgLogo, imgProfileIcon;
    @FXML
    private MFXButton btnAdministrator, btnManager, btnProgrammer, btnCancel, btnCreate;
    @FXML
    private ListView<Personnel> listPersonnel; // Specify the type parameter for ListView
    @FXML
    private MFXTextField txtUsername, txtPassword, txtConfirmPassword, txtSalary, txtSearchTeam;

    private Personnel newPersonnel = new Personnel();
    private Personnel selectedPersonnel = new Personnel();
    private Personnel operator;
    private ShowImageClass showImageClass = new ShowImageClass();
    private PersonnelModel personnelModel;
    private TeamModel teamModel;
    private String newAdministrator = "New Administrator";
    private String newManager = "New Manager";
    private String newProgrammer = "New Programmer";
    private String updateAdministrator = "Update Administrator";
    private String updateManager = "Update Manager";
    private String updateProgrammer = "Update Programmer";
    private String changeAdministrator = "Change To a  Administrator";
    private String changeManager = "Change To a  Manager";
    private String changeProgrammer = "Change To a Programmer";
    private String create = "Create";
    private String update = "Update";


    public CreatePersonnelController() {
        try {
            personnelModel = new PersonnelModel();
            teamModel = new TeamModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setup() {
        listPersonnel.setItems(personnelModel.getAllPersonnel());
        listTeam.setItems(teamModel.getAllTeams());
        txtSearchTeam.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtSalary.setText("");
        txtUsername.setText("");

        if (selectedPersonnel.getId() != 0) {
            btnCreate.setText(update);
            if (selectedPersonnel.getRoleId() == 1) {
                btnAdministrator.setText(updateAdministrator);
                btnManager.setText(changeManager);
                btnProgrammer.setText(changeProgrammer);
            }
            if (selectedPersonnel.getRoleId() == 2) {
                btnManager.setText(updateManager);
                btnAdministrator.setText(changeAdministrator);
                btnProgrammer.setText(changeProgrammer);
            }
            if (selectedPersonnel.getRoleId() == 3) {
                btnProgrammer.setText(updateProgrammer);
                btnAdministrator.setText(changeAdministrator);
                btnManager.setText(changeManager);
            }
        }
        if (selectedPersonnel.getId() == 0) {
            btnAdministrator.setText(newAdministrator);
            btnManager.setText(newManager);
            btnProgrammer.setText(newProgrammer);
            btnCreate.setText(create);
        }
    }

    public void setOperator(Personnel operator) {
        this.operator = operator;
        btnProfile.setText(operator.getUsername());
        if (operator != null && operator.getPicture() != null && !operator.getPicture().isEmpty()) {
            imgProfileIcon.setImage(showImageClass.showImage(operator.getPicture()));
        } else {
            imgProfileIcon.setImage(showImageClass.showImage("profileimages/image.png"));
        }
    }

    @FXML
    private void handleLogo(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = imgLogo.getScene(); // Get the current scene
            scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
            MainViewController controller = loader.getController();
            controller.setup();
            controller.setOperator(operator);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    private void handleProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonnelView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = btnProfile.getScene(); // Get the current scene
            scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
            PersonnelController controller = loader.getController();
            controller.setup();
            controller.setOperator(operator);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = btnProfile.getScene(); // Get the current scene
            scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    private void handleAdministrator(ActionEvent actionEvent) {
        newPersonnel.setRoleId(1);
        newPersonnel.setRole("Admin");
    }

    @FXML
    private void handleManager(ActionEvent actionEvent) {
        newPersonnel.setRoleId(2);
        newPersonnel.setRole("Manager");
    }

    @FXML
    private void handleProgrammer(ActionEvent actionEvent) {
        newPersonnel.setRoleId(3);
        newPersonnel.setRole("Programmer");
    }

    @FXML
    private void handleCreate(ActionEvent actionEvent) {
        if (newPersonnel.getRoleId() == 0) {
            // Role not selected, do nothing
            return;
        }

        if (btnCreate.getText().equals(create)) {
            newPersonnel.setSalary(Double.parseDouble(txtSalary.getText()));
            newPersonnel.setUsername(txtUsername.getText());
            if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                newPersonnel.setPassword(txtPassword.getText());
            } else {
                txtPassword.setText("");
                txtConfirmPassword.setText("");
                txtPassword.setPromptText("Password Didnt Match");
                return;
            }

            try {
                String searchTeamName = txtSearchTeam.getText();
                boolean teamExists = false;

                // Check if the team already exists
                for (Team t : listTeam.getItems()) { // Use listTeam.getItems() to iterate over items
                    if (t.getName().equals(searchTeamName)) {
                        teamExists = true;
                        break; // No need to continue searching if the team exists
                    }
                }

                // If the team doesn't exist, create a new one
                if (!teamExists) {
                    Team newTeam = new Team();
                    newTeam.setName(searchTeamName);
                    teamModel.createTeam(newTeam);
                }

                // Set the role ID based on the selected role
                newPersonnel.setRoleId(newPersonnel.getRoleId());

                // Create personnel regardless of whether the team existed or not
                personnelModel.createPersonnel(newPersonnel);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                setup();
            }
        }
    }
    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        selectedPersonnel = new Personnel();
        setup();
    }

    @FXML
    private void handleSelectedPersonnel(MouseEvent mouseEvent) {
        if (listPersonnel.getSelectionModel().getSelectedItem() != null) {
            selectedPersonnel = listPersonnel.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            btnCreate.setText(update);
            if (selectedPersonnel.getRoleId() == 1) {
                btnAdministrator.setText(updateAdministrator);
                btnManager.setText(changeManager);
                btnProgrammer.setText(changeProgrammer);
            }
            if (selectedPersonnel.getRoleId() == 2) {
                btnManager.setText(updateManager);
                btnAdministrator.setText(changeAdministrator);
                btnProgrammer.setText(changeProgrammer);
            }
            if (selectedPersonnel.getRoleId() == 3) {
                btnProgrammer.setText(updateProgrammer);
                btnAdministrator.setText(changeAdministrator);
                btnManager.setText(changeManager);
            }
        }
    }

    @FXML
    private void handleKeyPressedTeam(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Team team = listTeam.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (team != null) {
                teamModel.deleteTeam(team);
                listTeam.setItems(teamModel.getAllTeams());
            }
        }
    }

    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) {
        Team team = listTeam.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
        if (team != null) {
            txtSearchTeam.setText(team.getName());
        }
    }
}
