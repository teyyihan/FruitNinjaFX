package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    public void goToGame(ActionEvent event)throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("GameScene.fxml"));

        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.hide();
    }
    @FXML
    void goToWelcome(ActionEvent event) throws IOException {
        Node node3 =(Node) event.getSource();
        Stage stage3 =(Stage) node3.getScene().getWindow();
        stage3.close();
        Scene scene3 =new Scene(FXMLLoader.load(getClass().getResource("Welcome.fxml")));
        stage3.setTitle("TEYONUN MEYVELERİ");
        stage3.setScene(scene3);
        stage3.show();
    }
    @FXML
    void goToLeaderboard(ActionEvent event) throws IOException {
        Node node3 =(Node) event.getSource();
        Stage stage3 =(Stage) node3.getScene().getWindow();
        stage3.close();
        Scene scene3 =new Scene(FXMLLoader.load(getClass().getResource("Leaderboard.fxml")));
        stage3.setTitle("TEYONUN MEYVELERİ");
        stage3.setScene(scene3);
        stage3.show();
    }
}
