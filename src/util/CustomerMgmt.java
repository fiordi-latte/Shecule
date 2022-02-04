package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.JDBC;

import java.sql.*;

public class CustomerMgmt {

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public void addCustomers(Customer customer) {
        allCustomers.add(customer);
    }

    public static ObservableList<Customer> getCustomers() {
        try {

            String query = "SELECT * FROM customers";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                int id = rs.getInt("Customer_ID");
                //String address = rs.getString()
                Customer newCustomer = new Customer(customerName);
                newCustomer.setCustID(id);

                System.out.println(customerName);
                System.out.println(id);

                allCustomers.add(newCustomer);
                //System.out.println(allCustomers);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }
}
