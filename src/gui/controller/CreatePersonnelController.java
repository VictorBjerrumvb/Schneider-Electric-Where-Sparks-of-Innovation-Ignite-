package gui.controller;

import be.Personnel;
import gui.helperclases.ShowImageClass;
import gui.model.PersonnelModel;
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
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CreatePersonnelController {
    @FXML
    private MenuButton btnProfile;
    @FXML
    private ListView listTeam;
    @FXML
    private ImageView imgLogo, imgProfileIcon;
    @FXML
    private MFXButton btnAdministrator, btnManager, btnProgrammer, btnCancel, btnCreate;
    @FXML
    private ListView listPersonnel;
    @FXML
    private MFXTextField txtUsername, txtPassword, txtConfirmPassword, txtSalary, txtSearchTeam;

    private Personnel newPersonnel = new Personnel();
    private Personnel selectedPersonnel = new Personnel();
    private Personnel operator;
    private ShowImageClass showImageClass = new ShowImageClass();
    private PersonnelModel personnelModel;
    private String newAdministrator = "New Administrator";
    private String newManager = "New Manager";
    private String newProgrammer = "New Programmer";
    private String updateAdministrator = "Update Administrator";
    private String updateManager = "Update Manager";
    private String updateProgrammer = "Update Programmer";
    private String changeAdministrator = "Change" + selectedPersonnel.getUsername() + " To a  Administrator";
    private String changeManager = "Change" + selectedPersonnel.getUsername() + " To a  Manager";
    private String changeProgrammer = "Change" + selectedPersonnel.getUsername() + " To a Programmer";
    private String create = "Create";
    private String update = "Update";


    public CreatePersonnelController() {
        try {
            personnelModel = new PersonnelModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setup() {
        listPersonnel.setItems(personnelModel.getAllPersonnel());

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
            if (btnCreate.getText().equals(create)) {
                newPersonnel.setSalary(Double.parseDouble(txtSalary.getText()));
                newPersonnel.setUsername(txtUsername.getText());
                if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    newPersonnel.setPassword(txtPassword.getText());
                }
                if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                    txtPassword.setPromptText("Password Didnt Match");
                }
                try {
                    personnelModel.createPersonnel(newPersonnel);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (btnCreate.getText().equals(update)) {
            if (!txtPassword.getText().isEmpty()) {
                if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    selectedPersonnel.setPassword(txtPassword.getText());
                }
            }
            if (!txtUsername.getText().isEmpty()) {
                selectedPersonnel.setUsername(txtUsername.getText());
            }
            if (!txtSalary.getText().isEmpty()) {
                selectedPersonnel.setSalary(Double.parseDouble(txtSalary.getText()));
            }
            try {
                personnelModel.updatePersonnel(selectedPersonnel);
                setup();
                selectedPersonnel = new Personnel();
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        selectedPersonnel = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();
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
