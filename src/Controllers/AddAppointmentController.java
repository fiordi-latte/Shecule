package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import util.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 *
 * Controller for the AddApointment view<br>
 * Lambda expressions are used for GUI interactions to simplify the code and make it more readable
 * the lambdas also allow for flexibility and reuse
 */

public class AddAppointmentController implements Initializable {
    @FXML
    public TextField appTitle;
    @FXML
    public TextField appDescription;
    @FXML
    public TextField appType;
    @FXML
    public TextField appLocation;
    @FXML
    public TextField appStartTime;
    @FXML
    public TextField appEndTime;
    @FXML
    public ComboBox<String> contact;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Button save;
    @FXML
    public TextField customer;
    @FXML
    public Button cancel;
    int uid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contact.setItems(ContactMgmt.getContactNames());

        uid = UserMgmt.getUserID(User.getCurrentUser());

        /**
         * lambda expression to handle when cancel is pressed
         */
        cancel.setOnAction(e->{
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        });

        /**
         * lambda to handle when the save button is pressed
         */
        save.setOnAction(e->{
            int id;
            if(AppointmentMgmt.getAppointments().isEmpty()){
                id = 0;
            }
            else {
                id = AppointmentMgmt.getAppointments().get(AppointmentMgmt.getAppointments().size() - 1).getId() + 1;
            }
            String title = appTitle.getText();
            String description = appDescription.getText();
            String type = appType.getText();
            String location = appLocation.getText();
            String start = appStartTime.getText();
            String end = appEndTime.getText();
            LocalDate date = datePicker.getValue();
            String contactName = contact.getValue();
            String customerName = customer.getText();

            if(ErrorCheck.isEmpty(location) || ErrorCheck.isEmpty(title) || ErrorCheck.isEmpty(description) || ErrorCheck.isEmpty(type) || ErrorCheck.isEmpty(start)
                    || ErrorCheck.isEmpty(end) || ErrorCheck.isEmpty(contactName) || ErrorCheck.isEmpty(customerName)){
                Alert newAlert = new Alert(Alert.AlertType.ERROR);
                newAlert.setContentText("Fill all required fields");
                newAlert.showAndWait();
                return;
            }

            int contactID = ContactMgmt.getContactID(contactName);
            int customerID = CustomerMgmt.getCustomerID(customerName);

            if(customerID == 0){
                ErrorCheck.displayError("Please enter an existing customers name");
                return;
            }

            if(date == null){
                ErrorCheck.displayError("Please pick a date");
                return;
            }
            isValidTime(start);
            isValidTime(end);
            LocalDateTime startLT = LocalDateTime.of(date, LocalTime.parse(start));
            LocalDateTime endLT = LocalDateTime.of(date, LocalTime.parse(end));

            LocalTime businessClose = endLT.toLocalTime();
            int hourStart = startLT.getHour();
            int hourEnd = endLT.getHour();


            boolean officeHours = hourStart >= 8 && businessClose.isBefore(LocalTime.parse("22:01"));
            boolean startBeforeEnd = startLT.isBefore(endLT);

            if(!officeHours){
                ErrorCheck.displayError("Hours must be between 08:00 and 22:00");
                return;
            }

            if(!startBeforeEnd){
                ErrorCheck.displayError("Start time must be before end time");
                return;
            }

            LocalDateTime startLDT = LocalDateTime.of(date, LocalTime.parse(start));
            LocalDateTime endLDT = LocalDateTime.of(date, LocalTime.parse(end));

            if(startLT.isBefore(LocalDateTime.now())){
                ErrorCheck.displayError("Please pick a time in the future");
                return;
            }

            ZonedDateTime startTime = startLDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endTime = endLDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));

            Appointment newAppointment = new Appointment();
            newAppointment.setLocation(location);
            newAppointment.setTitle(title);
            newAppointment.setDescription(description);
            newAppointment.setType(type);
            newAppointment.setID(id);
            newAppointment.setCid(customerID);
            newAppointment.setUid(uid);
            newAppointment.setContactID(contactID);
            newAppointment.setStartTime(startTime);
            newAppointment.setEndTime(endTime);

            if(customerAppExists(customerID, startLDT, endLDT)){
                ErrorCheck.displayError("Customer is already scheduled for that time");
                return;
            }

            try {
                AppointmentMgmt.addAppointment(newAppointment);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            AppointmentMgmt.setAppointments();
            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();
        });
    }

    /**
     * Check that this customer doesn't already have an exisiting appointment at these times
     * @param custId
     * @param time
     * @param end
     * @return true if this customer has an appointment scheduled at that time, false if they do not
     */
    public static boolean customerAppExists(int custId, LocalDateTime time, LocalDateTime end){

       ZonedDateTime startTime = time.atZone(ZoneId.systemDefault());
        ZonedDateTime endTime = end.atZone(ZoneId.systemDefault());

        for (Appointment appointment : AppointmentMgmt.getAppointments()){

            if(startTime.isAfter(appointment.getLocalStartTime()) && startTime.isBefore(appointment.getLocalEndTime()) || endTime.isAfter(appointment.getLocalStartTime()) && endTime.isBefore(appointment.getLocalEndTime()) || startTime.isEqual(appointment.getLocalStartTime())){

                if(appointment.getCid() == custId){

                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Return false if the format is not correct
     * @param dateStr
     * @return
     */
    public boolean isValidTime(String dateStr){
        try{
            LocalTime.parse(dateStr);
        } catch (Exception e){
            ErrorCheck.displayError("Enter valid time in format of HH:mm");
            return false;

        }
        return true;
    }
}
