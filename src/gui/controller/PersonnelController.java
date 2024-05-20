package gui.controller;

import be.Personnel;
import gui.helperclases.ShowImageClass;
import gui.helperclases.WidgetsClass;
import gui.model.PersonnelModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller class for the Personnel view.
 */
public class PersonnelController {

    public MFXButton btnPersonalInfo;
    @FXML
    private Label lblProfileName;
    @FXML
    private ImageView imgLogo;
    @FXML
    private MFXTextField txtSearch;
    @FXML
    private MFXButton btnHome;
    @FXML
    private MFXButton btnPersonnelInfo;
    @FXML
    private MFXButton btnSecurity;
    @FXML
    private MFXButton btnPeopleAndSharing;
    @FXML
    private ImageView imgProfilePicture;
    @FXML
    private FlowPane flowPaneInformation;

    private ShowImageClass showImageClass = new ShowImageClass();
    private int textFieldCount = 0;
    private Personnel operator = new Personnel();
    private PersonnelModel personnelModel;
    private WidgetsClass widgetsClass = new WidgetsClass();

    /**
     * Setup method for initializing the Personnel view.
     */
    public void setup() {
        try {
            personnelModel = new PersonnelModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        flowPaneInformation.getChildren().clear();
        addButtonToFlowPane("Change Password");
    }

    /**
     * Sets the operator (logged-in user) for the Personnel view.
     *
     * @param operator The logged-in user.
     */
    public void setOperator(Personnel operator) {
        this.operator = operator;
        lblProfileName.setText(operator.getUsername());
        if (operator != null && operator.getPicture() != null && !operator.getPicture().isEmpty()) {
            imgProfilePicture.setImage(showImageClass.showImage(operator.getPicture()));
        } else {
            imgProfilePicture.setImage(showImageClass.showImage("profileimages/image.png"));
        }
    }

    private Path generateUniqueFilePath(Path directory, String fileName) {
        // Check if the file already exists in the directory
        Path filePath = directory.resolve(fileName);
        if (!Files.exists(filePath)) {
            return filePath; // File does not exist, return the original file path
        }

        // If the file already exists, append a number to the file name until a unique name is found
        int count = 1;
        String baseFileName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        while (true) {
            String numberedFileName = baseFileName + "_" + count + extension;
            Path numberedFilePath = directory.resolve(numberedFileName);
            if (!Files.exists(numberedFilePath)) {
                return numberedFilePath; // Found a unique file path
            }
            count++;
        }
    }

    private TextField addComponentsToGridPane(String labelText) {
        // Create label
        Label label = new Label(labelText);

        // Create text field
        TextField textField = new TextField();
        GridPane gridpane = new GridPane();
        // Add label and text field to grid pane
        gridpane.add(label, 0, textFieldCount);
        gridpane.add(textField, 0, textFieldCount + 1);

        // Increment text field count for positioning
        textFieldCount += 2;

        // Center label above text field
        GridPane.setHalignment(label, Pos.CENTER.getHpos());
        flowPaneInformation.getChildren().add(gridpane);
        return textField;
    }
    private Label addLabelToFlowPane(String labelText) {
        // Create label
        Label label = new Label(labelText);

        flowPaneInformation.getChildren().add(label);
        return label;
    }

    private Button addButtonToFlowPane(String buttonText) {
        // Create button
        Button button = new Button(buttonText);

        // Add button to flow pane
        flowPaneInformation.getChildren().add(button);

        return button;
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
    private void handleHome(ActionEvent actionEvent) {
        setup();
    }

    @FXML
    private void handlePersonnelInfo(ActionEvent actionEvent) {
        flowPaneInformation.getChildren().clear();
        GridPane gridPane = widgetsClass.createWidgetGridpane();

        Label label = widgetsClass.createWidgetLabel(operator.getUsername());
        MFXTextField username = widgetsClass.createWidgetMFXTextfield();

        GridPane.setHalignment(label, HPos.CENTER);

        gridPane.add(label, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);
    }

    @FXML
    private void handleSecurity(ActionEvent actionEvent) {
        flowPaneInformation.getChildren().clear();
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Change Password");
        Label label = widgetsClass.createWidgetLabel("Change Password");
        MFXPasswordField currentPassword = widgetsClass.createWidgetMFXPassword();
        currentPassword.setPromptText("Current Password");
        MFXPasswordField newPassword = widgetsClass.createWidgetMFXPassword();
        newPassword.setPromptText("Enter new Password");
        MFXPasswordField confirmPassword = widgetsClass.createWidgetMFXPassword();
        confirmPassword.setPromptText("Confirm new Password");

        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(currentPassword, HPos.CENTER);
        GridPane.setHalignment(newPassword, HPos.CENTER);
        GridPane.setHalignment(confirmPassword, HPos.CENTER);

        gridPane.add(label, 0, 0);
        gridPane.add(button, 0, 1);
        gridPane.add(currentPassword, 0, 2);
        gridPane.add(newPassword, 0, 3);
        gridPane.add(confirmPassword, 0, 4);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            // Clear previous styles
            currentPassword.getStyleClass().remove("red-outline");
            newPassword.getStyleClass().remove("red-outline");
            confirmPassword.getStyleClass().remove("red-outline");

            // Get stored hashed password
            String hashedPassword = operator.getPassword();
            System.out.println("Stored hashed password: " + hashedPassword);

            // Verify the current password
            String currentPwdText = currentPassword.getText();
            String newPwdText = newPassword.getText();
            String confirmPwdText = confirmPassword.getText();

            System.out.println("Entered current password: " + currentPwdText);
            System.out.println("Entered new password: " + newPwdText);
            System.out.println("Entered confirm password: " + confirmPwdText);

            if (BCrypt.checkpw(currentPwdText, hashedPassword)) {
                // Check if new password and confirm password match
                if (newPwdText.equals(confirmPwdText)) {
                    // update the operator's password
                    operator.setPassword(newPassword.getText());
                    personnelModel.updatePersonnel(operator);
                    System.out.println("Password updated successfully.");
                } else {
                    // New password and confirm password didn't match
                    newPassword.setText("");
                    confirmPassword.setText("");
                    confirmPassword.setPromptText("Passwords Didn't Match");
                    newPassword.setPromptText("Passwords Didn't Match");
                    newPassword.getStyleClass().add("red-outline");
                    confirmPassword.getStyleClass().add("red-outline");
                    System.out.println("New passwords do not match.");
                }
            } else {
                // Current password is incorrect
                currentPassword.setText("");
                currentPassword.setPromptText("Incorrect Password");
                currentPassword.getStyleClass().add("red-outline");
                System.out.println("Current password is incorrect.");
            }
        });
    }

