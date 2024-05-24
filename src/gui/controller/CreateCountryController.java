package gui.controller;

import be.Geography;
import be.Personnel;
import be.Team;
import gui.helperclases.ShowImageClass;
import gui.model.CountryModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CreateCountryController {
    @FXML
    private MFXTextField txtCountryGross, txtCountryMargin, txtCreateCountry;
    @FXML
    private ListView listCountry;
    @FXML
    private TextField txtSearch;
    @FXML
    private MFXButton btnCreateCountry;
    @FXML
    private Label lblCreateCountry;
    @FXML
    private ImageView imgProfileIcon, imgLogo;
    @FXML
    private MenuButton btnProfile;

    private Personnel operator;
    private ShowImageClass showImageClass = new ShowImageClass();
    private CountryModel countryModel;
    private String create = "Create New Country", update = "Update Country Name";
    private Geography selectedGeography;

    public CreateCountryController() {
        countryModel = new CountryModel();
    }

    public void setup() {
        listCountry.setItems(countryModel.getAllGeography());
        lblCreateCountry.setText(create);
        selectedGeography = new Geography();
        txtCountryGross.setPromptText("Denmark's GDP | 400.2Billion");
        txtCountryMargin.setPromptText("Country Gross Margin default");
        btnCreateCountry.setText(create);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch();
        });
    }

    private void handleSearch() {
        // If update mode is active, reset it
        if (lblCreateCountry.getText().equals(update)) {
            lblCreateCountry.setText(create);
            txtCreateCountry.setText("");
            txtCreateCountry.setPromptText("Denmark");
            txtCountryGross.setText("");
            txtCountryGross.setPromptText("Country Gross");
            txtCountryMargin.setText("");
            txtCountryMargin.setPromptText("Country Gross Margin default");
            btnCreateCountry.setText(create);
        }

        // Get the search query
        String query = txtSearch.getText().toLowerCase().trim();

        // Filter personnel based on search query
        ObservableList<Geography> filteredGeography = countryModel.getAllGeography().filtered(geography ->
                geography.getCountry().toLowerCase().contains(query)
        );
        listCountry.setItems(filteredGeography);
    }

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
    private void handleCreateCountry(ActionEvent actionEvent) {
        if (lblCreateCountry.getText().equals(create)) {
            Geography geography = new Geography();
            geography.setCountry(txtCreateCountry.getText());
            countryModel.createGeography(geography);
            listCountry.setItems(countryModel.getAllGeography());
        }
        if (lblCreateCountry.getText().equals(update)) {
            selectedGeography.setCountry(txtCreateCountry.getText());
            countryModel.updateGeographyName(selectedGeography);
            listCountry.setItems(countryModel.getAllGeography());
        }
    }

    @FXML
    private void handleKeyPressedCountry(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Geography geography = (Geography) listCountry.getSelectionModel().getSelectedItem(); // Remove unnecessary cast
            if (geography != null) {
                countryModel.deleteGeography(geography);
                listCountry.setItems(countryModel.getAllGeography());
            }
        }
    }

    @FXML
    private void handleSelectedCountry(MouseEvent mouseEvent) {
        selectedGeography = (Geography) listCountry.getSelectionModel().getSelectedItem();
        lblCreateCountry.setText(update);
        btnCreateCountry.setText(update);
        txtCreateCountry.setText(selectedGeography.getCountry());
        txtCountryGross.setText(selectedGeography.getCountryGross());
        txtCountryMargin.setText(String.valueOf(selectedGeography.getCountryMargin()));
    }

    @FXML
    private void handleUpdateCountry(ActionEvent actionEvent) {
        try {
            selectedGeography.setCountryGross(txtCountryGross.getText());
            selectedGeography.setCountryMargin(Double.parseDouble(txtCountryMargin.getText()));
            countryModel.updateGeography(selectedGeography);
            setup();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Optionally, show an alert to the user indicating the input was invalid
            showErrorAlert("Invalid input", "Please enter a valid number for country gross and margin.");
        }
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
