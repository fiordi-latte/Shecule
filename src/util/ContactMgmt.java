/**
 * Manages all contact information
 */
package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.JDBC;

import java.sql.*;

public class ContactMgmt {
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();

    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    public static ObservableList<String> contactNames = FXCollections.observableArrayList();

    public static ObservableList<Contact> getContacts(){
        return contacts;
    }

    public static ObservableList<String> getContactNames(){
        return contactNames;
    }

    /**
     * gets a list of only contacts names
     */
    public static void setContactNames(){
        for(Contact contact : contacts){
            contactNames.add(contact.getName());
        }

    }

    /**
     * gets contact name
     * @param id
     * @return String
     */
    public static String getContactNameByID(int id){
        for(Contact contact : contacts){
            if(contact.getID() == id){
                return contact.getName();
            }
        }
        return "";
    }

    /**
     * gets contact id by name
     * @param name
     * @return int
     */
    public static int getContactID(String name){
        for(Contact contact : contacts){
            if(contact.getName().equals(name)){
                return contact.getID();
            }
        }
        return 0;
    }

    /**
     * pulls contact info from database and sets it to an observable list
     */
    public static void setContacts(){
        try {

            String query = "SELECT * FROM contacts;";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                int id = rs.getInt("Contact_ID");
                Contact contact = new Contact();
                contact.setID(id);
                contact.setEmail(contactEmail);
                contact.setName(contactName);


                contacts.add(contact);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setContactNames();
    }

}
