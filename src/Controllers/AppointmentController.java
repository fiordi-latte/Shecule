package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import util.AppointmentMgmt;

import java.net.URL;
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

        add.setOnAction(e -> {

        });

        update.setOnAction(e -> {

        });

        delete.setOnAction(e -> {

        });
    }
}
