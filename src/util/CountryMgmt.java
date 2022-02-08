package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.Division;
import model.JDBC;

import java.sql.*;

public class CountryMgmt {

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();

    public static ObservableList<Country> countries = FXCollections.observableArrayList();
    public static ObservableList<Country> getCountryList(){
        return countries;

    }

    public static String getCountryName(int id){
        for(Country country : countries){
            if(country.getID() == id){
                return country.getName();
            }
        }
        return "";
    }

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
