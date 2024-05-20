package gui.controller;

import be.Personnel;
import dal.db.DataAccessException;
import gui.helperclases.ShowImageClass;
import gui.helperclases.WidgetsClass;
import gui.model.ManagerMembersModel;
import gui.model.PersonnelModel;
import gui.model.TeamMappingModel;
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
import javafx.scene.control.ListView;
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

    @FXML
    private Label lblProfileName;
    @FXML
    private ImageView imgLogo,imgProfilePicture;
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
        GridPane gridPaneP = widgetsClass.createWidgetGridpane();

        Label labelP = widgetsClass.createWidgetLabel("Change Profile Picture");
        Button buttonP = widgetsClass.createWidgetButton("Click me");

        gridPaneP.add(labelP, 0, 0);
        gridPaneP.add(buttonP, 0, 1);

        gridPaneP.getStyleClass().add("grid-pane");
        GridPane containerP = widgetsClass.applyContainer(gridPaneP);

        //-----------------------------------------------------------------------------------------//
        GridPane gridPane = widgetsClass.createWidgetGridpane();

        Label label = widgetsClass.createWidgetLabel("Change Password");
        Button button = widgetsClass.createWidgetButton("Click me");

        gridPane.add(label, 0, 0);
        gridPane.add(button, 0, 1);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().addAll(containerP,container);

        //-----------------------------------------------------------------------------------------//

        buttonP.setOnAction(event -> {
            selectProfilePicture();
        });
        button.setOnAction(event -> {
            handleSecurity(new ActionEvent());
        });


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
    private void handleHome(ActionEvent actionEvent) {
        setup();
    }

    @FXML
    private void handlePersonnelInfo(ActionEvent actionEvent) {
        flowPaneInformation.getChildren().clear();
        GridPane gridPane = widgetsClass.createWidgetGridpane();

        Label label = widgetsClass.createWidgetLabel("Username | " + operator.getUsername());
        Label labelR = widgetsClass.createWidgetLabel("Role | " + operator.getRole());
        Label labelS = widgetsClass.createWidgetLabel("Salary | " + operator.getSalary());

        GridPane.setHalignment(label, HPos.CENTER);

        gridPane.add(label, 0, 0);
        gridPane.add(labelR, 0, 1);
        gridPane.add(labelS, 0, 2);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        //-----------------------------------------------------------------------------------------//

        GridPane gridPaneP = widgetsClass.createWidgetGridpane();

        Label labelP = widgetsClass.createWidgetLabel("Change Profile Picture");
        Button buttonP = widgetsClass.createWidgetButton("Click me");

        gridPaneP.add(labelP, 0, 0);
        gridPaneP.add(buttonP, 0, 1);

        gridPaneP.getStyleClass().add("grid-pane");
        GridPane containerP = widgetsClass.applyContainer(gridPaneP);

        flowPaneInformation.getChildren().addAll(container,containerP);

        //-----------------------------------------------------------------------------------------//

        buttonP.setOnAction(event -> {
            selectProfilePicture();
        });
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

            try {
                if (BCrypt.checkpw(currentPassword.getText(), hashedPassword)) {
                    // Check if new password and confirm password match
                    if (newPassword.getText().equals(confirmPassword.getText())) {
                        // Hash the new password and update the operator's password
                        String newHashedPassword = BCrypt.hashpw(confirmPassword.getText(), BCrypt.gensalt());
                        operator.setPassword(newHashedPassword);
                        personnelModel.updatePersonnel(operator);
                    } else {
                        // New password and confirm password didn't match
                        newPassword.setText("");
                        confirmPassword.setText("");
                        confirmPassword.setPromptText("Passwords Didn't Match");
                        newPassword.setPromptText("Passwords Didn't Match");
                        newPassword.getStyleClass().add("red-outline");
                        confirmPassword.getStyleClass().add("red-outline");
                    }
                } else {
                    // Current password is incorrect
                    currentPassword.setText("");
                    currentPassword.setPromptText("Incorrect Password");
                    currentPassword.getStyleClass().add("red-outline");
                }
            } catch (Exception e) {
                System.out.println("Error during password verification: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handlePeopleAndSharing(ActionEvent actionEvent) throws DataAccessException {
        flowPaneInformation.getChildren().clear();
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Label label = widgetsClass.createWidgetLabel("List of Teams you Control");

        ListView teams = widgetsClass.createWidgetListview();
        ManagerMembersModel managerMembersModel = new ManagerMembersModel();
        teams.setItems(managerMembersModel.getAllManagerTeams(operator));

        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(teams, HPos.CENTER);

        gridPane.add(label, 0, 0);
        gridPane.add(teams, 0, 1);

        gridPane.getStyleClass().add("grid-pane");
        GridPane containerTeam = widgetsClass.applyContainer(gridPane);


        //-----------------------------------------------------------------------------------------//


        GridPane gridpaneP = widgetsClass.createWidgetGridpane();
        Label labelp = widgetsClass.createWidgetLabel("List of Personnel you Control");

        ListView personel = widgetsClass.createWidgetListview();
        personel.setItems(managerMembersModel.getAllManagerMembers(operator));

        GridPane.setHalignment(labelp, HPos.CENTER);
        GridPane.setHalignment(personel, HPos.CENTER);

        gridpaneP.add(labelp, 0, 0);
        gridpaneP.add(personel, 0, 1);

        gridpaneP.getStyleClass().add("grid-pane");
        GridPane containerPersonnel = widgetsClass.applyContainer(gridpaneP);

        flowPaneInformation.getChildren().addAll(containerTeam, containerPersonnel);

    }

    @FXML
    private void handleProfilePicture(MouseEvent mouseEvent) {
        selectProfilePicture();
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

    private void selectProfilePicture() {
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
}
