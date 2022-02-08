package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.JDBC;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AppointmentMgmt {

    public static LocalDateTime now = LocalDateTime.now();
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    public static void addAppointment(Appointment appointment) throws SQLException {
        String query = "INSERT INTO customers VALUES ('"+appointment.getId()+"', '"+appointment.getTitle()+"', '" + appointment.getDescription() + "', '" + appointment.getLocation() + "','" +appointment.getType()+ "', '"
                + Timestamp.valueOf(appointment.getStartTime()) + "',' "+ Timestamp.valueOf(appointment.getEndTime()) +"' ,'" + now +"', '"+ User.getCurrentUser() +"', '" + now + "','"  + User.getCurrentUser() + "','" + appointment.getCid() +"','" + appointment.getUid() + "','" + appointment.getContactID() +"')";

        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate();


        appointments.add(appointment);
    }

    public static void setAppointments() {
        try {

            String query = "SELECT * FROM appointments";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                String cid = rs.getString("Customer_ID");
                String uid = rs.getString("User_ID");
                String contactID = rs.getString("Contact_ID");
                String location = rs.getString("Location");
                String id = rs.getString("Appointment_ID");

                LocalDateTime startTime = start.toLocalDateTime();
                LocalDateTime endTime = end.toLocalDateTime();

                Appointment newAppointment = new Appointment();
                newAppointment.setID(id);
                newAppointment.setTitle(title);
                newAppointment.setContactID(contactID);
                newAppointment.setDescription(description);
                newAppointment.setStartTime(startTime);
                newAppointment.setEndTime(endTime);
                newAppointment.setUid(uid);
                newAppointment.setCid(cid);
                newAppointment.setType(type);
                newAppointment.setLocation(location);



                appointments.add(newAppointment);



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
