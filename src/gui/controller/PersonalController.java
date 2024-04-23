package gui.controller;

import gui.helperclases.ShowImageClass;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonalController {
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
    }

    @FXML
    private void handleSearch(MouseEvent mouseEvent) {
    }

    @FXML
    private void handleHome(ActionEvent actionEvent) {
    }

    @FXML
    private void handlePersonalInfo(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSecurity(ActionEvent actionEvent) {
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
            } catch (IOException e) {
                System.err.println("Error copying file: " + e.getMessage());
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
                } catch (IOException e) {
                    System.err.println("Error copying file: " + e.getMessage());
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
