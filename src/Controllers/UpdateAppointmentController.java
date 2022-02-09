package Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import util.AppointmentMgmt;
import util.ContactMgmt;
import util.CustomerMgmt;
import util.ErrorCheck;

import java.net.URL;
import java.sql.SQLException;
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
        contact.setItems(ContactMgmt.getContactNames());



        uid = User.getUserID(User.getCurrentUser());

        System.out.println(uid);

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

            int contactID = ContactMgmt.getContactID(contactName);
            int customerID = CustomerMgmt.getCustomerID(customerName);

            if(date == null){
                ErrorCheck.displayError("Please pick a date");
                return;
            }

            LocalDateTime startLT = LocalDateTime.of(date, LocalTime.parse(start));
            LocalDateTime endLT = LocalDateTime.of(date, LocalTime.parse(end));

            int hourStart = startLT.getHour();
            int hourEnd = endLT.getHour();

            boolean officeHours = hourStart >= 8 && hourEnd < 22;
            boolean startBeforeEnd = hourStart < hourEnd;
            if(!officeHours){
                ErrorCheck.displayError("Hours must be between 08:00 and 22:00");
                return;
            }



            if(!startBeforeEnd){
                ErrorCheck.displayError("Start time must be before end time");
                return;
            }





            ZonedDateTime startTime = ZonedDateTime.of(date, LocalTime.parse(start), ZoneId.of("UTC"));
            ZonedDateTime endTime = ZonedDateTime.of(date, LocalTime.parse(end), ZoneId.of("UTC"));

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


            try {
                AppointmentMgmt.addAppointment(newAppointment);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();


        });
    }
}
