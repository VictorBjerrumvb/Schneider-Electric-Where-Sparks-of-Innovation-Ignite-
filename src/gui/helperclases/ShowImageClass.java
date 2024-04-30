package gui.helperclases;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.io.File;

/**
 * Helper class for displaying images in the GUI.
 */
public class ShowImageClass {

    /**
     * Sets the background image using the provided image file.
     *
     * @param image The name of the image file.
     * @return The background with the specified image.
     */
    public Background setBackGroundImage(String image) {
        BackgroundImage backgroundImage = new BackgroundImage(
                showImage(image), // Your image
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);
        return background;
    }

    /**
     * Loads and returns the image with the specified name.
     *
     * @param imageName The name of the image file.
     * @return The loaded image.
     */
    public Image showImage(String imageName) {
        if (imageName != null) {
            String imagePath = "resources/images/" + imageName;
            Image eventImage = new Image(new File(imagePath).toURI().toString());
            return eventImage;
        }
        return null;
    }
}
