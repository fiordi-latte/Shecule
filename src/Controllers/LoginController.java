package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.JDBC;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Button loginButton;
    @FXML
    Label userNameLabel;
    @FXML
    Label passwordLabel;
    @FXML
    TextField userNameInput;
    @FXML
    TextField passwordInput;

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(e -> {
            String userName = userNameInput.getText();
            String password = passwordInput.getText();

            if(verifyLogin(userName, password)){

            }

        });
    }



    public Boolean verifyLogin(String userName, String password)
    {
        try {
            sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT * FROM USERS WHERE User_Name = '" + userName + "' AND Password = '"+ password + "'");
            if(rs.next()){
                String user = rs.getString("User_Name");
                System.out.println(user);
                return Boolean.TRUE;
            }
            else{
                return Boolean.FALSE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
