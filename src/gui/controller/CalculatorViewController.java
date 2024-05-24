package gui.controller;

import be.Geography;
import be.Personnel;
import be.Team;
import dal.db.DataAccessException;
import gui.helperclases.ShowImageClass;
import gui.model.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class CalculatorViewController {
    @FXML
    private Label lblListPersonnel,lblPersonnelName,lblTeamName;
    @FXML
    private FlowPane flowPaneInformation;
    public MFXButton CalcBtn;
    @FXML
    private ImageView imgLogo,imgProfileIcon;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private ListView listPersonnel,listTeams;
    @FXML
    private MFXTextField txtAnnualSalary,txtCountryName,txtFixedSalary,txtCountryGross,txtEffectiveWorkingHours,txtHourlyRate,txtDailyRate,txtMarkupMultiplier,txtMarginMultiplier,txtAmountOfHoursAllocated;

    private Personnel operator = new Personnel(),selectedPersonnel = new Personnel();
    private PersonnelModel personnelModel;
    private TeamMappingModel teamMappingModel;
    private TeamModel teamModel;
    private ManagerMembersModel managerMembersModel;
    private ShowImageClass showImageClass = new ShowImageClass();
    private Team selectedTeam = new Team();
    private String lastUpdatedField = "", daily = "daily", hourly = "hourly", markup = "markup",personneltext = "List of Personnel", countrytext= "List of Country/Regions";
    private double fHourlyRate, fDailyRate;
    private boolean isProgrammaticChange = false;

    public CalculatorViewController() {
        try {
            personnelModel = new PersonnelModel();
            teamMappingModel = new TeamMappingModel();
            teamModel = new TeamModel();
            managerMembersModel = new ManagerMembersModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setup(Personnel operator) {
        if (operator.getRoleId() == 1) {
            ObservableList<Personnel> listOfPersonnel = FXCollections.observableArrayList();
            listOfPersonnel.setAll(personnelModel.getAllPersonnel());
            listPersonnel.setItems(listOfPersonnel);

            ObservableList<Team> listOfTeams = FXCollections.observableArrayList();
            listOfTeams.setAll(teamModel.getAllTeams());
            listTeams.setItems(listOfTeams);
        }
        if (operator.getRoleId() == 2) {
            try {
                listTeams.setItems(managerMembersModel.getAllManagerTeams(operator));
                listPersonnel.setItems(managerMembersModel.getAllManagerMembers(operator));
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }


        lblListPersonnel.setText(personneltext);
        txtCountryName.textProperty().addListener((observable, oldValue, newValue) -> {
            lblListPersonnel.setText(countrytext);
            CountryModel countryModel = new CountryModel();
            String query = txtCountryName.getText().toLowerCase().trim();

            // Filter personnel based on search query
            ObservableList<Geography> filteredGeography = countryModel.getAllGeography().filtered(geography ->
                    geography.getCountry().toLowerCase().contains(query)
            );
            listPersonnel.setItems(filteredGeography);
        });

        // Add listeners for txtHourlyRate
        txtHourlyRate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange) {
                lastUpdatedField = hourly;
                calculateDailyRate();
            }
        });

        // Add listeners for txtDailyRate
        txtDailyRate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange) {
                lastUpdatedField = daily;
                calculateHourlyRate();
            }
        });

        // Add listeners for txtAmountOfHoursAllocated
        txtAmountOfHoursAllocated.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange) {
                if (lastUpdatedField.equals(daily)) {
                    calculateHourlyRate();
                } else if (lastUpdatedField.equals(hourly)) {
                    calculateDailyRate();
                } else if (lastUpdatedField.equals(markup)) {
                    calculateDailyRate();
                }
            }
        });

        // Add listener for txtMarginMultiplier
        txtMarkupMultiplier.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange) {
                lastUpdatedField = markup;
                if (newValue.isEmpty()) {
                    setProgrammaticChange(true);
                    txtHourlyRate.setText(String.valueOf(fHourlyRate));
                    txtDailyRate.setText(String.valueOf(fDailyRate));
                    setProgrammaticChange(false);
                } else {
                    calculateMarkUp();
                }
            }
        });

        txtMarginMultiplier.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange) {
                lastUpdatedField = markup;
                if (newValue.isEmpty()) {
                    setProgrammaticChange(true);
                    txtHourlyRate.setText(String.valueOf(fHourlyRate));
                    txtDailyRate.setText(String.valueOf(fDailyRate));
                    setProgrammaticChange(false);
                } else {
                    calculateMargin();
                }
            }
        });

        // Add listener for txtFixedSalary
        txtFixedSalary.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange) {
                calculateHourlyRate();
                calculateDailyRate();
                calculateMarkUp();
                calculateMargin();
            }
        });

        txtEffectiveWorkingHours.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange) {
                calculateRatesWithEffectiveWorkingHours();
            }
        });
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
            controller.setup(operator);
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
        if (lblListPersonnel.getText().equals(personneltext)) {
            selectedPersonnel = (Personnel) listPersonnel.getSelectionModel().getSelectedItem();

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
        if (lblListPersonnel.getText().equals(countrytext)) {
            Geography geography = (Geography) listPersonnel.getSelectionModel().getSelectedItem();
            txtCountryGross.setText(geography.getCountryGross());
            txtMarginMultiplier.setText(String.valueOf(geography.getCountryMargin()));
            txtCountryName.setText(geography.getCountry());
            lblListPersonnel.setText(personneltext);

            reloadWithRoleId();
        }
    }

    @FXML
    private void handleSelectedTeam(MouseEvent mouseEvent) throws DataAccessException {
        selectedTeam = (Team) listTeams.getSelectionModel().getSelectedItem();
        ObservableList<Personnel> listOfTeamMembers = FXCollections.observableArrayList();
        listOfTeamMembers.setAll(teamMappingModel.getAllTeamMembers(selectedTeam));
        listPersonnel.setItems(listOfTeamMembers);
        lblListPersonnel.setText(personneltext);
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
    private void handleReload(MouseEvent mouseEvent) {
        reloadWithRoleId();
    }
    private void reloadWithRoleId() {
        if (operator.getRoleId() == 1) {
            listPersonnel.setItems(personnelModel.getAllPersonnel());
        }
        if (operator.getRoleId() == 2) {
            try {
                listPersonnel.setItems(managerMembersModel.getAllManagerMembers(operator));
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void handlePrintPdf(ActionEvent actionEvent) {
        try {
            // Create a PDFMergerUtility instance
            PDFMergerUtility mergerUtility = new PDFMergerUtility();

            // Iterate over FlowPane children and add each PDF to the merger utility
            for (javafx.scene.Node node : flowPaneInformation.getChildren()) {
                if (node instanceof VBox) {
                    VBox pdfBox = (VBox) node;
                    Object[] userDataArray = (Object[]) pdfBox.getUserData();
                    String base64PDF = (String) userDataArray[0]; // Base64 PDF data

                    // Decode base64 to byte array
                    byte[] pdfBytes = Base64.getDecoder().decode(base64PDF);

                    // Add the PDF bytes to the merger utility
                    mergerUtility.addSource(new ByteArrayInputStream(pdfBytes));
                }
            }

            // Prompt user for save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Merged PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                // Set the destination file
                mergerUtility.setDestinationFileName(file.getAbsolutePath());

                // Merge the PDFs
                mergerUtility.mergeDocuments(null);

                System.out.println("Merged PDF saved successfully.");
            } else {
                System.out.println("PDF merge canceled by user.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCalcPerson() {
        String name = selectedPersonnel.getUsername();
        String role = selectedPersonnel.getRole();
        double personalSalary = selectedPersonnel.getSalary();
        double fixedSalary = Double.parseDouble(txtFixedSalary.getText());
        String team = "";
        if (selectedTeam != null) {
            team = selectedTeam.getName();
        }
        if (selectedTeam.getId() == 0) {
            selectedTeam.setName("Independent");
            team = selectedTeam.getName();
        }

        double effectiveWorkingHours = Double.parseDouble(txtEffectiveWorkingHours.getText());
        double hourlyRate = Double.parseDouble(txtHourlyRate.getText());
        double dailyRate = Double.parseDouble(txtDailyRate.getText());
        double markupMultiplier = 1.0; // Default value
        String markupMultiplierText = txtMarginMultiplier.getText();
        if (!markupMultiplierText.isEmpty()) {
            markupMultiplier = Double.parseDouble(txtMarkupMultiplier.getText());
        }
        double marginMultiplier = 1.0; // Default value
        String marginMultiplierText = txtMarginMultiplier.getText();
        if (!marginMultiplierText.isEmpty()) {
            marginMultiplier = Double.parseDouble(marginMultiplierText);
        }
        String countryGross = txtCountryGross.getText();
        String countryName = txtCountryName.getText();
        double amountOfHoursAllocated = Double.parseDouble(txtAmountOfHoursAllocated.getText());

        PDDocument document = null;
        PDPageContentStream contentStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            // Create a new PDF document
            document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            contentStream = new PDPageContentStream(document, page);

            // Add content to the page
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            float yCoordinate = 700; // Starting Y-coordinate
            contentStream.newLineAtOffset(100, yCoordinate);
            contentStream.showText("Name: " + name);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            if (!team.isEmpty()) {
                contentStream.newLineAtOffset(0, -12); // Move to the next line
                contentStream.showText("Team: " + team);
                yCoordinate -= 12; // Decrease Y-coordinate for next line
            }
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Role: " + role);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Personal Salary: " + personalSalary);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Fixed Salary: " + fixedSalary);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Effective Working Hours: " + effectiveWorkingHours);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Hourly Rate: " + hourlyRate);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Daily Rate: " + dailyRate);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Markup Multiplier: " + markupMultiplier);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Country: " + countryName);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Country GDP: " + countryGross);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Country Margin Multiplier: " + marginMultiplier);
            yCoordinate -= 12; // Decrease Y-coordinate for next line
            contentStream.newLineAtOffset(0, -12); // Move to the next line
            contentStream.showText("Amount of Hours Allocated in a day: " + amountOfHoursAllocated);
            contentStream.endText();

            // Close the content stream
            contentStream.close();

            // Save the document to a byte array
            document.save(byteArrayOutputStream);

            // Store PDF data as a base64 string
            String base64PDF = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            // Add the PDF to the FlowPane
            addPDFToFlowPane(base64PDF, name);
            clearTxtFields();

            System.out.println("PDF generated and added to FlowPane successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (contentStream != null) {
                    contentStream.close();
                }
                if (document != null) {
                    document.close();
                }
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearTxtFields(){
        txtMarkupMultiplier.setText("");
        txtDailyRate.setText("");
        txtAnnualSalary.setText("");
        txtHourlyRate.setText("");
        txtFixedSalary.setText("");
        txtEffectiveWorkingHours.setText("");
        txtAmountOfHoursAllocated.setText("");
        txtCountryGross.setText("");
        txtCountryName.setText("");
        txtMarginMultiplier.setText("");
        lblTeamName.setText("Team Name");
        lblPersonnelName.setText("Personnel Name");
    }

    private void addPDFToFlowPane(String base64PDF, String name) {
        VBox pdfBox = new VBox();
        Text pdfName = new Text("PDF: " + name);
        Button viewButton = new Button("View");
        Button deleteButton = new Button("Delete");

        viewButton.setOnAction(event -> viewPDF(base64PDF));
        deleteButton.setOnAction(event -> deletePDF(pdfBox));

        pdfBox.getChildren().addAll(pdfName, viewButton, deleteButton);

        // Store personnel and additional information as an object array
        Object[] userDataArray = new Object[]{base64PDF};
        pdfBox.setUserData(userDataArray); // Store personnel and additional information in userData

        flowPaneInformation.getChildren().add(pdfBox);
    }
    private void viewPDF(String base64PDF) {
        try {
            // Decode base64 to byte array
            byte[] pdfBytes = Base64.getDecoder().decode(base64PDF);

            // Create a temporary file
            File tempFile = File.createTempFile("tempPDF", ".pdf");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(pdfBytes);
            }

            // Open the PDF using the system's default PDF viewer
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(tempFile);
            } else {
                System.out.println("Desktop is not supported. Cannot open PDF.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletePDF(VBox pdfBox) {
        flowPaneInformation.getChildren().remove(pdfBox);
        System.out.println("PDF deleted from FlowPane.");
    }
    @FXML
    private void handleSearchCountryName(ActionEvent actionEvent) {
        CountryModel countryModel = new CountryModel();
        listPersonnel.setItems(countryModel.getAllGeography());
    }

    private void calculateDailyRate() {
        if (txtMarkupMultiplier.getText().isEmpty()) {
            try {
                double hourly = Double.parseDouble(txtHourlyRate.getText());
                double mountOfHoursInADay = Double.parseDouble(txtAmountOfHoursAllocated.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());

                double dailyRate = hourly * mountOfHoursInADay;
                double totalHours = fixedSalary / hourly;

                setProgrammaticChange(true);
                txtDailyRate.setText(String.valueOf(dailyRate));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);

                fDailyRate = dailyRate;
            } catch (NumberFormatException e) {
                // Handle the case where the text is not a valid double
                setProgrammaticChange(true);
                txtDailyRate.setPromptText("Not Calculated Yet");
                txtEffectiveWorkingHours.setPromptText("Not Calculated Yet");
                setProgrammaticChange(false);
            }
        }
        if (!txtMarkupMultiplier.getText().isEmpty()){
            try {
                double hourly = Double.parseDouble(txtHourlyRate.getText());
                double mountOfHoursInADay = Double.parseDouble(txtAmountOfHoursAllocated.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());

                double dailyRate = hourly * mountOfHoursInADay;
                double totalHours = fixedSalary / hourly;

                setProgrammaticChange(true);
                txtDailyRate.setText(String.valueOf(dailyRate));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);
            } catch (NumberFormatException e) {
                // Handle the case where the text is not a valid double
                setProgrammaticChange(true);
                txtDailyRate.setPromptText("Not Calculated Yet");
                txtEffectiveWorkingHours.setPromptText("Not Calculated Yet");
                setProgrammaticChange(false);
            }
        }
    }
    private void calculateHourlyRate() {
        if (txtMarkupMultiplier.getText().isEmpty()) {
            try {
                double daily = Double.parseDouble(txtDailyRate.getText());
                double mountOfHoursInADay = Double.parseDouble(txtAmountOfHoursAllocated.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());

                double hourlyRate = daily / mountOfHoursInADay;
                double totalHours = fixedSalary / hourlyRate;

                setProgrammaticChange(true);
                txtHourlyRate.setText(String.valueOf(hourlyRate));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);

                fHourlyRate = hourlyRate;
            } catch (NumberFormatException e) {
                // Handle the case where the text is not a valid double
                setProgrammaticChange(true);
                txtHourlyRate.setPromptText("Not Calculated Yet");
                txtEffectiveWorkingHours.setPromptText("Not Calculated Yet");
                setProgrammaticChange(false);
            }
        }
        if (!txtMarkupMultiplier.getText().isEmpty()) {
            try {
                double daily = Double.parseDouble(txtDailyRate.getText());
                double mountOfHoursInADay = Double.parseDouble(txtAmountOfHoursAllocated.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());

                double hourlyRate = daily / mountOfHoursInADay;
                double totalHours = fixedSalary / hourlyRate;

                setProgrammaticChange(true);
                txtHourlyRate.setText(String.valueOf(hourlyRate));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);
            } catch (NumberFormatException e) {
                // Handle the case where the text is not a valid double
                setProgrammaticChange(true);
                txtHourlyRate.setPromptText("Not Calculated Yet");
                txtEffectiveWorkingHours.setPromptText("Not Calculated Yet");
                setProgrammaticChange(false);
            }
        }
    }

    private void calculateMarkUp() {
        try {
            if (fHourlyRate == 0 || fDailyRate == 0) {
                fHourlyRate = Double.parseDouble(txtHourlyRate.getText());
                fDailyRate = Double.parseDouble(txtDailyRate.getText());
            }
            if (txtMarginMultiplier.getText().isEmpty()) {
                double markUp = Double.parseDouble(txtMarkupMultiplier.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());

                double calcRateHourly = fHourlyRate * markUp;
                double calcRateDaily = fDailyRate * markUp;
                double totalHours = fixedSalary / calcRateHourly;

                setProgrammaticChange(true);
                txtDailyRate.setText(String.valueOf(calcRateDaily));
                txtHourlyRate.setText(String.valueOf(calcRateHourly));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);
            }
            if (!txtMarginMultiplier.getText().isEmpty()) {
                double markUp = Double.parseDouble(txtMarkupMultiplier.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());
                double margin = Double.parseDouble(txtMarginMultiplier.getText());

                double calcRateHourly = fHourlyRate * markUp * margin;
                double calcRateDaily = fDailyRate * markUp * margin;;
                double totalHours = fixedSalary / calcRateHourly;

                setProgrammaticChange(true);
                txtDailyRate.setText(String.valueOf(calcRateDaily));
                txtHourlyRate.setText(String.valueOf(calcRateHourly));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);
            }
        } catch (NumberFormatException e) {
            setProgrammaticChange(true);
            txtHourlyRate.setPromptText("Not Calculated Yet");
            txtDailyRate.setPromptText("Not Calculated Yet");
            setProgrammaticChange(false);
        }
    }

    private void calculateMargin() {
        try {
            if (fHourlyRate == 0 || fDailyRate == 0) {
                fHourlyRate = Double.parseDouble(txtHourlyRate.getText());
                fDailyRate = Double.parseDouble(txtDailyRate.getText());
            }
            if (txtMarkupMultiplier.getText().isEmpty()) {
                double margin = Double.parseDouble(txtMarginMultiplier.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());

                double calcRateHourly = fHourlyRate * margin;
                double calcRateDaily = fDailyRate * margin;
                double totalHours = fixedSalary / calcRateHourly;

                setProgrammaticChange(true);
                txtDailyRate.setText(String.valueOf(calcRateDaily));
                txtHourlyRate.setText(String.valueOf(calcRateHourly));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);
            }
            if (!txtMarkupMultiplier.getText().isEmpty()) {
                double markUp = Double.parseDouble(txtMarkupMultiplier.getText());
                double fixedSalary = Double.parseDouble(txtFixedSalary.getText());
                double margin = Double.parseDouble(txtMarginMultiplier.getText());

                double calcRateHourly = fHourlyRate * markUp * margin;
                double calcRateDaily = fDailyRate * markUp * margin;;
                double totalHours = fixedSalary / calcRateHourly;

                setProgrammaticChange(true);
                txtDailyRate.setText(String.valueOf(calcRateDaily));
                txtHourlyRate.setText(String.valueOf(calcRateHourly));
                txtEffectiveWorkingHours.setText(String.valueOf(totalHours));
                setProgrammaticChange(false);
            }
        } catch (NumberFormatException e) {
            setProgrammaticChange(true);
            txtHourlyRate.setPromptText("Not Calculated Yet");
            txtDailyRate.setPromptText("Not Calculated Yet");
            setProgrammaticChange(false);
        }
    }

    private void calculateRatesWithEffectiveWorkingHours() {
        try {
            double effectiveWorkingHours = Double.parseDouble(txtEffectiveWorkingHours.getText());
            double fixedSalary = Double.parseDouble(txtFixedSalary.getText());
            double mountOfHoursInADay = Double.parseDouble(txtAmountOfHoursAllocated.getText());

            double calcRateHourly = fixedSalary / effectiveWorkingHours;
            double calcRateDaily = calcRateHourly * mountOfHoursInADay;

            setProgrammaticChange(true);
            txtDailyRate.setText(String.valueOf(calcRateDaily));
            txtHourlyRate.setText(String.valueOf(calcRateHourly));
            setProgrammaticChange(false);
        } catch (NumberFormatException e) {
            setProgrammaticChange(true);
            txtHourlyRate.setPromptText("Not Calculated Yet");
            txtDailyRate.setPromptText("Not Calculated Yet");
            setProgrammaticChange(false);
        }
    }
    private void setProgrammaticChange(boolean value) {
        isProgrammaticChange = value;
    }
}
