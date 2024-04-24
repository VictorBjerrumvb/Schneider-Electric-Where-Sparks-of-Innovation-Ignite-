package gui.controller;

import be.Personal;
import bll.PersonalManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginViewController {

    @FXML
    private MFXPasswordField txtFieldPassword;
    @FXML
    private MFXTextField txtFieldUsername;
    @FXML
    private MFXButton btnLogin1;
    @FXML
    private MFXButton btnLogin;

    private PersonalManager personalManager = new PersonalManager();

    @FXML
    private void handleLogin(ActionEvent actionEvent) throws Exception {
        String userName = txtFieldUsername.getText();
        String userPassword = txtFieldPassword.getText();


        // here the method is run that validates the user credentials.
        Personal personalLogged = personalManager.validatePersonal(userName, userPassword);
        if (!(personalLogged == null)) {
            // if it matches you are logged in
            if (personalLogged.getRoleId() == 1) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle("Admin Page");
                PersonalController controller = loader.getController();
                controller.setup();
                controller.setOperator(personalLogged);
                Scene scene = new Scene(secondWindow);
                newStage.setScene(scene);

                // Check if the current window is maximized
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                if (currentStage.isMaximized()) {
                    newStage.setMaximized(true); // Maximize the new window
                }

                newStage.show();
                currentStage.hide();

            }
            if (personalLogged.getRoleId() == 2) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KoordinatorPage.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle("Koordinator Page");
                Scene scene = new Scene(secondWindow);
                newStage.setScene(scene);

                // Check if the current window is maximized
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                if (currentStage.isMaximized()) {
                    newStage.setMaximized(true); // Maximize the new window
                }

                newStage.show();
                currentStage.hide();

            }
        }else {
            txtFieldUsername.setText("");
            txtFieldPassword.setText("");
            txtFieldUsername.setPromptText("Incorrect Password or Username");
        }
    }

    @FXML
    private void handleForgotPassword(ActionEvent actionEvent) {
    }
}
