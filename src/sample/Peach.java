package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Peach extends Fruit {

    public Peach(double centerX, double centerY, double mradius, double xVelocity, double yVelocity) {

        super(centerX, centerY, mradius, xVelocity, yVelocity);
        getView().setFill(new ImagePattern(new Image("file:images/peach.png")));
    }

    @Override
    public void slice(double angle) {
        GameScene.createSemiBalls(0,  this.getCenterX()+10, this.getCenterY()+10, angle,"file:images/peach1.png","file:images/peach2.png");

    }
}
