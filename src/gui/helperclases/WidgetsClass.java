package gui.helperclases;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Helper class for creating GUI widgets such as buttons, labels, and grid panes.
 */
public class WidgetsClass {

    /**
     * Creates a GridPane containing a button and a label.
     *
     * @param buttonText The text to be displayed on the button.
     * @param labelText The text to be displayed on the label.
     * @return The GridPane containing the button and label.
     */
    public GridPane createWidgetInGridpane(String buttonText, String labelText) {
        GridPane widgetGridpane = new GridPane();
        widgetGridpane.setVgap(2);
        widgetGridpane.setHgap(2);
        Button button1 = new Button(buttonText);
        Label label1 = new Label(labelText);
        button1.getStyleClass().add("buttonWidget");
        label1.getStyleClass().add("labelWidget");
        widgetGridpane.getStyleClass().add("grid-pane");
        widgetGridpane.add(button1,0,1);
        widgetGridpane.add(label1,0,0);

        return widgetGridpane;
    }

    /**
     * Creates a button widget.
     *
     * @param buttonText The text to be displayed on the button.
     * @return The created Button object.
     */
    public Button createWidgetButton(String buttonText) {
        Button button1 = new Button(buttonText);
        button1.getStyleClass().add("buttonWidget");
        return button1;
    }

    /**
     * Creates a label widget.
     *
     * @param labelText The text to be displayed on the label.
     * @return The created Label object.
     */
    public Label createWidgetLabel(String labelText) {
        Label label1 = new Label(labelText);
        label1.getStyleClass().add("labelWidget");
        return label1;
    }

    /**
     * Creates an empty GridPane.
     *
     * @return The created GridPane object.
     */
    public GridPane createWidgetGridpane() {
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        return gridPane;
    }

    /**
     * Wraps the provided GridPane in a container GridPane.
     *
     * @param gridPane The GridPane to be wrapped.
     * @return The container GridPane containing the provided GridPane.
     */
    public GridPane applyContainer(GridPane gridPane) {
        GridPane container = new GridPane();
        container.getStyleClass().add("grid-pane-container");
        container.add(gridPane, 0,0);
        return container;
    }
}
