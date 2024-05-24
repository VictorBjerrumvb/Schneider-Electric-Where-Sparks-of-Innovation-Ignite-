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

import java.io.IOException;

public class MainViewController {

    public FlowPane flowPaneInformation;

    private WidgetsClass widgetsClass = new WidgetsClass();
    private Personnel operator;

    public void setup(Personnel operator) {
        flowPaneInformation.getChildren().clear();
        if (operator.getRoleId() == 1) {
            switchPersonnelView();
            switchCalculatorView();
            switchTeamsView();
            switchManagersView();
            switchCountryView();
        }
        if (operator.getRoleId() == 2) {
            switchCalculatorView();
            switchCountryView();
        }
    }

    public void setOperator(Personnel operator) {
        this.operator = operator;
    }

    private void switchPersonnelView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button personnel = widgetsClass.createWidgetButton("Manage Personnel");

        GridPane.setHalignment(personnel, HPos.CENTER);

        gridPane.add(personnel, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        personnel.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreatePersonnelView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = personnel.getScene();
                scene.setRoot(secondWindow);
                CreatePersonnelController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void switchCalculatorView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Calculation");

        // Set alignment for each cell
        GridPane.setHalignment(button, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(button, 0, 0);

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
                controller.setup(operator);
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });
    }
    private void switchTeamsView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Manage Teams");

        // Set alignment for each cell
        GridPane.setHalignment(button, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(button, 0, 0);

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
    private void switchManagersView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Assign Personnel to Managers");

        // Set alignment for each cell
        GridPane.setHalignment(button, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(button, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageManagersView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene(); // Get the current scene
                scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
                ManageManagersController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });
    }
    private void switchCountryView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Manage Countries/Regions");

        // Set alignment for each cell
        GridPane.setHalignment(button, HPos.CENTER);

        // Add nodes to the grid
        gridPane.add(button, 0, 0);

        gridPane.getStyleClass().add("grid-pane");
        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateCountryView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene(); // Get the current scene
                scene.setRoot(secondWindow); // Set the root of the current scene to the new scene
                CreateCountryController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });
    }
}
