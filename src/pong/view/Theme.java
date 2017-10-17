package pong.view;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import pong.service.SpriteService;
import static pong.model.Pong.*;

/*
   Class to hold a single theme.
   Pure data (a data transfer class)

   *** Nothing to do here ***

*/

import static pong.view.PongGUI.*;

public class Theme {

    public final Color textColor;
    public final Image ballImg;
    public final Image paddleRightImg;
    public final Image paddleLeftImg;
    public final Image backgroundImg;

    public Theme(Color textColor, String ballImgName,
                 String paddleRightImgName, String paddleLeftImgName, String backgroundImgName) {
        this.textColor = textColor;
        this.ballImg = SpriteService.getImage(ballImgName, BALL_DIAM, BALL_DIAM);
        this.paddleRightImg = SpriteService.getImage(paddleRightImgName, PADDLE_WIDTH, PADDLE_HEIGHT, false);
        this.paddleLeftImg = SpriteService.getImage(paddleLeftImgName, PADDLE_WIDTH, PADDLE_HEIGHT, false);
        this.backgroundImg = SpriteService.getImage(backgroundImgName, WIDTH, HEIGHT, false);
    }
}
