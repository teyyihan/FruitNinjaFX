package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    public static Connection connectionDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root", "pass123");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
