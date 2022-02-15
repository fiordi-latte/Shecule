package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.JDBC;
import model.User;

import java.sql.*;

public class UserMgmt {

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static final ObservableList<User> users = FXCollections.observableArrayList();

    public static ObservableList<User> getUserList(){
        return users;
    }

    public String getUserName(int id){
        for(User user : users){
            if(user.getID() == id){
                return user.getUserName();
            }
        }
        return "";
    }

    public static int getUserID(String name){
        for(User user: users){
            if(user.getUserName().equals(name)) {
                System.out.println("true");
                return user.getID();
            }
        }

        return 0;
    }

    public static void setUsers() throws SQLException {
        users.clear();
        String query = "SELECT * FROM users";
        PreparedStatement sm = conn.prepareStatement(query);
        ResultSet rs = sm.executeQuery(query);
        while (rs.next()) {
            String userName = rs.getString("User_Name");
            int userID = rs.getInt("User_ID");

            User newUser = new User();
            newUser.setUserID(userID);
            System.out.println(userID + userName);
            newUser.setUserName(userName);


            users.add(newUser);
        }

    }
}
