package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class User {
    private String userName;
    private String userPassword;
    private int userID;
    private boolean isCurrent;
    private static final Connection conn = JDBC.getConnection();
    public static String currentUser;


    public User() throws SQLException {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {this.userName = userName;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }
    public int getID(){
     return userID;
    }

    public boolean isCurrent(){
        this.isCurrent = true;
        return true;
    }


    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    public static void setCurrentUser(String name){
     currentUser = name;
    }



    public static String getCurrentUser(){
        return currentUser;
    }

}

