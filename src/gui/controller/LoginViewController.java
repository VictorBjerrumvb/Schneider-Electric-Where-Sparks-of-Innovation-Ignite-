package gui.controller;

import be.ManagerMembers;
import be.Personnel;
import be.Team;
import bll.PersonnelManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the login view.
 */
public class LoginViewController {

    // FXML elements injected from the corresponding FXML file
    @FXML
    private MFXPasswordField txtFieldPassword;
    @FXML
    private MFXTextField txtFieldUsername;
    @FXML
    private MFXButton btnLogin;
    @FXML
    private MFXButton btnForgotPassword;

    // Instance of PersonnelManager for handling Personnel-related operations
    private PersonnelManager personnelManager = new PersonnelManager();

    public LoginViewController() throws IOException {
    }

    /**
     * Event handler for the login button.
     *
     * @param actionEvent The action event generated by clicking the login button.
     * @throws Exception if an error occurs during login validation or view loading.
     */
    @FXML
    private void handleLogin(ActionEvent actionEvent) throws Exception {
        // Retrieve the username and password entered by the user
        String userName = txtFieldUsername.getText();
        String userPassword = txtFieldPassword.getText();

        // Validate the user credentials
        Personnel personnelLogged = personnelManager.validatePersonnel(userName, userPassword);

        if (personnelLogged != null) {
            // Load different views based on user role
            FXMLLoader loader;
            String title;

            loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            title = "Admin Page";

            Parent secondWindow = loader.load();
            MainViewController controller = loader.getController();
            controller.setup(personnelLogged);
            controller.setOperator(personnelLogged);
            Scene scene = new Scene(secondWindow);

            // Check if the current window is maximized
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(title);
            if (currentStage.isMaximized()) {
                newStage.setMaximized(true); // Maximize the new window
            }

            newStage.show();
            currentStage.hide();
        } else {
            // Display a message for incorrect password or username
            txtFieldUsername.setText("");
            txtFieldPassword.setText("");
            txtFieldUsername.setPromptText("Incorrect Password or Username");
        }
    }

    @FXML
    private void handlePasswordLoginKey(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleLogin(new ActionEvent());
        }
    }
}
