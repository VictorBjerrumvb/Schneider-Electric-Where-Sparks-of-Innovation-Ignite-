package gui.controller;

import be.Personnel;
import gui.helperclases.WidgetsClass;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainViewController {

    public FlowPane flowPaneInformation;


    private WidgetsClass widgetsClass = new WidgetsClass();
    private Personnel operator;


    public void setup() {
        flowPaneInformation.getChildren().clear();
        switchPersonnelView();
        switchCalculatorView();
        switchAdminView();
        switchTeamsView();
    }
    public void setOperator(Personnel operator) {
        this.operator = operator;
    }
    private void switchPersonnelView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button personnel = widgetsClass.createWidgetButton("Swipe");
        Label managePersonnel = widgetsClass.createWidgetLabel("Manage Personnel");

        // Set alignment for each cell
        GridPane.setHalignment(personnel, HPos.CENTER);
        GridPane.setHalignment(managePersonnel, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(personnel, 0, 1);
        gridPane.add(managePersonnel, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        personnel.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreatePersonnelView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = personnel.getScene(); // Get the current scene
                scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
                CreatePersonnelController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });
    }
    private void switchCalculatorView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Swipe");
        Label label = widgetsClass.createWidgetLabel("Calculation");

        // Set alignment for each cell
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(button, 0, 1);
        gridPane.add(label, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CalculatorView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene(); // Get the current scene
                scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
                CalculatorViewController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });
    }
    private void switchAdminView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Swipe");
        Label label = widgetsClass.createWidgetLabel("Administrator Controller");

        // Set alignment for each cell
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(button, 0, 1);
        gridPane.add(label, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);
        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene(); // Get the current scene
                scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
                AdminController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });
    }
    private void switchTeamsView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Swipe");
        Label label = widgetsClass.createWidgetLabel("Manage Teams");

        // Set alignment for each cell
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(button, 0, 1);
        gridPane.add(label, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTeamView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene(); // Get the current scene
                scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
                CreateTeamController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });
    }
}
