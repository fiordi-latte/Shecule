package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Country;
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

    public static void setContactNames(){
        for(Contact contact : contacts){
            contactNames.add(contact.getName());
        }

    }

    public static String getContactNameByID(int id){
        for(Contact contact : contacts){
            if(contact.getID() == id){
                return contact.getName();
            }
        }
        return "";
    }

    public static int getContactID(String name){
        for(Contact contact : contacts){
            if(contact.getName().equals(name)){
                return contact.getID();
            }
        }
        return 0;
    }

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
