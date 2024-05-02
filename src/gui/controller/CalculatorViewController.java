package gui.controller;

import be.Personnel;
import gui.helperclases.ShowImageClass;
import gui.helperclases.WidgetsClass;
import gui.model.PersonnelModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class CalculatorViewController {
    public FlowPane flowPaneInformation;
    @FXML
    private ImageView imgLogo;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private ImageView imgProfileIcon;
    @FXML
    private ListView listPersonnel;
    @FXML
    private ListView listTeams;
    @FXML
    private Label lblPersonnelName;
    @FXML
    private MFXTextField txtAnnualSalary;
    @FXML
    private Label lblCountryName;
    @FXML
    private MFXTextField txtCountryName;
    @FXML
    private Label lblTeamName;
    @FXML
    private MFXTextField txtFixedSalary;
    @FXML
    private Label lblCountryGross;
    @FXML
    private MFXTextField txtCountryGross;
    @FXML
    private MFXTextField txtEffectiveWorkingHours;
    @FXML
    private MFXTextField txtHourlyRate;
    @FXML
    private MFXTextField txtDailyRate;
    @FXML
    private MFXTextField txtMarkupMultiplier;
    @FXML
    private MFXTextField txtMarginMultiplier;
    @FXML
    private MFXTextField txtAmountOfHoursAllocated;

    private Personnel operator = new Personnel();
    private PersonnelModel personnelModel;
    private ShowImageClass showImageClass = new ShowImageClass();
    private Personnel selectedPersonnel = new Personnel();
    public CalculatorViewController() {
        try {
            personnelModel = new PersonnelModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setup() {
        ObservableList<Personnel> listOfPersonnel = FXCollections.observableArrayList();
        listOfPersonnel.setAll(personnelModel.getAllPersonnel());
        listPersonnel.setItems(listOfPersonnel);
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
    private void handleSelectedPersonnel(MouseEvent mouseEvent) {
        selectedPersonnel = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();
        lblPersonnelName.setText(selectedPersonnel.getUsername());
        txtAnnualSalary.setText(String.valueOf(selectedPersonnel.getSalary()));
    }

    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) {
    }

    @FXML
    private void handleSearchCountryName(ActionEvent actionEvent) {
    }
}
