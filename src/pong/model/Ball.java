package pong.model;

/**
 * A Ball for the Pong game
 *
 * A model class
 */
public class Ball {

    private final double radius;
    private double x;     // Upper left corner in enclosing square
    private double y;
    private double dx;    // Speed
    private double dy;    // Speed

    public Ball(double x, double y, double radius, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
    }

    // TODO Methods
    public void move (){
        x = x + dx;
        y = y + dy;
    }
    public void bouncePaddle () {
        dx = dx *(-1);

    }
    public void bounceWall () {
        dy = dy*(-1);

    }
    public void incBallSpeed () {
        dy = dy * 1.1;
        dx = dx * 1.1;
    }

    // Utilities
    public double getMinX() {
        return x;
    }

    public double getMaxX() {
        return x + 2 * radius;
    }

    public double getMinY() {
        return y;
    }

    public double getMaxY() {
        return y + 2 * radius;
    }

    public double getRadius() {
        return radius;
    }

    public double ballSpeedX() {return  dx;}
}
