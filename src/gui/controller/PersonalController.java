package gui.controller;

import be.Personal;
import gui.helperclases.ShowImageClass;
import gui.model.PersonalModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonalController {
    @FXML
    private Label lblProfileName;
    @FXML
    private ImageView imgLogo;
    @FXML
    private MFXTextField txtSearch;
    @FXML
    private MFXButton btnHome;
    @FXML
    private MFXButton btnPersonalInfo;
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
    private Personal operator = new Personal();
    private PersonalModel personalModel;

    public void setup() {
        try {
            personalModel = new PersonalModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        flowPaneInformation.getChildren().clear();
        addButtonToFlowPane("Change Password");
    }
    public void setOperator(Personal operator) {
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
    }

    @FXML
    private void handleHome(ActionEvent actionEvent) {
        setup();
    }

    @FXML
    private void handlePersonalInfo(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSecurity(ActionEvent actionEvent) {
        flowPaneInformation.getChildren().clear();
        TextField currentPassword = addComponentsToGridPane("Enter your current Password");
        TextField newPassword =  addComponentsToGridPane("Enter your new Password");
        Button changePassword = addButtonToFlowPane("Change Password");

        changePassword.setOnAction(event -> {
            if (currentPassword.getText().equals(operator.getPassword())) {
                operator.setPassword(newPassword.getText());
                try {
                    personalModel.updatePersonal(operator);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                flowPaneInformation.getChildren().removeAll(changePassword,newPassword,changePassword);
                Label passwordChanged = addLabelToFlowPane("Successfully Changed Password");
                flowPaneInformation.getChildren().add(passwordChanged);
            } else {
                currentPassword.setText("");
                newPassword.setText("");
                currentPassword.setPromptText("Incorrect Password");
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
                personalModel.updatePersonal(operator);
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
                    personalModel.updatePersonal(operator);
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
