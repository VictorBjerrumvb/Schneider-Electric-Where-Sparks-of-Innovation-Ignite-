package gui.helperclases;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

/**
 * Helper class for creating GUI widgets such as buttons, labels, and grid panes.
 */
public class WidgetsClass {

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

    public MFXPasswordField createWidgetMFXPassword() {
        MFXPasswordField txt = new MFXPasswordField();
        txt.setMinWidth(180);
        return txt;
    }

    public ListView createWidgetListview() {
        ListView lst = new ListView();
        lst.setMinWidth(400);
        lst.setMinHeight(400);
        return lst;
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
        container.add(gridPane, 0, 0);
        return container;
    }
}
