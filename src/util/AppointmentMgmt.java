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
import java.time.ZonedDateTime;

public class AppointmentMgmt {

    public static LocalDateTime now = LocalDateTime.now();
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    public static void addAppointment(Appointment appointment) throws SQLException {
        LocalDateTime start = LocalDateTime.from(appointment.getStartTime());
        LocalDateTime end = LocalDateTime.from(appointment.getEndTime());

        String query = "INSERT INTO appointments VALUES ('"+appointment.getId()+"', '"+appointment.getTitle()+"', '" + appointment.getDescription() + "', '" + appointment.getLocation() + "','" +appointment.getType()+ "', '"
                + Timestamp.valueOf(start) + "',' "+ Timestamp.valueOf(end) +"' ,'" + now +"', '"+ User.getCurrentUser() +"', '" + now + "','"  + User.getCurrentUser() + "','" + appointment.getCid() +"','" + appointment.getUid() + "','" + appointment.getContactID() +"')";

        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate();


        appointments.add(appointment);
    }

    public static void updateAppointment(Appointment appointment) throws SQLException{

        LocalDateTime start = LocalDateTime.from(appointment.getStartTime());
        LocalDateTime end = LocalDateTime.from(appointment.getEndTime());
        String query = "UPDATE appointments set Title = '" +appointment.getTitle()+"',  Description = '" + appointment.getDescription() + "', Location = '" + appointment.getLocation() + "', Type = '" +appointment.getType()+ "', Start = '"
                + Timestamp.valueOf(start) + "', End = '"+ Timestamp.valueOf(end) + "', Last_Update  = '" + now + "', Last_Updated_By = '"  + User.getCurrentUser() + "', Customer_ID = '"
                + appointment.getCid() +"', User_ID = '" + appointment.getUid() + "', Contact_ID = '" + appointment.getContactID() + "' WHERE Appointment_ID = '" + appointment.getId() + "'";

        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate();


        int i = appointments.indexOf(appointment);
        System.out.println(i);

        appointments.set(i, appointment);

    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String query = "DELETE FROM appointments where Appointment_ID = '" + appointment.getId() + "'";
        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate(query);
        int i = appointments.indexOf(appointment);
        appointments.remove(i);
    }


    public static void setAppointments() {
        appointments.clear();
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
                int cid = rs.getInt("Customer_ID");
                int uid = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String location = rs.getString("Location");
                int id = rs.getInt("Appointment_ID");

                ZonedDateTime startTime = start.toLocalDateTime().atZone(ZoneId.systemDefault());
                ZonedDateTime endTime = end.toLocalDateTime().atZone(ZoneId.systemDefault());

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
