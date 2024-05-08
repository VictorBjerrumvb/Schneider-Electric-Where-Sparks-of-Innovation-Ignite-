package gui.controller;

import be.CreateTeamMapping;
import be.Personnel;
import be.Team;
import gui.helperclases.ShowImageClass;
import gui.model.PersonnelModel;
import gui.model.TeamMappingModel;
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
            String salaryText = txtSalary.getText().trim();
            if (salaryText.isEmpty()) {
                // Salary field is empty, show an error message to the user
                // You can customize the error message based on your requirements
                // For example:
                showAlert("Salary field cannot be empty!");
                return;
            }

            try {
                // Attempt to parse the salary
                double salary = Double.parseDouble(salaryText);
                newPersonnel.setSalary(salary);

                // Continue with creating the personnel
                newPersonnel.setUsername(txtUsername.getText());
                if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    newPersonnel.setPassword(txtPassword.getText());
                } else {
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                    txtPassword.setPromptText("Password Didn't Match");
                    return;
                }

                // Database interaction to create personnel
                try {
                    // Call the method to create personnel in the model
                    Personnel p = personnelModel.createPersonnelWithReturn(newPersonnel);
                    Team nt = new Team();
                    nt.setName(txtSearchTeam.getText());
                    Team t = teamModel.createTeamWithReturn(nt);

                    TeamMappingModel teamMappingModel = new TeamMappingModel();
                    CreateTeamMapping createTeamMapping = new CreateTeamMapping();
                    createTeamMapping.setTeamId(t.getId());
                    createTeamMapping.setPersonnelId(p.getId());
                    teamMappingModel.createTeam(createTeamMapping);


                    // Optionally, you can show a success message to the user
                    // For example:
                    showAlert("Personnel created successfully!");

                    // Reset fields and update UI
                    setup();
                } catch (Exception e) {
                    // Handle database exception
                    // For example:
                    showAlert("Error creating personnel: " + e.getMessage());
                }
            } catch (NumberFormatException e) {
                // Error occurred while parsing the salary, show an error message to the user
                // For example:
                showAlert("Invalid salary format! Please enter a valid number.");
            }
        }
    }

    private void showAlert(String s) {
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
            txtUsername.setText(selectedPersonnel.getUsername());
            txtSalary.setText(String.valueOf(selectedPersonnel.getSalary()));
            TeamMappingModel teamMappingModel = new TeamMappingModel();
            Team team = teamMappingModel.getPersonnelTeam(selectedPersonnel);
            //if (team != null) {
                //txtSearchTeam.setText(team.getName());
            //}
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
    private void handleKeyPressedPersonnel(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Personnel personnel = listPersonnel.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (personnel != null) {
                personnelModel.deletePersonnel(personnel);
                listPersonnel.setItems(personnelModel.getAllPersonnel());
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
