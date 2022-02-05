package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import model.JDBC;
import java.sql.*;

public class User {
    private static String userName;
    private String userPassword;
    //public static String user;
    public static ObservableList<User> users = FXCollections.observableArrayList();
    private static final Connection conn = JDBC.getConnection();


    public User() throws SQLException {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public static ObservableList<User> getUsers(){
        return users;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public static void setUsers() throws SQLException {
        String query = "SELECT * FROM customers";
        PreparedStatement sm = conn.prepareStatement(query);
        ResultSet rs = sm.executeQuery(query);
        while (rs.next()) {
            String customerName = rs.getString("Customer_Name");
            int id = rs.getInt("Customer_ID");
            User newUser = new User();

            newUser.setUserName(userName);


            users.add(newUser);
        }

    }
}

