package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import model.JDBC;

import java.sql.*;
/**
 * Gets and manages the information for divisions from the database
 */


public class DivisionMgmt {
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();

    public static ObservableList<Division> divisions = FXCollections.observableArrayList();

    public static ObservableList<Division> getDivisions() {
        return divisions;
    }

    /**
     * Gets division id by the passed name
     * @param name
     * @return int
     */
    public static int getDivisionID(String name){
        for(Division division : divisions){
            if(division.getName() == name){
                return division.getID();
            }
        }
        return 0;
    }

    /**
     * Returns division name from the passed id
     * @param id
     * @return String
     */
    public static String getDivisionName(int id){
        for(Division division : divisions){
            if(division.getID() == id){
                return division.getName();
            }
        }
         return "";
    }

    /**
     * Returns the country name from the passed id
     * @param id
     * @return int
     */
    public static int getCountryName(int id){
        for(Division division : divisions){
            if(division.getID() == id){
                return division.getCountryID();
            }
        }
        return -1;
    }

    /**
     * Gets the division information from the database and adds it to the divisions list
     */
    public static void setDivisions(){
        try {

            String query = "SELECT * FROM first_level_divisions;";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String divisionName = rs.getString("Division");
                int id = rs.getInt("Division_ID");
                int cid = rs.getInt("Country_ID");

                Division newDivision = new Division(divisionName, id);
                newDivision.setName(divisionName);
                newDivision.setCountryID(cid);
                newDivision.setID(id);

                divisions.add(newDivision);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
