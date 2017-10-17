package pong.model;


import javafx.scene.input.KeyCode;
import pong.model.Ball;
import pong.model.Paddle;

import java.util.Random;

import static pong.model.Paddle.Direction.DOWN;
import static pong.model.Paddle.Direction.STOP;
import static pong.model.Paddle.Direction.UP;


/**
 * Logic for the Pong Game
 * Model class representing the "whole" game
 * <p>
 * Nothing visual here
 * <p>
 * See:
 * - week6/samples/catchtherain
 */
public class Pong {

    // Global constants
    public static final int WIDTH = 600;     // Size of window
    public static final int HEIGHT = 400;
    public static final double PADDLE_WIDTH = 10;
    public static final double PADDLE_HEIGHT = 60;
    public static final double BALL_DIAM = 40;
    private static final Random rand = new Random();
    private static final int SPEED = 2;

    private final Paddle lPaddle;
    private final Paddle rPaddle;

    private int pointsLeft;
    private int pointsRight;
    private Ball ball;


    public Pong(Paddle lPaddle, Paddle rPaddle) {
        this.lPaddle = lPaddle;
        this.rPaddle = rPaddle;
    }

    // --------  Game Logic -------------

    // The logic (the game loop)
    public void update() {

        ball.move();

        getLeftPaddle().moveP();
        getRightPaddle().moveP();

        if (ball.getMaxY() >= HEIGHT || ball.getMinY() <= 0) {
            ball.bounceWall(); // REMEMBER : change lower wall
        }
        if (ball.getMaxX() >= WIDTH) {
            newBall(WIDTH / 2, HEIGHT / 2, BALL_DIAM/2);
            pointsLeft++;
        }

        if (ball.getMinX() <= 0) {
            newBall(WIDTH / 2, HEIGHT / 2, BALL_DIAM/2);
            pointsRight++;
        }
        if (ball.ballSpeedX() < 0 && paddleLeftY() && paddleLeftX()){                //LEFT condition (TOP/BOTTOM ONLY)
            ball.bouncePaddle();
            ball.incBallSpeed();
        }
        if (ball.ballSpeedX() > 0 && paddleRightY() && paddleRightX()){                //RIGHT condition  (TOP/BOTTOM ONLY)
            ball.bouncePaddle();
            ball.incBallSpeed();
        }
        if  (ball.ballSpeedX() < 0 && paddleLeftSideY() && paddleLeftSideX() ){
            ball.bouncePaddle();
            ball.incBallSpeed();
        }
        if  (ball.ballSpeedX() > 0 && paddleRightSideY() && paddleRightSideX() ){
            ball.bouncePaddle();
            ball.incBallSpeed();        }
    }

/*
        if (onPaddleYLeft() && onPaddleXLeft()) {
            ball.bouncePaddle();
            ball.incBallSpeed();
        }
        if (onPaddleYRight() && onPaddleXRight()) {
            ball.bouncePaddle();
            ball.incBallSpeed();
        }*/
      /*  if (underPaddleX() ) {
            ball.bounceWall();

        }*/




    //------------------------------- TOP/ BOTTOM OF PADDLES -------------------------------------------------
    //Check if in paddle range (left and right separated) Y axis
    boolean paddleLeftY (){
        boolean bottomPaddleLeft = (getLeftPaddle().getMinY() < ball.getMinY()) && (ball.getMinY() <= getLeftPaddle().getMaxY());
        boolean topPaddleLeft = (getLeftPaddle().getMaxY() > ball.getMaxY()) && (ball.getMaxY() >= getLeftPaddle().getMinY());      //Ball can hit exakt border and inside, but not outside paddle

        return (topPaddleLeft && bottomPaddleLeft);
    }

    boolean paddleRightY (){
        boolean bottomPaddleRight = (getRightPaddle().getMinY() < ball.getMinY()) && (ball.getMinY() <= getRightPaddle().getMaxY());
        boolean topPaddleRight = (getRightPaddle().getMaxY() > ball.getMaxY()) && ball.getMaxY() >= getRightPaddle().getMinY();

        return (topPaddleRight && bottomPaddleRight);
    }

    //Check if ball hits top/bottom on X axis (left and right separated)
    boolean paddleLeftX(){
        boolean leftPaddleLeftX = getLeftPaddle().getMinX() <= ball.getMaxX();
        boolean leftPaddleRightX = getLeftPaddle().getMaxX() > ball.getMinX();
        return leftPaddleLeftX && leftPaddleRightX;
    }

    boolean paddleRightX(){
        boolean rightPaddleLeftX = getRightPaddle().getMinX() < ball.getMaxX();
        boolean rightPaddleRightX = getRightPaddle().getMaxX() >= ball.getMinX();
        return rightPaddleLeftX && rightPaddleRightX;
    }


    //--------------------------------- SIDE OF PADDLES -------------------------------------------------------------


    boolean paddleLeftSideY (){                     //Check if top/bottom of ball is within paddle scope (left)
        double topBall = ball.getMinY();
        double bottomBall = ball.getMaxY();
        boolean leftPaddleTop = getLeftPaddle().getMinY() >= bottomBall;
        boolean leftPaddleBottom = getLeftPaddle().getMaxY() <= topBall;
        return (leftPaddleBottom && leftPaddleTop);
    }

