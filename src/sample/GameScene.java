package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.Math.*;
import static java.lang.Math.sin;
import static sample.Main.primaryStage;

public class GameScene implements Initializable {

    public static int score=0 ;
    public static int fail=0;
    private static final double RADIUS = 25 ;
    private static final double SPEED = 250 ;
    //variable for making game harder more and more
    public double delay = 50.0;
    public double angle;

    private ObservableList<Sliceable> fruits = FXCollections.observableArrayList();
    //List that stores sliced fruits
    private static ObservableList<HalfFruit> semifruits = FXCollections.observableArrayList();

    //class for info about framse
    private final FrameStats frameStats = new FrameStats() ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Pane ballContainer = new Pane();

        BackgroundImage myBI= new BackgroundImage(new Image("file:images/background.jpg",1000,1000,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        ballContainer.setBackground(new Background(myBI));

        //this is our main loop that creates fruits and checks if game is over
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                //creates fruits or bombs every specific time
                if(frameStats.getFrameCount()%100000000==delay  ) {

                    createBalls( RADIUS, SPEED, 150+ random()*500, 600);

                    //making game harder
                    if(score<30){delay+=50;
                    }
                    else if(score>=30 && score<60){delay+=40;}
                    else {delay +=20;}

                }
                //Removing semiballs
                for (int i = 0; i < fruits.size(); i++) {
                    if( fruits.get(i).getCenterY()-fruits.get(i).getRadius()>ballContainer.getHeight()+1 || fruits.get(i).getCenterX()-fruits.get(i).getRadius()>ballContainer.getWidth()+1 || (fruits.get(i).getCenterX()+fruits.get(i).getRadius())<0) {
                        if(fruits.get(i) instanceof Fruit){
                            fail++;}
                        fruits.remove(fruits.get(i));
                    }}
                if(fail>3){
                    //game over method


                }
            }

        }.start();

        //this is our mouse event that checks if sliceable object is sliced. We used only entry point to slice a fruit.
        //so blade doesn't have to exit the fruit



