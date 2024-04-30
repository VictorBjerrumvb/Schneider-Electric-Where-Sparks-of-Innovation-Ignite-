package gui.controller;

import be.Personal;
import gui.helperclases.ShowImageClass;
import gui.model.PersonalModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreatePersonalController {
    public MFXTextField txtSalary;
    public MFXTextField txtConfirmPassword;
    public MenuButton btnProfile;
    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgProfileIcon;
    @FXML
    private MFXButton btnAdministrator;
    @FXML
    private MFXButton btnManager;
    @FXML
    private MFXButton btnProgrammer;
    @FXML
    private ListView listPersonal;
    @FXML
    private MFXTextField txtUsername;
    @FXML
    private MFXTextField txtPassword;
    @FXML
    private MFXButton btnCreate;
    @FXML
    private MFXButton btnCancel;

    private Personal newPersonal = new Personal();
    private Personal selectedPersonal = new Personal();
    private Personal operator;
    private ShowImageClass showImageClass = new ShowImageClass();
    private PersonalModel personalModel;
    private String newAdministrator = "New Administrator";
    private String newManager = "New Manager";
    private String newProgrammer = "New Programmer";
    private String updateAdministrator = "Update Administrator";
    private String updateManager = "Update Manager";
    private String updateProgrammer = "Update Programmer";
    private String changeAdministrator = "Change" + selectedPersonal.getUsername() + " To a  Administrator";
    private String changeManager = "Change" + selectedPersonal.getUsername() + " To a  Manager";
    private String changeProgrammer = "Change" + selectedPersonal.getUsername() + " To a Programmer";
    private String create = "Create";
    private String update = "Update";


    public CreatePersonalController() {
        try {
            personalModel = new PersonalModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setup() {
        listPersonal.setItems(personalModel.getAllPersonal());

        if (selectedPersonal.getId() != 0) {
            btnCreate.setText(update);
            if (selectedPersonal.getRoleId() == 1) {
                btnAdministrator.setText(updateAdministrator);
                btnManager.setText(changeManager);
                btnProgrammer.setText(changeProgrammer);
            }
            if (selectedPersonal.getRoleId() == 2) {
                btnManager.setText(updateManager);
                btnAdministrator.setText(changeAdministrator);
                btnProgrammer.setText(changeProgrammer);
            }
            if (selectedPersonal.getRoleId() == 3) {
                btnProgrammer.setText(updateProgrammer);
                btnAdministrator.setText(changeAdministrator);
                btnManager.setText(changeManager);
            }
        }
        if (selectedPersonal.getId() == 0) {
            btnAdministrator.setText(newAdministrator);
            btnManager.setText(newManager);
            btnProgrammer.setText(newProgrammer);
            btnCreate.setText(create);
        }
    }
    public void setOperator(Personal operator) {
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
    }

    @FXML
    private void handleProfile(ActionEvent actionEvent) {
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {
    }

    @FXML
    private void handleAdministrator(ActionEvent actionEvent) {
        newPersonal.setRoleId(1);
        newPersonal.setRole("Admin");
    }

    @FXML
    private void handleManager(ActionEvent actionEvent) {
        newPersonal.setRoleId(2);
        newPersonal.setRole("Manager");
    }

    @FXML
    private void handleProgrammer(ActionEvent actionEvent) {
        newPersonal.setRoleId(3);
        newPersonal.setRole("Programmer");
    }

    @FXML
    private void handleCreate(ActionEvent actionEvent) {
        if (newPersonal.getRoleId() == 0) {
            if (btnCreate.getText().equals(create)) {
                newPersonal.setSalary(Double.parseDouble(txtSalary.getText()));
                newPersonal.setUsername(txtUsername.getText());
                if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    newPersonal.setPassword(txtPassword.getText());
                }
                if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                    txtPassword.setPromptText("Password Didnt Match");
                }
                try {
                    personalModel.createPersonal(newPersonal);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (btnCreate.getText().equals(update)) {
            if (!txtPassword.getText().isEmpty()) {
                if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    selectedPersonal.setPassword(txtPassword.getText());
                }
            }
            if (!txtUsername.getText().isEmpty()) {
                selectedPersonal.setUsername(txtUsername.getText());
            }
            if (!txtSalary.getText().isEmpty()) {
                selectedPersonal.setSalary(Double.parseDouble(txtSalary.getText()));
            }
            try {
                personalModel.updatePersonal(selectedPersonal);
                setup();
                selectedPersonal = new Personal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        selectedPersonal = new Personal();
        setup();
    }

    @FXML
    private void handleSelectedPersonal(MouseEvent mouseEvent) {
        selectedPersonal = (Personal) listPersonal.getSelectionModel().getSelectedItem();
        btnCreate.setText(update);
        if (selectedPersonal.getRoleId() == 1) {
            btnAdministrator.setText(updateAdministrator);
            btnManager.setText(changeManager);
            btnProgrammer.setText(changeProgrammer);
        }
        if (selectedPersonal.getRoleId() == 2) {
            btnManager.setText(updateManager);
            btnAdministrator.setText(changeAdministrator);
            btnProgrammer.setText(changeProgrammer);
        }
        if (selectedPersonal.getRoleId() == 3) {
            btnProgrammer.setText(updateProgrammer);
            btnAdministrator.setText(changeAdministrator);
            btnManager.setText(changeManager);
        }
    }
}
