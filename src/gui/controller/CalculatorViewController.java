package gui.controller;

import be.Personnel;
import gui.helperclases.ShowImageClass;
import gui.helperclases.WidgetsClass;
import gui.model.PersonnelModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

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
        listPersonnel.setItems(personnelModel.getAllPersonnel());
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
        flowPaneInformation.getChildren().add(widgetsClass.createWidgetInGridpane("swipe","Create Personnel"));
    }

    @FXML
    private void handleProfile(ActionEvent actionEvent) {
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {
    }
    private WidgetsClass widgetsClass = new WidgetsClass();
    @FXML
    private void handleSelectedPersonal(MouseEvent mouseEvent) {
        selectedPersonnel = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();
        lblPersonnelName.setText(selectedPersonnel.getUsername());
    }

    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) {
    }

    @FXML
    private void handleSearchCountryName(ActionEvent actionEvent) {
    }
}
