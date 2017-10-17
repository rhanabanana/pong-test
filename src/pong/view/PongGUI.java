package pong.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pong.service.SpriteService;
import pong.model.Ball;
import pong.model.Paddle;
import pong.model.Pong;


import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;
import static java.lang.System.out;
import static pong.model.Pong.*;

/*
 * The GUI for the Pong game.
 * No application logic here just GUI and event handling
 *
 * Run this to run the game
 *
 * See: https://en.wikipedia.org/wiki/Pong
 *
 */
public class PongGUI extends Application {



    // The game
    private Pong pong;

    // ------- Keyboard handling ----------------------------------

    private void keyPressed(KeyEvent event) {
        KeyCode kc = event.getCode();
        out.println("Pressed " + kc );
        switch (kc) {
            case UP:
                pong.getRightPaddle().speedUp();
                break;
            case DOWN:
                pong.getRightPaddle().speedDown();
                break;
            case Q:
                pong.getLeftPaddle().speedUp();
                break;
            case A:
                pong.getLeftPaddle().speedDown();
                break;
            default:  // Nothing
        }
    }

    private void keyReleased(KeyEvent event) {
        KeyCode kc = event.getCode();
        switch (kc) {
            case UP:
            case DOWN:
                pong.getRightPaddle().noSpeed();
                break;
            case A:
            case Q:
                pong.getLeftPaddle().noSpeed();
                break;
            default: // Nothing
        }
    }

    // ---- Menu handling -----------------
    private void handleMenu(ActionEvent e) {
        MenuItem mi = (MenuItem) e.getSource();
        //out.println("Click " + mi.getText());

        switch (mi.getText()) {
            case "New":
                newGame();
                break;
            case "Stop":
                killRound();
                break;
            case "Exit":
                exit(0);
                break;
            case "Cool":
                setTheme("Cool");
                break;
            case "Duckie":
                setTheme("Duckie");
                break;
        }
    }

    // ---------- Menu actions ---------------------

    private void newGame() {
        textMenuItem.get("New").setDisable(true);
        textMenuItem.get("Stop").setDisable(false);
        textMenuItem.get("Cool").setDisable(true);
        textMenuItem.get("Duckie").setDisable(true);

        // Build the model
        Paddle pRight = new Paddle(WIDTH - 50, HEIGHT / 2 + PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle pLeft = new Paddle(50, HEIGHT / 2 + PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);

        pong = new Pong(pLeft, pRight);

        pong.newBall(WIDTH/2,HEIGHT / 2,BALL_DIAM/2);

        timer.start();
    }

    private void killRound() {
        timer.stop();
        textMenuItem.get("New").setDisable(false);
        textMenuItem.get("Stop").setDisable(true);
        textMenuItem.get("Cool").setDisable(false);
        textMenuItem.get("Duckie").setDisable(false);
    }

    private void setTheme(String themeName) {
        switch (themeName) {
            case "Cool":
                theme = new Theme(Color.WHITE, "coolBall.png",
                        "coolbluepaddle.png",
                        "coolredpaddle.png",
                        "coolBg.png");
                ((CheckMenuItem) textMenuItem.get("Cool")).setSelected(true);
                ((CheckMenuItem) textMenuItem.get("Duckie")).setSelected(false);
                break;
            case "Duckie":
                theme = new Theme(Color.RED, "duckieBall.png",
                        "coolbluepaddle.png",
                        "coolredpaddle.png",
                        "duckieBg.jpg");
                ((CheckMenuItem) textMenuItem.get("Cool")).setSelected(false);
                ((CheckMenuItem) textMenuItem.get("Duckie")).setSelected(true);
                break;
        }
    }


    // ------------- FX Initialization -----------------------

    @Override
    public void init() {
        // Default theme is "Cool"
        theme = new Theme(Color.WHITE, "coolBall.png",
                "coolbluepaddle.png",
                "coolredpaddle.png",
                "coolBg.png");
    }

    // ---------- Rendering -----------------

    private AnimationTimer timer;
    private Theme theme;
    // For debugging, see renderGame
    private boolean renderDebug = false; //true;

    private void renderGame(GraphicsContext g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);    // Clear everything
        Ball b = pong.getBall();
        Paddle lp = pong.getLeftPaddle();
        Paddle rp = pong.getRightPaddle();

        if (renderDebug) {                    //<----------------------- renderDebug!
            renderDebug(g, lp, rp, b);
        } else {
            g.drawImage(theme.backgroundImg, 0, 0);
            g.setFill(theme.textColor);
            g.fillText("Points: " + pong.getPointsLeft(), 10, 20);
            g.fillText("Points: " + pong.getPointsRight(), 500, 20);
            g.drawImage(theme.paddleLeftImg, lp.getMinX(), lp.getMinY());
            g.drawImage(theme.paddleRightImg, rp.getMinX(), rp.getMinY());
            g.drawImage(theme.ballImg, b.getMinX(), b.getMinY());
        }
    }

    private void renderDebug(GraphicsContext g, Paddle left, Paddle right, Ball b) {
        g.strokeRect(left.getMinX(), left.getMinY(),
                left.getMaxX() - left.getMinX(), left.getMaxY() - left.getMinY());
        g.strokeRect(right.getMinX(), right.getMinY(),
                right.getMaxX() - right.getMinX(), right.getMaxY() - right.getMinY());
        g.strokeRect(b.getMinX(), b.getMinY(),
                b.getMaxX() - b.getMinX(), b.getMaxY() - b.getMinY());
    }

    // ----------- Menu -------------------

    // Mapping the text on the MenuItem to the MenuItem object (easy access)
    private final Map<String, MenuItem> textMenuItem = new HashMap<>();

    private MenuBar getMenu() {
        Menu menuFile = new Menu("File");
        MenuItem[] menuFileItems = {
                getMenuItem("New", false),
                getMenuItem("Stop", true),
                getMenuItem("Exit", false),
        };
        menuFile.getItems().addAll(menuFileItems);
        for (MenuItem mi : menuFileItems) {
            textMenuItem.put(mi.getText(), mi);
        }

        Menu menuTheme = new Menu("Themes");
        MenuItem[] menuThemeItems = new MenuItem[]{
                getCheckMenuItem("Cool", true),
                getCheckMenuItem("Duckie", false),
        };
        menuTheme.getItems().addAll(menuThemeItems);

        for (MenuItem mi : menuThemeItems) {
            textMenuItem.put(mi.getText(), mi);
        }

        // Event handling, same method handles all menu items
        for (MenuItem mi : textMenuItem.values()) {
            mi.setOnAction(this::handleMenu);
        }

        // -- The bar for all menus
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuFile, menuTheme);
        return menuBar;
    }

    private MenuItem getMenuItem(String text, boolean b) {
        MenuItem mi = new MenuItem(text);
        mi.setDisable(b);
        return mi;
    }

    private MenuItem getCheckMenuItem(String text, boolean b) {
        CheckMenuItem mi = new CheckMenuItem(text);
        mi.setSelected(b);
        return mi;
    }

    // -------------- Build Scene and add some nodes ---------------

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        Image pongBg = SpriteService.getImage("pong.jpg", WIDTH, HEIGHT, false);
        root.getChildren().addAll(new ImageView(pongBg));

        // Menu
        MenuBar menu = getMenu();
        menu.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menu);

        //Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.setCenter(canvas);//getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        timer = new AnimationTimer() {
            // Param not used
            public void handle(long currentNanoTime) {
                pong.update();
                renderGame(gc);

            }
        };

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::keyPressed);
        scene.setOnKeyReleased(this::keyReleased);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pong");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
