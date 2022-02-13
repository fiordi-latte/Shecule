package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class User {
    private static String userName;
    private String userPassword;
    private int userID;
    private boolean isCurrent;
    public static ObservableList<User> users = FXCollections.observableArrayList();
    private static final Connection conn = JDBC.getConnection();
    private static int currentUserIndex;
    public static String currentUser;


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

    public String getUserName(int id){
        for(User user : users){
            if(user.userID == id){
              return user.userName;
            }
        }
        return "";
    }

    public static ObservableList<User> getUsers(){
        return users;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public static ObservableList<User> getUser(){
        return users;
    }

    public static void setCurrentUser(String name){
     currentUser = name;
    }

    public static int getUserID(String name){
        for(User user: users){
            if(user.getUserName().equals(name)) {
                return user.getID();
            }
        }

        return 0;
    }

    public static String getCurrentUser(){
        return currentUser;
    }

    public static void setUsers() throws SQLException {
        String query = "SELECT * FROM users";
        PreparedStatement sm = conn.prepareStatement(query);
        ResultSet rs = sm.executeQuery(query);
        while (rs.next()) {
            String userName = rs.getString("User_Name");
            int userID = rs.getInt("User_ID");

            User newUser = new User();
            newUser.setUserID(userID);

            newUser.setUserName(userName);


            users.add(newUser);
        }

    }
}

