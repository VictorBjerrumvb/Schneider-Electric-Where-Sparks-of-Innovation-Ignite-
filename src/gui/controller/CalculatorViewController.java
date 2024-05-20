package gui.controller;

import be.Personnel;
import be.Team;
import dal.db.DataAccessException;
import gui.helperclases.ShowImageClass;
import gui.helperclases.WidgetsClass;
import gui.model.PersonnelModel;
import gui.model.TeamMappingModel;
import gui.model.TeamModel;
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
import java.util.ArrayList;
import java.util.List;

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
    private TeamMappingModel teamMappingModel;
    private TeamModel teamModel;
    private ShowImageClass showImageClass = new ShowImageClass();
    private Personnel selectedPersonnel = new Personnel();
    private Team selectedTeam = new Team();
    public CalculatorViewController() {
        try {
            personnelModel = new PersonnelModel();
            teamMappingModel = new TeamMappingModel();
            teamModel = new TeamModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setup() {
        ObservableList<Personnel> listOfPersonnel = FXCollections.observableArrayList();
        listOfPersonnel.setAll(personnelModel.getAllPersonnel());
        listPersonnel.setItems(listOfPersonnel);
        ObservableList<Team> listOfTeams = FXCollections.observableArrayList();
        listOfTeams.setAll(teamModel.getAllTeams());
        listTeams.setItems(listOfTeams);
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
        ObservableList<Team> listOfTeams = FXCollections.observableArrayList();
        listOfTeams.setAll(teamMappingModel.getPersonnelTeam(selectedPersonnel));
        List<String> teams = new ArrayList<>();
        for (Team t : listOfTeams) {
            String string = t.getName();
            teams.add(string);
        }
        lblTeamName.setText(String.valueOf(teams));
        txtAnnualSalary.setText(String.valueOf(selectedPersonnel.getSalary()));
    }

    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) throws DataAccessException {
        selectedTeam = (Team) listTeams.getSelectionModel().getSelectedItem();
        ObservableList<Personnel> listOfTeamMembers = FXCollections.observableArrayList();
        listOfTeamMembers.setAll(teamMappingModel.getAllTeamMembers(selectedTeam));
        if (listOfTeamMembers.isEmpty()) {
            String firststring = "The selected Team";
            String secondstring = "does not have any members";
            listPersonnel.getItems().clear();
            listPersonnel.getItems().add(firststring);
            listPersonnel.getItems().add(secondstring);
        }
        if (!listOfTeamMembers.isEmpty()) {
            listPersonnel.setItems(teamMappingModel.getAllTeamMembers(selectedTeam));
        }
    }

    @FXML
    private void handleSearchCountryName(ActionEvent actionEvent) {
    }
    @FXML
    private void handleCalculate(ActionEvent actionEvent) {
        // Calculate and display day rate or hourly rate based on the entered employee profile
        calculateRates();
    }

    private void calculateRates() {
        // Get selected employee from the list
        Personnel selectedEmployee = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            double annualSalary = selectedEmployee.getSalary();
            double overheadMultiplier = selectedEmployee.getOverheadMultiplier();
            double fixedAnnualAmount = selectedEmployee.getFixedAnnualAmount();
            double annualEffectiveWorkingHours = selectedEmployee.getAnnualEffectiveWorkingHours();
            double utilizationPercentage = selectedEmployee.getUtilizationPercentage();

            // Perform calculations based on the provided formulas
            double hourlyRate = (annualSalary / (annualEffectiveWorkingHours * utilizationPercentage)) + overheadMultiplier;
            double dayRate = hourlyRate * 8; // Assuming 8 working hours per day

            // Apply multipliers
            double markupMultiplier = Double.parseDouble(txtMarkupMultiplier.getText());
            double gmMultiplier = Double.parseDouble(txtMarginMultiplier.getText());
            hourlyRate *= (1 + (markupMultiplier / 100));
            hourlyRate *= (1 + (gmMultiplier / 100));
            dayRate *= (1 + (markupMultiplier / 100));
            dayRate *= (1 + (gmMultiplier / 100));

            // Display the calculated rates in the UI
            txtHourlyRate.setText(String.valueOf(hourlyRate));
            txtDailyRate.setText(String.valueOf(dayRate));
        }
    }
    @FXML
    private void handleDistributeCost(ActionEvent actionEvent) {
        // Distribute the cost of overhead employees among various teams
        distributeCost();
    }

    private void distributeCost() {
        // Implement the functionality to distribute the cost of overhead employees among various teams
        // You may need additional UI elements to select teams and specify cost distribution percentages
        // Calculate and update the cost distribution accordingly
    }

    public void handleReload(MouseEvent mouseEvent) {
        listPersonnel.setItems(personnelModel.getAllPersonnel());
    }
}
