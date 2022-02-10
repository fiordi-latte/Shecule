package Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;
import util.AppointmentMgmt;
import util.ContactMgmt;
import util.CustomerMgmt;
import util.ErrorCheck;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

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
        private final DateTimeFormatter timePat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        int uid;
        public Appointment selectedApp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedApp = AppointmentController.getSelectedAppointment();
        uid = User.getUserID(User.getCurrentUser());
        System.out.println(selectedApp.getId());
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

        contact.setOnMouseClicked(e -> {
            contact.setItems(ContactMgmt.getContactNames());
        });

        //System.out.println(uid);

        cancel.setOnAction(e->{
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        });

        save.setOnAction(e->{
            int id;
            if(AppointmentMgmt.getAppointments().isEmpty()){
                id = 0;
            }
            else {
                //Appointment appointment = AppointmentMgmt.getAppointments().get(-1);
                id = AppointmentMgmt.getAppointments().get(AppointmentMgmt.getAppointments().size() - 1).getId() + 1;
                //id = appointment.getId();
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

            int hourStart = startLT.getHour();
            int hourEnd = endLT.getHour();


            boolean officeHours = hourStart >= 8 && hourEnd < 22;
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
            System.out.println(selectedApp.getLocalStartTime());

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