        ballContainer.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        for (int i = 0; i < fruits.size(); i++) {
                            //if(Math.sqrt((Math.abs(event.getX()-balls.get(i).getCenterX())*Math.abs(event.getX()-balls.get(i).getCenterX()))+(Math.abs(event.getY()-balls.get(i).getCenterY())*Math.abs(event.getY()-balls.get(i).getCenterY())))<=balls.get(i).getRadius()) {
                            if((Math.abs(fruits.get(i).getCenterX() - event.getX()) <= fruits.get(i).getRadius()) && (Math.abs(fruits.get(i).getCenterY() - event.getY()) <= fruits.get(i).getRadius())) {

                                if(event.getX()>=fruits.get(i).getCenterX() && event.getY()<=fruits.get(i).getCenterY()) { angle = Math.abs(Math.asin((event.getX()-fruits.get(i).getCenterX())/fruits.get(i).getRadius()))*57.3; }
                                else if(event.getX()>=fruits.get(i).getCenterX() && event.getY()>=fruits.get(i).getCenterY() ) {angle= 90+Math.abs(Math.asin((Math.abs(event.getY()-fruits.get(i).getCenterY()))/fruits.get(i).getRadius()))*57.3;}
                                else if(event.getX()<=fruits.get(i).getCenterX() && event.getY()>=fruits.get(i).getCenterY() ) {angle= 180+Math.abs(Math.asin((Math.abs(event.getX()-fruits.get(i).getCenterX()))/fruits.get(i).getRadius()))*57.3;}
                                else { angle= 270+Math.abs(Math.asin((event.getY()-fruits.get(i).getCenterY())/fruits.get(i).getRadius())*57.3); }

                                //increasing score by fruits' scores
                                if(fruits.get(i) instanceof Apple){
                                    fruits.get(i).slice(angle);
                                    score+=3;
                                    createSlashTrace(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), angle, ballContainer );
                                }
                                else if(fruits.get(i) instanceof Peach){
                                    fruits.get(i).slice(angle);
                                    score+=5;
                                    createSlashTrace(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), angle, ballContainer );
                                }
                                else if(fruits.get(i) instanceof Lemon){
                                    fruits.get(i).slice(angle);
                                    score+=4;
                                    createSlashTrace(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), angle, ballContainer );
                                }
                                else if(fruits.get(i) instanceof Watermelon){
                                    //Watermelon
                                    fruits.get(i).slice(angle);
                                    score+=2;
                                    createSlashTrace(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), angle, ballContainer );
                                }
                                else {fruits.get(i).slice(angle);}

                                fruits.remove(fruits.get(i));
                            }}
                    }

                });


        //if a fruit or bomb is created or vanished, it prints or makes it vanished on screen
        fruits.addListener(new ListChangeListener<Sliceable>() {
            @Override
            public void onChanged(Change<? extends Sliceable> change) {
                while (change.next()) {
                    for (Sliceable b : change.getAddedSubList()) {
                        ballContainer.getChildren().add(b.getView());
                    }
                    for (Sliceable b : change.getRemoved()) {
                        ballContainer.getChildren().remove(b.getView());
                    }
                }
            }
        });

        //if a sliced fruit (halffruit) is created or vanished, it prints or makes it vanished on screen
        semifruits.addListener(new ListChangeListener<HalfFruit>() {
            @Override
            public void onChanged(Change<? extends HalfFruit> change) {
                while (change.next()) {
                    for (HalfFruit b : change.getAddedSubList()) {
                        ballContainer.getChildren().add(b.getView());
                    }
                    for (HalfFruit b : change.getRemoved()) {
                        ballContainer.getChildren().remove(b.getView());
                    }
                }
            }
        });

        //creates first fruit
        createBalls( RADIUS, SPEED, 400, 600);


        final BorderPane root = new BorderPane();
        final Label stats = new Label();
        stats.textProperty().bind(frameStats.textProperty());

        root.setCenter(ballContainer);
        root.setBottom(stats);

        final Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        startAnimation(ballContainer);


    }


    public void createSlashTrace(double initalX, double initalY, double angle1,Pane pane )
    {

        // Get angle of incline with +ve X axis
        double angle = angle1;

        // Define the slash image and create the object for it
        ImageView trace = new ImageView("file:images/splash.png");

        // Set rotate angle we calculated, 45 hard-coded value
        trace.setRotate(angle+10);

        trace.setFitWidth(50);
        trace.setFitHeight(50);

        // Firstly make the slash invisible
        trace.setVisible(false);

        // Tie the trace with our full watermelon layout x, y
        trace.setLayoutX(initalX);
        trace.setLayoutY(initalY);

        // Add the trace to the pane
        pane.getChildren().add(trace);

        // Make animation to show the trace just for 300 millisecond
        Timeline showSlash = new Timeline(
                new KeyFrame(Duration.ZERO, e ->
                {
                    trace.setVisible(true);
                }
                ),
                new KeyFrame(Duration.millis(300), e->
                {
                    trace.setVisible(false);
                }
                )
        );

        showSlash.setCycleCount(1);
        showSlash.play();
    }

    //updates screen
    private void startAnimation(final Pane ballContainer) {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0) {
                    long elapsedTime = timestamp - lastUpdateTime.get();
                    updateWorld(elapsedTime);
                    frameStats.addFrame(elapsedTime);
                }
                lastUpdateTime.set(timestamp);
            }


        };
        timer.start();
    }

    //this is the part where fruits and semifruits move (translate) by gravity
    private void updateWorld(long elapsedTime) {
        double elapsedSeconds = elapsedTime / 1_000_000_000.0;
        double zorluk = 4.6;

        for (HalfFruit b : semifruits) {
            b.setCenterX(b.getCenterX() + elapsedSeconds * b.getXVelocity());
            b.setCenterY((b.getCenterY() + elapsedSeconds * b.getYVelocity()) - zorluk * b.getGravity());
            b.setGravity(b.getGravity() - 0.05);
        }

        for (Sliceable b : fruits) {
            b.setCenterX(b.getCenterX() + elapsedSeconds * b.getXVelocity());
            b.setCenterY((b.getCenterY() + elapsedSeconds * b.getYVelocity()) - zorluk * b.getGravity());
            b.setGravity(b.getGravity() - 0.02);
        }
    }

    //this is the method to create fruits
    private void createBalls( double mradius,double mspeed, double initialX, double initialY) {

        final Random rng = new Random();
        double angle = 2 * PI * rng.nextDouble();


        int random = rng.nextInt(5);

        switch (random){

            case 0: //apple
                fruits.add(new Apple(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 1: //lemon
                fruits.add(new Lemon(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 2: //peach
                fruits.add(new Peach(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 3: //watermelon
                fruits.add(new Watermelon(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 4: //bomb
                fruits.add(new Bomb(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            default: break;

        }
    }

    //this is the method to create half fruits
    public static void createSemiBalls( double mspeed, double initialX, double initialY, double angle1, String path, String path2) {
        final Random rng = new Random();

        final double angle = 2 * PI * rng.nextDouble();

        HalfFruit semifruit1 = new HalfFruit(initialX, initialY, mspeed*cos(angle),
                -Math.abs(mspeed*sin(angle)), angle1 );
        semifruit1.getView().setImage(new Image(path));
        semifruit1.getView().setFitHeight(50);
        semifruit1.getView().setFitWidth(25);

        HalfFruit semifruit2 = new HalfFruit(initialX, initialY, mspeed*cos(angle),
                -Math.abs(mspeed*sin(angle)), angle1 );
        semifruit2.getView().setImage(new Image(path2));
        semifruit2.getView().setFitHeight(50);
        semifruit2.getView().setFitWidth(25);


        if(angle1<90){
            semifruit1.getView().setX(initialX-25);
            semifruit1.getView().setY(initialY-25);
            semifruit2.getView().setX(initialX);
            semifruit2.getView().setY(initialY);
            semifruit1.getView().setRotate(angle1);
            semifruit2.getView().setRotate(angle1);
        }
        else if(angle1>90 && angle1<180){
            semifruit1.getView().setX(initialX);
            semifruit1.getView().setY(initialY);
            semifruit2.getView().setX(initialX+25);
            semifruit2.getView().setY(initialY-25);
            semifruit1.getView().setRotate(angle1-180);
            semifruit2.getView().setRotate(angle1-180);
        }
        else if(angle1>180 && angle1<270){
            semifruit1.getView().setX(initialX);
            semifruit1.getView().setY(initialY);
            semifruit2.getView().setX(initialX+25);
            semifruit2.getView().setY(initialY+25);
            semifruit1.getView().setRotate(angle1-180);
            semifruit2.getView().setRotate(angle1-180);
        }
        else {
            semifruit1.getView().setX(initialX-25);
            semifruit1.getView().setY(initialY+25);
            semifruit2.getView().setX(initialX);
            semifruit2.getView().setY(initialY);
            semifruit1.getView().setRotate(angle1);
            semifruit2.getView().setRotate(angle1);
        }

        semifruits.add(semifruit1);
        semifruits.add(semifruit2);
    }


    //inner class for frames
    private static class FrameStats {
        private long frameCount ;
        private double meanFrameInterval ; // millis
        private final ReadOnlyStringWrapper text = new ReadOnlyStringWrapper(this, "text", "Frame count: 0 Average frame interval: N/A");



        public long getFrameCount() {
            return frameCount;
        }
        public double getMeanFrameInterval() {
            return meanFrameInterval;
        }

        public void addFrame(long frameDurationNanos) {
            meanFrameInterval = (meanFrameInterval * frameCount + frameDurationNanos / 1_000_000.0) / (frameCount + 1) ;
            frameCount++ ;
            text.set(toString());
        }

        public String getText() {
            return text.get();
        }

        public ReadOnlyStringProperty textProperty() {
            return text.getReadOnlyProperty() ;
        }

        @Override
        public String toString() {
            return String.format("SCORE: %d, FAIL: %d",score, fail);

            //return String.format("Frame count: %,d Average frame interval: %.3f milliseconds Score: %d, Fail: %d", getFrameCount(), getMeanFrameInterval(), score, fail);
        }
    }

}
