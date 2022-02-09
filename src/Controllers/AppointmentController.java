package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import util.AppointmentMgmt;
import util.CustomerMgmt;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    @FXML
    public TableView<Appointment> appointmentView;
    @FXML
    public TableColumn<Appointment, String> appTitle;
    @FXML
    public TableColumn<Appointment, String> appDescription;
    @FXML
    public TableColumn<Appointment, String> appLocation;
    @FXML
    public TableColumn<Appointment, String> appType;
    @FXML
    public TableColumn<Appointment, String> appStart;
    @FXML
    public TableColumn<Appointment, String> appEnd;
    @FXML
    public TableColumn<Appointment, String> appContact;
    @FXML
    public TableColumn<Appointment, String> custID;

    @FXML
    public TableColumn<Appointment, String> userID;
    @FXML
    public TableColumn<Appointment, String> appID;
    @FXML
    public Button add;
    @FXML
    public Button update;
    @FXML
    public Button delete;
    public static Appointment selectedAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentMgmt.setAppointments();

        appID.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        custID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        userID.setCellValueFactory(new PropertyValueFactory<>("uid"));

        appointmentView.setItems(AppointmentMgmt.getAppointments());

        delete.setOnAction(e->{
            selectedAppointment = appointmentView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete?");
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {

                    try {
                        AppointmentMgmt.deleteAppointment(selectedAppointment);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        });

        add.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../Views/AddAppointmentForm.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Add Appointment");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        update.setOnAction(e -> {

        });


    }
}
