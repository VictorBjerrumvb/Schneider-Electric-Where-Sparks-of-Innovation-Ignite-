package gui.helperclases;


import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class WidgetsClass {
    public GridPane createWidgetInGridpane(String buttonText, String labelText) {
        GridPane widgetGridpane = new GridPane();
        widgetGridpane.setVgap(2);
        widgetGridpane.setHgap(2);
        Button button1 = new Button(buttonText);
        Label label1 = new Label(labelText);

        widgetGridpane.add(button1,0,1);
        widgetGridpane.add(label1,0,0);

        return widgetGridpane;
    }
}
