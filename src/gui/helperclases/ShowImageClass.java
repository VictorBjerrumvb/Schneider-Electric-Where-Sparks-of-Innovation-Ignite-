package gui.helperclases;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;

public class ShowImageClass {
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
    public Image showImage(String imageName) {
        if (imageName != null) {
            String imagePath = "resources/images/" + imageName;
            Image eventImage = new Image(new File(imagePath).toURI().toString());
            return eventImage;
        }
        return null;
    }
}
