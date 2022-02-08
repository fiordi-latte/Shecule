package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentMgmt {

    public static LocalDateTime now = LocalDateTime.now();
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static void setAppointments() {
        try {

            String query = "SELECT * FROM appointments";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"));
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"));
                String cid = rs.getString("Customer_ID");
                String uid = rs.getString("User_ID");
                String contactID = rs.getString("Contact_ID");
                String location = rs.getString("Location");
                int id = rs.getInt("Customer_ID");
                Appointment newAppointment = new Appointment();
                newAppointment.setID(id);
                newAppointment.setTitle(title);
                newAppointment.setContactID(contactID);
                newAppointment.setStartTime(start);
                newAppointment.setEndTime(end);
                //newAppointment.setCreateTime(phone);
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
