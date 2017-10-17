package pong.model;

import javafx.scene.input.KeyCode;

import static pong.model.Pong.HEIGHT;

/**
 * A Paddle for the Pong game
 *
 * A model class
 *
 */
public class Paddle {

    public enum Direction {
        STOP,
        UP,
        DOWN;
    }

    private final double width;
    private final double height;
    private double x;          // Upper left corner in enclosing square
    private double y;
    private double dy;         // Speed

    public Paddle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveP () {
        y = y + dy;
    }
    public void speedDown () {
            dy = 5;
    }
    public void speedUp () {
            dy = -5;
     }
    public void noSpeed () {
        dy = 0;
    }

    // Utilities
    public double getMinX() {
        return x;
    }

    public double getMaxX() {
        return x + width;
    }

    public double getMinY() {
        return y;
    }

    public double getMaxY() {
        return y + height;
    }

}
