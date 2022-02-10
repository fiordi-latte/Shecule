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
import jdk.dynalink.StandardOperation;
import model.JDBC;
import model.User;


import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
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
    @FXML
    public Label zoneId;

    public ZoneId currentZoneId = ZoneId.systemDefault();

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String zid = currentZoneId.toString();

        zoneId.setText(zid);
        userNameInput.setText("admin");
        passwordInput.setText("admin");
        loginButton.setOnAction(e -> {
            String userName = userNameInput.getText();
            String password = passwordInput.getText();

            if(verifyLogin(userName, password)){
                User.setCurrentUser(userNameInput.getText());
                String toWrite = "Successful login by '"+ User.getCurrentUser() +"' at '" + LocalDateTime.now() +"'";
                Path file = Paths.get("login-activity.txt");
                try {
                    Files.write(file, Collections.singleton(toWrite), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Calender/Customer");
                stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
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

                return Boolean.TRUE;
            }
            else{
                String toWrite = "Unsuccessful login by '"+ userName +"' at '" + LocalDateTime.now() +"'";Path file = Paths.get("login-activity.txt");try {    Files.write(file, Collections.singleton(toWrite), StandardCharsets.UTF_8, StandardOpenOption.APPEND);} catch (IOException ex) {    ex.printStackTrace();}

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
