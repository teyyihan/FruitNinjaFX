package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Apple extends Fruit {

    public Apple(double centerX, double centerY, double mradius, double xVelocity, double yVelocity) {

        super(centerX, centerY, mradius, xVelocity, yVelocity);
        getView().setFill(new ImagePattern(new Image("file:images/apple.png")));
    }

    @Override
    public void slice(double angle) {
        GameScene.createSemiBalls(0,  this.getCenterX()+10, this.getCenterY()+10, angle,"file:images/apple1.png","file:images/apple2.png");
    }
}
