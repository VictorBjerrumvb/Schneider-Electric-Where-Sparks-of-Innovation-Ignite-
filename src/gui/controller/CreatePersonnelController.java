package gui.controller;
import javafx.scene.control.TextField;
import be.CreateTeamMapping;
import be.ManagerMembers;
import be.Personnel;
import gui.helperclases.ShowImageClass;
import gui.model.ManagerMembersModel;
import gui.model.PersonnelModel;
import gui.model.TeamMappingModel;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.scene.paint.Color;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreatePersonnelController {
    @FXML
    private TextField txtTeam1Percentage,txtTeam2Percentage,txtTeam3Percentage;
    @FXML
    private Map<String, Double> teamTimeAllocations = new HashMap<>();
    @FXML
    private MenuButton btnProfile;
    @FXML
    private ImageView imgLogo, imgProfileIcon;
    @FXML
    private MFXButton btnAdministrator, btnManager, btnProgrammer, btnCreate;
    @FXML
    private ListView<Personnel> listPersonnel; // Specify the type parameter for ListView
    @FXML
    private MFXTextField txtUsername, txtPassword, txtConfirmPassword, txtSalary, txtRole;

    private Personnel newPersonnel = new Personnel(),selectedPersonnel = new Personnel(),operator;
    private ShowImageClass showImageClass = new ShowImageClass();
    private PersonnelModel personnelModel;
    private String newAdministrator = "New Administrator" ,newManager = "New Manager",newProgrammer = "New Programmer",updateAdministrator = "Update Administrator",
            updateManager = "Update Manager",updateProgrammer = "Update Programmer",changeAdministrator = "Change To a  Administrator",changeManager = "Change To a  Manager",
            changeProgrammer = "Change To a Programmer",create = "Create",update = "Update";


    public CreatePersonnelController() {
        try {
            personnelModel = new PersonnelModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setup() {
        listPersonnel.setItems(personnelModel.getAllPersonnel());
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtSalary.setText("");
        txtUsername.setText("");
        txtRole.setText("");

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


    @FXML
    private void handleLogo(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = imgLogo.getScene(); // Get the current scene
            scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
            MainViewController controller = loader.getController();
            controller.setup(operator);
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
        // Remove red outline style from this button
        btnAdministrator.getStyleClass().remove("red-outline");
        btnManager.getStyleClass().remove("red-outline");
        btnProgrammer.getStyleClass().remove("red-outline");
    }

    @FXML
    private void handleManager(ActionEvent actionEvent) {
        newPersonnel.setRoleId(2);
        // Remove red outline style from this button
        btnAdministrator.getStyleClass().remove("red-outline");
        btnManager.getStyleClass().remove("red-outline");
        btnProgrammer.getStyleClass().remove("red-outline");
    }

    @FXML
    private void handleProgrammer(ActionEvent actionEvent) {
        newPersonnel.setRoleId(3);
        // Remove red outline style from this button
        btnAdministrator.getStyleClass().remove("red-outline");
        btnManager.getStyleClass().remove("red-outline");
        btnProgrammer.getStyleClass().remove("red-outline");
    }


    @FXML
    private void handleCreate(ActionEvent actionEvent) {
        if (newPersonnel.getRoleId() == 0) {
            btnAdministrator.getStyleClass().add("red-outline");
            btnManager.getStyleClass().add("red-outline");
            btnProgrammer.getStyleClass().add("red-outline");
            return;
        }

        if (btnCreate.getText().equals(create)) {
            String salaryText = txtSalary.getText().trim();
            if (salaryText.isEmpty()) {
                // This block of code will only execute if the salary text field is empty,
                // but it seems like you want to set the team time allocations regardless of the salary.
                // You should remove this condition so that the team time allocations are always set.
                double team1Percentage = Double.parseDouble(txtTeam1Percentage.getText());
                double team2Percentage = Double.parseDouble(txtTeam2Percentage.getText());
                double team3Percentage = Double.parseDouble(txtTeam3Percentage.getText());

                // Validate that the total percentage equals 100
                if (team1Percentage + team2Percentage + team3Percentage != 100) {
                    showAlert("Total percentage must equal 100%");
                    return;
                }

                // Set time allocations for each team
                teamTimeAllocations.put("Team 1", team1Percentage);
                teamTimeAllocations.put("Team 2", team2Percentage);
                teamTimeAllocations.put("Team 3", team3Percentage);
            }
            if (txtRole.getText().equals("")) {
                txtRole.setPromptText("Please Fill in a role");
            }
            else {
                try {
                    // Attempt to parse the salary
                    double salary = Double.parseDouble(salaryText);
                    newPersonnel.setSalary(salary);
                    newPersonnel.setRole(txtRole.getText());

                    // Continue with creating the personnel
                    newPersonnel.setUsername(txtUsername.getText());
                    if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                        newPersonnel.setPassword(BCrypt.hashpw(txtPassword.getText(),BCrypt.gensalt()));
                    } else {
                        txtPassword.setText("");
                        txtConfirmPassword.setText("");
                        txtPassword.setPromptText("Password Didn't Match");
                        return;
                    }

                    // Database interaction to create personnel
                    try {
                        // Call the method to create personnel in the model
                        personnelModel.createPersonnel(newPersonnel);

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
        if (btnCreate.getText().equals(update)) {
            String salaryText = txtSalary.getText().trim();
            if (salaryText.isEmpty()) {
                // This block of code will only execute if the salary text field is empty,
                // but it seems like you want to set the team time allocations regardless of the salary.
                // You should remove this condition so that the team time allocations are always set.
                double team1Percentage = Double.parseDouble(txtTeam1Percentage.getText());
                double team2Percentage = Double.parseDouble(txtTeam2Percentage.getText());
                double team3Percentage = Double.parseDouble(txtTeam3Percentage.getText());

                // Validate that the total percentage equals 100
                if (team1Percentage + team2Percentage + team3Percentage != 100) {
                    showAlert("Total percentage must equal 100%");
                    return;
                }

                // Set time allocations for each team
                teamTimeAllocations.put("Team 1", team1Percentage);
                teamTimeAllocations.put("Team 2", team2Percentage);
                teamTimeAllocations.put("Team 3", team3Percentage);
            }
            if (txtRole.getText().equals("")) {
                txtRole.setPromptText("Please Fill in a role");
            }
            else {
                try {
                    // Attempt to parse the salary
                    double salary = Double.parseDouble(salaryText);
                    selectedPersonnel.setSalary(salary);
                    selectedPersonnel.setRole(txtRole.getText());

                    // Continue with creating the personnel
                    selectedPersonnel.setUsername(txtUsername.getText());
                    if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                        selectedPersonnel.setPassword(BCrypt.hashpw(txtPassword.getText(),BCrypt.gensalt()));
                    } else {
                        txtPassword.setText("");
                        txtConfirmPassword.setText("");
                        txtPassword.setPromptText("Password Didn't Match");
                        return;
                    }

                    // Database interaction to create personnel
                    try {
                        // Call the method to create personnel in the model
                        personnelModel.updatePersonnel(selectedPersonnel);

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
            selectedPersonnel = listPersonnel.getSelectionModel().getSelectedItem();
            txtUsername.setText(selectedPersonnel.getUsername());
            txtSalary.setText(String.valueOf(selectedPersonnel.getSalary()));
            btnCreate.setText(update);
            txtRole.setText(selectedPersonnel.getRole());
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
            TeamMappingModel teamMappingModel = new TeamMappingModel();
            ManagerMembersModel managerMembersModel = new ManagerMembersModel();
            Personnel personnel = listPersonnel.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (personnel != null) {
                CreateTeamMapping createTeamMapping = new CreateTeamMapping();
                createTeamMapping.setPersonnelId(personnel.getId());
                ManagerMembers managerMembers = new ManagerMembers();
                managerMembers.setPersonnelId(personnel.getId());
                managerMembers.setManagerId(personnel.getId());
                if (managerMembers.getPersonnelId() != 0) {
                    managerMembersModel.deleteManagerMembers(managerMembers);
                }
                if (managerMembers.getManagerId() != 0) {
                    managerMembersModel.deleteManagerManager(managerMembers);
                }
                if (createTeamMapping.getPersonnelId() != 0) {
                    teamMappingModel.deleteTeamMappingWithPersonnelId(createTeamMapping);
                }
                personnelModel.deletePersonnel(personnel);

                listPersonnel.getItems().remove(personnel);
            }
        }
    }
}