    boolean paddleRightSideY(){
        double topBall = ball.getMinY();
        double bottomBall = ball.getMaxY();
        boolean rightPaddleBottom = getRightPaddle().getMinY() >= bottomBall;
        boolean rightPaddleTop = getRightPaddle().getMaxY() <= topBall;
        return rightPaddleBottom && rightPaddleTop;
    }

    boolean paddleLeftSideX(){
        double rightSideBall = ball.getMaxX();
        double leftSideBall = ball.getMinX();
        double leftPaddleSide = getLeftPaddle().getMaxX();
        double rightPaddleSide = getLeftPaddle().getMinX();

        boolean leftPaddleRightBall = leftPaddleSide <= rightSideBall;
        boolean leftPaddleLeftBall = rightPaddleSide >= leftSideBall;

        return leftPaddleRightBall && leftPaddleLeftBall;
    }

    boolean paddleRightSideX(){
        double rightSideBall = ball.getMaxX();
        double leftSideBall = ball.getMinX();
        double leftPaddleSide = getRightPaddle().getMinX();
        double rightPaddleSide = getRightPaddle().getMaxX();

        boolean rightPaddleRightBall = leftPaddleSide <= rightSideBall;
        boolean rightPaddleLeftBall = rightPaddleSide >= leftSideBall;

        return rightPaddleRightBall && rightPaddleLeftBall;

    }
    /*


    boolean underPaddleX() {

        boolean bottomRight = (ball.getMinY() <= getRightPaddle().getMaxY()) &&
                (ball.getMinX() <= getRightPaddle().getMaxX() && ball.getMaxX() >= getRightPaddle().getMinX());

        boolean bottomLeft =  (ball.getMinY() <= getLeftPaddle().getMaxY()) &&
                (ball.getMinX() <= getLeftPaddle().getMaxX() && ball.getMaxX() >= getLeftPaddle().getMinX());

        boolean topLeft = (ball.getMaxY () >= getLeftPaddle().getMinY()) &&
                (ball.getMaxX() >= getLeftPaddle().getMinX() && ball.getMinX() <= getLeftPaddle().getMaxX()) ;

        boolean topRight = (ball.getMaxY () >= getRightPaddle().getMinY()) &&
                (ball.getMaxX() >= getRightPaddle().getMinX() && ball.getMinX() <= getRightPaddle().getMaxX()) ;


        return (bottomLeft || bottomRight || topLeft || topRight);


    }


    boolean onPaddleYLeft() {

        boolean leftBottomBallSmallerThanPaddle = ball.getMaxY() <= getLeftPaddle().getMaxY();
        boolean leftBottomBallBiggerThanPaddle = getLeftPaddle().getMinY() <= ball.getMaxY();
        boolean leftTopBallBiggerThanPaddle= getLeftPaddle().getMinY() <= ball.getMinY();
        boolean leftTopBallSmallThanPaddle =  ball.getMinY() <= getLeftPaddle().getMaxY();

        return (leftBottomBallBiggerThanPaddle || leftBottomBallSmallerThanPaddle || leftTopBallBiggerThanPaddle || leftTopBallSmallThanPaddle);

    }

    boolean onPaddleYRight () {

        boolean rightInRangeUpDown = (getRightPaddle().getMinY() <= ball.getMaxY() && ball.getMaxY() <= getRightPaddle().getMaxY());
        boolean rightInRangeDownUp = (getRightPaddle().getMinY() <= ball.getMinY() && ball.getMinY() <= getRightPaddle().getMaxY());

        return rightInRangeDownUp && rightInRangeUpDown;
    }


    boolean onPaddleXLeft() {

        boolean leftPaddleX = ball.getMinX() <= getLeftPaddle().getMaxX() && ball.getMinX() >= getLeftPaddle().getMinX() && ball.ballSpeedX() < 0;


        return leftPaddleX;

    }
    boolean onPaddleXRight () {

        boolean rightPaddleX = ball.getMaxX() >= getRightPaddle().getMinX() && ball.getMaxX() <= getRightPaddle().getMaxX() && ball.ballSpeedX() > 0;

        return rightPaddleX;
    }
*/

    // ----- Utility --------------

    public void newBall(double x, double y, double radius) {
        ball = new Ball(x, y, radius, 2, 2);


        int[] dirs = {1, 2, 3 - 1, -2, -3};
        int dx = dirs[rand.nextInt(dirs.length)];
        int dy = dirs[rand.nextInt(dirs.length)];
        ball = new Ball(x, y, radius, dx, dy);   // TIP: For debug use dy = 0

    }

    // --- Used by GUI (rendering) ------------------------

    public Ball getBall() {
        return ball;
    }

    public Paddle getLeftPaddle() {
        return lPaddle;
    }

    public Paddle getRightPaddle() {
        return rPaddle;
    }

    public int getPointsLeft() {
        return pointsLeft;
    }

    public int getPointsRight() {
        return pointsRight;
    }
}
