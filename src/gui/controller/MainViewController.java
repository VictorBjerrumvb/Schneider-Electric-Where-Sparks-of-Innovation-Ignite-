package gui.controller;

import be.Personnel;
import gui.helperclases.WidgetsClass;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        GridPane.setHalignment(button, HPos.CENTER);

        gridPane.add(button, 0, 0);

        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CalculatorView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene();
                scene.setRoot(secondWindow);
                CalculatorViewController controller = loader.getController();
                controller.setup(operator);
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void switchTeamsView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Manage Teams");

        GridPane.setHalignment(button, HPos.CENTER);

        gridPane.add(button, 0, 0);

        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTeamView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene();
                scene.setRoot(secondWindow);
                CreateTeamController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void switchManagersView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Assign Personnel to Managers");

        GridPane.setHalignment(button, HPos.CENTER);

        gridPane.add(button, 0, 0);

        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageManagersView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene();
                scene.setRoot(secondWindow);
                ManageManagersController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void switchCountryView() {
        GridPane gridPane = widgetsClass.createWidgetGridpane();
        Button button = widgetsClass.createWidgetButton("Manage Countries/Regions");

        GridPane.setHalignment(button, HPos.CENTER);

        gridPane.add(button, 0, 0);

        GridPane container = widgetsClass.applyContainer(gridPane);

        flowPaneInformation.getChildren().add(container);

        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateCountryView.fxml"));
                Parent secondWindow = loader.load();
                Scene scene = button.getScene();
                scene.setRoot(secondWindow);
                CreateCountryController controller = loader.getController();
                controller.setup();
                controller.setOperator(operator);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
