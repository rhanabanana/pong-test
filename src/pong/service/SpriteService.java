package pong.service;


import javafx.scene.image.Image;

import java.net.URL;

/*
 *  A service, a class that can support the model
 *
 *    *** Nothing to do here ***
 */
public class SpriteService {

    public static Image getImage(String fileName, double requestedWidth, double requestedHeight, boolean preserveRatio) {
        URL url = SpriteService.class.getResource("../img/" + fileName);
        return new Image(url.toString(), requestedWidth, requestedHeight, preserveRatio, true);
    }

    // Overloaded
    public static Image getImage(String fileName, double requestedWidth, double requestedHeight) {
        URL url = SpriteService.class.getResource("../img/" + fileName);
        return new Image(url.toString(), requestedWidth, requestedHeight, true, true);
    }

}
