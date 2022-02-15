package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.JDBC;

import java.sql.*;

/**
 * Manages country information from database and list
 */

public class CountryMgmt {

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();

    public static ObservableList<Country> countries = FXCollections.observableArrayList();
    public static ObservableList<Country> getCountryList(){
        return countries;

    }

    /**
     * gets the country name from the passed id
     * @param id
     * @return String
     */
    public static String getCountryName(int id){
        for(Country country : countries){
            if(country.getID() == id){
                return country.getName();
            }
        }
        return "";
    }

    /**
     * gets country information from the database and adds it to an observable list
     */
    public static void setCountries(){
        try {

            String query = "SELECT * FROM countries;";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String countryName = rs.getString("Country");
                int id = rs.getInt("Country_ID");
                Country newCountry = new Country(countryName);
                newCountry.setID(id);

                newCountry.setName(countryName);


                countries.add(newCountry);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
