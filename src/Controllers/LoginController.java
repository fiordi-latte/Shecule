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
import util.ErrorCheck;
import util.UserMgmt;

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
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.FRANCE;

/**
 * @Author Andrew Rusnac
 * Controller for the login view
 * Lambda expressions are used for GUI interactions to simplify the code and make it more readable
 * the lambdas also allow for flexibility and reuse
 */



public class LoginController implements Initializable {
    @FXML
    public Button loginButton;
    @FXML
    public Label usernameLabel;
    @FXML

    public Label passwordLabel;
    @FXML
    public TextField userNameInput;
    @FXML
    public Label titleLabel;
    @FXML
    public TextField passwordInput;
    @FXML
    public Label zoneId;

    public ZoneId currentZoneId = ZoneId.systemDefault();

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(FRANCE);
        try {
            UserMgmt.setUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(User user : UserMgmt.getUserList()){
            System.out.println("id = " +  user.getID() + "name " + user.getUserName());
        }
        String zid = currentZoneId.toString();
        try {
            resourceBundle = ResourceBundle.getBundle("Properties.login", Locale.getDefault());
            System.out.println(Locale.getDefault());
            zoneId.setText(zid);
            passwordLabel.setText(resourceBundle.getString("password"));
            usernameLabel.setText(resourceBundle.getString("username"));
            titleLabel.setText(resourceBundle.getString("title"));
            loginButton.setText(resourceBundle.getString("login"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        ResourceBundle finalResourceBundle = resourceBundle;

        loginButton.setOnAction(e -> {
            String userName = userNameInput.getText();
            String password = passwordInput.getText();
            System.out.println(userNameInput.getText());

            if(ErrorCheck.isEmpty(userName) || ErrorCheck.isEmpty(password)){
                ErrorCheck.displayError(finalResourceBundle.getString("empty"));
                return;
            }

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


    /**
     * Verifies that the passed credentials are found in the database, additionally writes to
     * login-activity.txt with all login attempts
     *
     * @param userName
     * @param password
     * @return Boolean
     */
    public Boolean verifyLogin(String userName, String password)
    {
        try {
            sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT * FROM USERS WHERE User_Name = '" + userName + "' AND Password = '"+ password + "'");
            if(rs.next()){
                String user = rs.getString("User_Name");



                return Boolean.TRUE;
            }
            else{
                String toWrite = "Unsuccessful login by '"+ userName +"' at '" + LocalDateTime.now() +"'";Path file = Paths.get("login-activity.txt");try {    Files.write(file, Collections.singleton(toWrite), StandardCharsets.UTF_8, StandardOpenOption.APPEND);} catch (IOException ex) {    ex.printStackTrace();}
                ResourceBundle resourceBundle = ResourceBundle.getBundle("Properties.login", Locale.getDefault());
                Alert newAlert = new Alert(Alert.AlertType.ERROR);
                newAlert.setContentText(resourceBundle.getString("invalid"));
                newAlert.showAndWait();

                return Boolean.FALSE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

}
