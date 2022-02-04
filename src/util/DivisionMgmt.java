package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;
import model.JDBC;

import java.sql.*;

public class DivisionMgmt {
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();

    public static ObservableList<Division> divisions = FXCollections.observableArrayList();

    public static ObservableList<Division> getDivisions(){
        try {

            String query = "SELECT * FROM first_level_divisions;";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String divisionName = rs.getString("Division");
                int id = rs.getInt("Division_ID");
                int cid = rs.getInt("Country_ID");
                //int id = rs.getInt("Customer_ID");
                Division newDivision = new Division(divisionName, id);
                //newCountry.setCustID(id);
                newDivision.setName(divisionName);
                newDivision.setCountryID(cid);


                divisions.add(newDivision);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }
}
