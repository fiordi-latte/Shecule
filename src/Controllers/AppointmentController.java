package Controllers;

import javafx.collections.transformation.FilteredList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the main appointment view.
 * Lambda expressions are used to simplify the code for GUI interactions. Saving time while also making the code more readable
 */

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
    @FXML
    public Button previous;
    @FXML
    public Button next;
    @FXML
    public Label titleLabel;
    @FXML
    public RadioButton weeklyRadio;
    @FXML
    public RadioButton monthlyRadio;
    @FXML
    public RadioButton allRadio;
    ZonedDateTime today = ZonedDateTime.now();

    public static int toAdd = 0;
    public static int monthToAdd = 0;

    public static Appointment selectedAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appID.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("formattedStartTime"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("formattedEndTime"));
        custID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        userID.setCellValueFactory(new PropertyValueFactory<>("uid"));

        AppointmentMgmt.setAppointments();
        appointmentView.setItems(AppointmentMgmt.getAppointments());

        /**
         * Lambda expression to handle when the radio button for "All" is pressed
         * showing all appointments
         */
        allRadio.setOnAction(e->{
            appointmentView.setItems(AppointmentMgmt.getAppointments());
        });

        /**
         * Lambda expression to handle when the radio button for "weekly" is pressed
         * only showing appointments by week
         */
        weeklyRadio.setOnAction(e->{
            FilteredList<Appointment> filtered = new FilteredList<>(AppointmentMgmt.getAppointments());
            filtered.setPredicate(p->  p.getStartTime().isAfter(today.now()) && p.getStartTime().isBefore(today.plus(Period.ofWeeks(1))));

            appointmentView.setItems(filtered);
        });

        /**
         * Lambda expression to handle when the radio button for "monthly" is pressed
         * only showing appointments by month
         */
        monthlyRadio.setOnAction(e->{
            monthToAdd = today.getMonthValue();
            FilteredList<Appointment> filtered = new FilteredList<>(AppointmentMgmt.getAppointments());

            filtered.setPredicate(p->  p.getStartTime().isAfter(today.now()) && p.getStartTime().getMonthValue() == today.getMonthValue());

            appointmentView.setItems(filtered);
        });

        /**
         * Lambda expression to handle when the next button is pressed
         */
        next.setOnAction(e->{
            if(weeklyRadio.isSelected()){
                toAdd += 1;
                filterWeekly(toAdd);
            }
            if(monthlyRadio.isSelected()) {
                if(monthToAdd != 12) {
                    monthToAdd += 1;
                }
                else{monthToAdd =1;}

                filterMonthly(monthToAdd);
            }
        });

        /**
         * Lambda expression to handle when the previous button is pressed
         */
        previous.setOnAction(e->{
            if(monthlyRadio.isSelected()) {
                if (monthToAdd != 1) {
                    monthToAdd -= 1;
                }

                if (monthToAdd == 0) {
                    monthToAdd = 12;
                }
                System.out.println(monthToAdd);
                filterMonthly(monthToAdd);
            }

            if(weeklyRadio.isSelected()) {
                filterWeekly(toAdd);
                if(toAdd >= 0) {
                    toAdd -= 1;
                }
            }
        });

        /**
         * Lambda expression to handle when the delete button is pressed
         */
        delete.setOnAction(e->{
            selectedAppointment = appointmentView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete? Appointment ID: " + selectedAppointment.getId() + " Type: " + selectedAppointment.getType());
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

        /**
         * Lambda expression to handle when the add button is pressed
         */
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

        /**
         * Lambda expression to handle when the update button is pressed
         */
        update.setOnAction(e -> {
            selectedAppointment = appointmentView.getSelectionModel().getSelectedItem();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../Views/UpdateAppointmentForm.fxml"));

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




    }

    /**
     * Increments or decrements when next or previous are pressed, selecting appointsments by week
     * @param numToAdd
     */
    public void filterWeekly(int numToAdd){
        int toAdd = numToAdd;
        FilteredList<Appointment> filtered = new FilteredList<>(AppointmentMgmt.getAppointments());
        /**
         * Lambda expression to set the predicate for the filtered list
         */
        filtered.setPredicate(p-> p.getStartTime().isAfter(today.plus(Period.ofWeeks(toAdd))) && p.getStartTime().isBefore(today.plus(Period.ofWeeks(toAdd+1))));
        appointmentView.setItems(filtered);

    }

    public void filterMonthly(int numToAdd){
        int toAdd = numToAdd;
        FilteredList<Appointment> filtered = new FilteredList<>(AppointmentMgmt.getAppointments());
        /**
         * Lambda expression to set the predicate for the filtered list
         * Used because it makes the code much more compact and simple
         * which makes it more readable and saves time in coding.
         * This interface is also only used here
         */
        filtered.setPredicate(p-> p.getStartTime().getMonthValue() == toAdd && p.getStartTime().isAfter(today.now()));

        appointmentView.setItems(filtered);
    }

    public static Appointment getSelectedAppointment(){
        return selectedAppointment;
    }
}
