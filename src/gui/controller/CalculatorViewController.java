package gui.controller;

import be.Personal;
import gui.helperclases.ShowImageClass;
import gui.model.PersonalModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CalculatorViewController {
    @FXML
    private ImageView imgLogo;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private ImageView imgProfileIcon;
    @FXML
    private ListView listPersonal;
    @FXML
    private ListView listTeams;
    @FXML
    private Label lblPersonalName;
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

    private Personal operator = new Personal();
    private PersonalModel personalModel;
    private ShowImageClass showImageClass = new ShowImageClass();
    private Personal selectedPersonal = new Personal();
    public CalculatorViewController() {
        try {
            personalModel = new PersonalModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setup() {
        listPersonal.setItems(personalModel.getAllPersonal());
    }
    public void setOperator(Personal operator) {
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
    }

    @FXML
    private void handleProfile(ActionEvent actionEvent) {
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSelectedPersonal(MouseEvent mouseEvent) {
        selectedPersonal = (Personal) listPersonal.getSelectionModel().getSelectedItem();
        lblPersonalName.setText(selectedPersonal.getUsername());
    }

    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) {
    }

    @FXML
    private void handleSearchCountryName(ActionEvent actionEvent) {
    }
}
