package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    AnchorPane rootpane = new AnchorPane();

    public void goToMainMenu(ActionEvent event)throws IOException {

        Node node3 =(Node) event.getSource();
        Stage stage3 =(Stage) node3.getScene().getWindow();
        stage3.close();
        Scene scene3 =new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")));
        stage3.setTitle("TEYONUN MEYVELERİ");
        stage3.setScene(scene3);
        stage3.show();
        /*Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.hide();
    */}

    @FXML
    void lsignInClick(MouseEvent event) throws IOException {
        Node node3 =(Node) event.getSource();
        Stage stage3 =(Stage) node3.getScene().getWindow();
        stage3.close();
        Scene scene3 =new Scene(FXMLLoader.load(getClass().getResource("Welcome.fxml")));
        stage3.setTitle("TEYONUN MEYVELERİ");
        stage3.setScene(scene3);
        stage3.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
