package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JDBC;
import model.User;


import java.io.IOException;
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
        userNameInput.setText("admin");
        passwordInput.setText("admin");
        loginButton.setOnAction(e -> {
            String userName = userNameInput.getText();
            String password = passwordInput.getText();

            if(verifyLogin(userName, password)){



                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Calender/Customer");
                stage.setScene(scene);

                stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                User.setUsers();

                System.out.println(user);
                return Boolean.TRUE;
            }
            else{
                Alert newAlert = new Alert(Alert.AlertType.ERROR);
                newAlert.setContentText("Invalid Login Credentials");
                newAlert.showAndWait();

                return Boolean.FALSE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
