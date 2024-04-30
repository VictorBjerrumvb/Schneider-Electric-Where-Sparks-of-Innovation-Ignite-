import gui.controller.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class to launch the JavaFX application.
 */
public class Main extends Application {

    /**
     * Main method to launch the JavaFX application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch(args);
    }

    /**
     * Start method called when the JavaFX application is launched.
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws Exception If an error occurs during startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the login view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/LoginView.fxml"));
        Parent root = loader.load();

        // Set the scene with the login view
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Schneider"); // Set the title of the stage
        primaryStage.setMaximized(false); // Set the initial window to not be maximized

        // Get the controller associated with the login view
        LoginViewController controller = loader.getController();

        // Show the primary stage
        primaryStage.show();
    }
}
