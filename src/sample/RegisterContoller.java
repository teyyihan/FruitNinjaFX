package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterContoller implements Initializable {

    Connection connection ;

    @FXML
    TextField username;
    @FXML
    TextField passwordtxt;

    public void goToWelcome(ActionEvent event)throws IOException {

        Node node3 =(Node) event.getSource();
        Stage stage3 =(Stage) node3.getScene().getWindow();
        stage3.close();
        Scene scene3 =new Scene(FXMLLoader.load(getClass().getResource("Welcome.fxml")));
        stage3.setTitle("TEYONUN MEYVELERİ");
        stage3.setScene(scene3);
        stage3.show();
    }

    @FXML
    void goToLogin(ActionEvent event) throws IOException {

        String userID = username.getText();
        String password = passwordtxt.getText();

        register(userID,password);

        Node node3 =(Node) event.getSource();
        Stage stage3 =(Stage) node3.getScene().getWindow();
        stage3.close();
        Scene scene3 =new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage3.setTitle("TEYONUN MEYVELERİ");
        stage3.setScene(scene3);
        stage3.show();
    }

    public void register(String username,String password){
        String sql = "INSERT INTO player (username, password) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = ConnectionClass.connectionDB();
    }
}
