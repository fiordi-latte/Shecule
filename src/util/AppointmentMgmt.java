package util;

/**
 * Controller for the update customer view
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentMgmt {

    public static LocalDateTime now = LocalDateTime.now();
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static final ObservableList<ContactReport> report = FXCollections.observableArrayList();
    public static final ObservableList<ReportByMonth> reportByMonth = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    /**
     * adds newly created appointment to the database and list
     * @param appointment
     * @throws SQLException
     */
    public static void addAppointment(Appointment appointment) throws SQLException {
        LocalDateTime start = LocalDateTime.from(appointment.getStartTime());
        LocalDateTime end = LocalDateTime.from(appointment.getEndTime());

        String query = "INSERT INTO appointments VALUES ('"+appointment.getId()+"', '"+appointment.getTitle()+"', '" + appointment.getDescription() + "', '" + appointment.getLocation() + "','" +appointment.getType()+ "', '"
                + Timestamp.valueOf(start) + "',' "+ Timestamp.valueOf(end) +"' ,'" + now +"', '"+ User.getCurrentUser() +"', '" + now + "','"  + User.getCurrentUser() + "','" + appointment.getCid() +"','" + appointment.getUid() + "','" + appointment.getContactID() +"')";

        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate();


        appointments.add(appointment);
    }

    /**
     * updates selected appointment
     * @param appointment
     * @throws SQLException
     */
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

    /**
     * Removes selected appointment
     * @param appointment
     * @throws SQLException
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String query = "DELETE FROM appointments where Appointment_ID = '" + appointment.getId() + "'";
        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate(query);
        int i = appointments.indexOf(appointment);
        appointments.remove(i);
    }

    /**
     * Creates and returns a list of customers associated with appointment months
     * @return
     * @throws SQLException
     */
    public static ObservableList<ReportByMonth> reportByMonths() throws SQLException{
        reportByMonth.clear();
        String query = "SELECT DATE_FORMAT(Start, '%M') AS month, COUNT(start) AS count FROM Appointments GROUP BY month;";

        PreparedStatement sm = conn.prepareStatement(query);
        ResultSet rs = sm.executeQuery(query);
        while (rs.next()) {
            String month = rs.getString("month");
            int count = rs.getInt("count");
            ReportByMonth newReport = new ReportByMonth();
            newReport.setReportMonth(month);
            newReport.setReportCount(count);
            reportByMonth.add(newReport);

        }
        return reportByMonth;
    }

    /**
     * Returns a list of customers associated with an appointment type
     * @return ObservalbeList reportByMonth
     * @throws SQLException
     */
    public static ObservableList<ReportByMonth> reportByType() throws SQLException{
        reportByMonth.clear();
        String query = "SELECT Type AS type, COUNT(Customer_ID) AS count FROM Appointments GROUP BY type;";

        PreparedStatement sm = conn.prepareStatement(query);
        ResultSet rs = sm.executeQuery(query);
        while (rs.next()) {
            String type = rs.getString("type");
            //System.out.println(month);
            int count = rs.getInt("count");
            ReportByMonth newReport = new ReportByMonth();
            newReport.setReportMonth(type);
            newReport.setReportCount(count);
            reportByMonth.add(newReport);

        }
        return reportByMonth;
    }

    /**
     * returns a list of number of customers matched to locations
     * @return ObservableList reportByMonth
     * @throws SQLException
     */
    public static ObservableList<ReportByMonth> reportByLocation() throws SQLException{
        reportByMonth.clear();
        String query = "SELECT Location AS location, COUNT(Customer_ID) AS count FROM Appointments GROUP BY location;";

        PreparedStatement sm = conn.prepareStatement(query);
        ResultSet rs = sm.executeQuery(query);
        while (rs.next()) {
            String type = rs.getString("location");
            //System.out.println(month);
            int count = rs.getInt("count");
            ReportByMonth newReport = new ReportByMonth();
            newReport.setReportMonth(type);
            newReport.setReportCount(count);
            reportByMonth.add(newReport);

        }
        return reportByMonth;
    }

    /**
     * Returns a list for the contacts by appointment
     * @param contactID
     * @return ObservableList report
     * @throws SQLException
     */
    public static ObservableList<ContactReport> reportContacts(int contactID) throws SQLException {
        report.clear();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID FROM appointments WHERE Contact_ID = '" + contactID + "'";
        PreparedStatement sm = conn.prepareStatement(query);
        ResultSet rs = sm.executeQuery(query);
        while (rs.next()) {
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int cid = rs.getInt("Customer_ID");
            int id = rs.getInt("Appointment_ID");
            String location = rs.getString("Location");

            ZonedDateTime startTime = start.toLocalDateTime().atZone(ZoneId.systemDefault());
            ZonedDateTime endTime = end.toLocalDateTime().atZone(ZoneId.systemDefault());

            ContactReport contactReport = new ContactReport();
            contactReport.setAppEnd(endTime);
            contactReport.setAppStart(startTime);
            contactReport.setAppId(id);
            contactReport.setCustomerId(cid);
            contactReport.setAppType(type);
            contactReport.setAppDescription(description);
            contactReport.setAppTitle(title);
            contactReport.setLocation(location);

            report.add(contactReport);

        }
        return report;
    }

    /**
     * get and set all appointments from the database to an observablelist
     */
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
