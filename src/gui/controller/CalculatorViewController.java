package gui.controller;

import be.Personnel;
import be.Team;
import dal.db.DataAccessException;
import gui.helperclases.ShowImageClass;
import gui.model.PersonnelModel;
import gui.model.TeamMappingModel;
import gui.model.TeamModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    @FXML
    private ListView<String> rateListView;
    public FlowPane flowPaneInformation;
    public MFXButton CalcBtn;
    @FXML
    private ImageView imgLogo;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private ImageView imgProfileIcon;
    @FXML
    private ListView<Personnel> listPersonnel;
    @FXML
    private ListView<Team> listTeams;
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
            Scene scene = imgLogo.getScene();
            scene.setRoot(secondWindow);
            MainViewController controller = loader.getController();
            controller.setup();
            controller.setOperator(operator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonnelView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = btnProfile.getScene();
            scene.setRoot(secondWindow);
            PersonnelController controller = loader.getController();
            controller.setup();
            controller.setOperator(operator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent secondWindow = loader.load();
            Scene scene = btnProfile.getScene();
            scene.setRoot(secondWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSelectedPersonnel(MouseEvent mouseEvent) {
        selectedPersonnel = listPersonnel.getSelectionModel().getSelectedItem();

        if (selectedPersonnel != null) {
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
        } else {
            // Handle the case where selectedPersonnel is null
            // For example, clear labels and text fields
            lblPersonnelName.setText("");
            lblTeamName.setText("");
            txtAnnualSalary.setText("");
        }
    }

    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) throws DataAccessException {
        selectedTeam = listTeams.getSelectionModel().getSelectedItem();
        ObservableList<Personnel> listOfTeamMembers = FXCollections.observableArrayList();
        listOfTeamMembers.setAll(teamMappingModel.getAllTeamMembers(selectedTeam));
        listPersonnel.setItems(listOfTeamMembers);
        if (listOfTeamMembers.isEmpty()) {
            // Clear the list and display a message
            listPersonnel.getItems().clear();
            showNoTeamMembersAlert();
        } else {
            listPersonnel.setItems(listOfTeamMembers);
        }
    }

    private void showNoTeamMembersAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Team Members");
        alert.setHeaderText(null);
        alert.setContentText("The selected team does not have any members.");
        alert.showAndWait();
    }

    @FXML
    private void handleCalculate(ActionEvent actionEvent) {
        calculateRates();
    }

    private void calculateRates() {
        // Get the values from the text fields
        try {
            double annualSalary = Double.parseDouble(txtAnnualSalary.getText());
            double overheadMultiplier = Double.parseDouble(txtFixedSalary.getText());
            double fixedAnnualAmount = Double.parseDouble(txtCountryGross.getText());
            double annualEffectiveWorkingHours = Double.parseDouble(txtEffectiveWorkingHours.getText());
            double utilizationPercentage = Double.parseDouble(txtAmountOfHoursAllocated.getText());
            double markupMultiplier = Double.parseDouble(txtMarkupMultiplier.getText());
            double gmMultiplier = Double.parseDouble(txtMarginMultiplier.getText());

            // Calculate the hourly rate
            double hourlyRate = (annualSalary / (annualEffectiveWorkingHours * (utilizationPercentage / 100))) + (fixedAnnualAmount / annualEffectiveWorkingHours);
            hourlyRate = hourlyRate * (1 + (overheadMultiplier / 100));

            // Calculate the daily rate (assuming 8 working hours per day)
            double dayRate = hourlyRate * 8;

            // Apply multipliers
            hourlyRate *= (1 + (markupMultiplier / 100));
            hourlyRate *= (1 + (gmMultiplier / 100));
            dayRate *= (1 + (markupMultiplier / 100));
            dayRate *= (1 + (gmMultiplier / 100));

            // Display the calculated rates in the UI
            txtHourlyRate.setText(String.valueOf(hourlyRate));
            txtDailyRate.setText(String.valueOf(dayRate));

            // Print out the information to the console
            System.out.println("Annual Salary: " + annualSalary);
            System.out.println("Overhead Multiplier: " + overheadMultiplier);
            System.out.println("Fixed Annual Amount: " + fixedAnnualAmount);
            System.out.println("Annual Effective Working Hours: " + annualEffectiveWorkingHours);
            System.out.println("Utilization Percentage: " + utilizationPercentage);
            System.out.println("Markup Multiplier: " + markupMultiplier);
            System.out.println("GM Multiplier: " + gmMultiplier);
            System.out.println("Hourly Rate: " + hourlyRate);
            System.out.println("Daily Rate: " + dayRate);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    @FXML
    private void handleDistributeCost(ActionEvent actionEvent) {
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

    @FXML
    private void handleCalcBtn() {
        try {
            // Get input values from text fields
            double fixedSalary = Double.parseDouble(txtFixedSalary.getText());
            double effectiveWorkingHours = Double.parseDouble(txtEffectiveWorkingHours.getText());
            double hourlyRate = Double.parseDouble(txtHourlyRate.getText());
            double dailyRate = Double.parseDouble(txtDailyRate.getText());
            double markupMultiplier = Double.parseDouble(txtMarkupMultiplier.getText());
            double marginMultiplier = Double.parseDouble(txtMarginMultiplier.getText());
            double amountOfHoursAllocated = Double.parseDouble(txtAmountOfHoursAllocated.getText());

            // Perform calculation
            double calculatedRate = (fixedSalary / effectiveWorkingHours) * (hourlyRate + dailyRate) * markupMultiplier * marginMultiplier * amountOfHoursAllocated;

            // Update ListView with the calculated rate
            String rateInfo = String.format("Calculated Rate: %.2f", calculatedRate);
            rateListView.getItems().add(rateInfo);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e.getMessage());
            // Handle the case where the input values are not valid numbers
            // You can show an error message to the user or take appropriate action
        }
    }

    public void handleSearchCountryName(ActionEvent actionEvent) {
    }
}