    @FXML
    private void handlePeopleAndSharing(ActionEvent actionEvent) {
    }

    @FXML
    private void handleProfilePicture(MouseEvent mouseEvent) {
        // Create a new FileChooser
        FileChooser fileChooser = new FileChooser();

        // Set the title for the FileChooser dialog
        fileChooser.setTitle("Select Image File");

        // Set the initial directory for the FileChooser (optional)
        File initialDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(initialDirectory);

        // Add filters to only allow certain types of files to be selected (optional)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show the FileChooser dialog and wait for user selection
        File selectedFile = fileChooser.showOpenDialog(null);

        // If a file was selected, copy it to the destination folder
        if (selectedFile != null) {
            try {
                // Construct the destination path
                Path destinationPath = generateUniqueFilePath(Paths.get("resources/images"), selectedFile.getName());

                // Copy the file to the destination folder
                Files.copy(selectedFile.toPath(), destinationPath);

                // Updating UI
                imgProfilePicture.setImage(showImageClass.showImage(destinationPath.getFileName().toString()));
                operator.setPicture(destinationPath.getFileName().toString());
                personnelModel.updatePersonnel(operator);
            } catch (IOException e) {
                System.err.println("Error copying file: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("error uploading Profile picture" + e.getMessage());
            }
        }
    }
    @FXML
    private void handleDragDrop(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            for (File file : dragboard.getFiles()) {
                // Construct the destination path
                try {
                    // Construct the destination path
                    Path destinationPath = generateUniqueFilePath(Paths.get("resources/images"), file.getName());

                    // Copy the file to the destination folder
                    Files.copy(file.toPath(), destinationPath);

                    // Updating UI
                    imgProfilePicture.setImage(showImageClass.showImage(file.getName()));
                    operator.setPicture(file.getName());
                    personnelModel.updatePersonnel(operator);
                } catch (IOException e) {
                    System.err.println("Error copying file: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("error uploading Profile picture" + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != this) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }
}
