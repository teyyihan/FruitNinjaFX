package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage = new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Fruit Janissary");
        Scene asd = new Scene(root);
        primaryStage.setScene(asd);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
