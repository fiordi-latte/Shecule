
package Controllers;

import javafx.collections.ObservableList;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * @Author Andrew Rusnac
 * Controller for the update appointment view
 * Lambda expressions are used to simplify the code for GUI interactions making it more readable and saving time
 * the lambdas also allow for flexibility and reuse
 */

public class UpdateAppointmentController implements Initializable {

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
        public ObservableList<String> contactNames;

        int uid;
        public Appointment selectedApp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedApp = AppointmentController.getSelectedAppointment();
        uid = UserMgmt.getUserID(User.getCurrentUser());

        /**
         * get and fill the contact of selected appointment
         */
        String selectedContact = ContactMgmt.getContactNameByID(selectedApp.getContactID());

        String selectedCustomer = CustomerMgmt.getCustomerNameByID(selectedApp.getCid());

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String selectedStart = selectedApp.getStartTime().format(formatterTime);
        String selectedEnd = selectedApp.getEndTime().format(formatterTime);
        LocalDate selectedDay = selectedApp.getStartTime().toLocalDate();

        appTitle.setText(selectedApp.getTitle());
        appDescription.setText(selectedApp.getDescription());
        appLocation.setText(selectedApp.getLocation());
        appType.setText(selectedApp.getType());
        contact.getSelectionModel().select(selectedContact);
        customer.setText(selectedCustomer);
        appStartTime.setText(selectedStart);
        appEndTime.setText(selectedEnd);
        datePicker.setValue(selectedDay);

        /**
         * lambda expression to handle when the combobox is selected
         */
        contact.setOnMouseClicked(e -> {
            contact.setItems(ContactMgmt.getContactNames());
        });

        /**
         * Lambda expression when the cancel button is pressed
         */

        cancel.setOnAction(e->{
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        });

        /**
         * Lambda expression for when the save button is pressed
         * get all text from the TextFields
         * set them to the updated customer and update them in AppointmentMgmt
         */
        save.setOnAction(e->{
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

            int checkCustomer = CustomerMgmt.getCustomerID(customerName);
            if(checkCustomer == 0){
                ErrorCheck.displayError("Please enter an existing customers name");
                return;
            }

            int contactID = ContactMgmt.getContactID(contactName);
            int customerID = CustomerMgmt.getCustomerID(customerName);

            if(date == null){
                ErrorCheck.displayError("Please pick a date");
                return;
            }

            isValidTime(start);
            isValidTime(end);

            LocalDateTime startLT = LocalDateTime.of(date, LocalTime.parse(start));
            LocalDateTime endLT = LocalDateTime.of(date, LocalTime.parse(end));

            LocalTime businessClose = endLT.toLocalTime();

            if(startLT.isBefore(LocalDateTime.now())){
                ErrorCheck.displayError("Please pick a time in the future");
                return;
            }

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

            ZonedDateTime startTime = startLDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));

            ZonedDateTime endTime = endLDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));

            selectedApp.setLocation(location);
            selectedApp.setTitle(title);
            selectedApp.setDescription(description);
            selectedApp.setType(type);
            selectedApp.setCid(customerID);
            selectedApp.setUid(uid);
            selectedApp.setContactID(contactID);
            selectedApp.setStartTime(startTime);
            selectedApp.setEndTime(endTime);

            if(AddAppointmentController.customerAppExists(customerID, startLDT, endLDT))
            {

                ErrorCheck.displayError("The appointment times overlap with this customers existing appointment");
                return;
            }

            try {
                AppointmentMgmt.updateAppointment(selectedApp);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            AppointmentMgmt.setAppointments();

            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();


        });
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
