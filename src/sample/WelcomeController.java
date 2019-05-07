package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    void goToLogin(MouseEvent event) throws IOException {
        Node node =(Node) event.getSource();
        Stage stage =(Stage) node.getScene().getWindow();
        stage.close();
        Scene scene =new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage.setTitle("gir reis");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void sampleClick2(MouseEvent event) throws IOException {
        Node node2 =(Node) event.getSource();
        Stage stage2 =(Stage) node2.getScene().getWindow();
        stage2.close();
        Scene scene2 =new Scene(FXMLLoader.load(getClass().getResource("Register.fxml")));
        stage2.setTitle("kaydol reis");
        stage2.setScene(scene2);
        stage2.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
