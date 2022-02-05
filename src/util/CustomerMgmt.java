package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.JDBC;
import model.User;

import java.sql.*;

public class CustomerMgmt {

    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<User> currentUser = FXCollections.observableArrayList();
    public static ObservableList<Customer> getAllCustomers( ) {
        return allCustomers;
    }

    public static void addCustomer(Customer customer) throws SQLException {
        //currentUser = User.getUsers();
        User newUser = new User();
        newUser.setUserName("admin");

        //TODO fix username
        String query = "INSERT INTO customers VALUES ('"+customer.getCustID()+"', '"+customer.getCustName()+"', '" + customer.getCustAdress() + "', '" + customer.getCustZip() + "','" +customer.getCustPhone()+ "', '"
        + customer.getCustCreateTime() + "',' "+ newUser.getUserName() +"' ,'" + customer.getLastUpdate() +"', '"+ newUser.getUserName() +"', '" + customer.getCustDiv() + "')";

        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate();
        Customer newCustomer = new Customer(customer.getCustName());
        allCustomers.add(newCustomer);
    }

    public static ObservableList<Customer> getCustomers() {
        try {

            String query = "SELECT * FROM customers";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                int id = rs.getInt("Customer_ID");
                Customer newCustomer = new Customer(customerName);
                newCustomer.setCustID(id);
                newCustomer.setCustName(customerName);


                allCustomers.add(newCustomer);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }
}
