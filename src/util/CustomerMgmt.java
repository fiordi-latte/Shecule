package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.JDBC;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class CustomerMgmt {
    public static LocalDateTime now = LocalDateTime.now();
    Statement sm = null;
    private static final Connection conn = JDBC.getConnection();
    public static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<Customer> getAllCustomers( ) {
        return allCustomers;
    }

    public static void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers VALUES ('"+customer.getCustID()+"', '"+customer.getCustName()+"', '" + customer.getCustAdress() + "', '" + customer.getCustZip() + "','" +customer.getCustPhone()+ "', '"
        + now + "',' "+ User.getCurrentUser() +"' ,'" + now +"', '"+ User.getCurrentUser() +"', '" + customer.getCustDiv() + "')";

        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate();


        allCustomers.add(customer);
    }

    public static void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers set Customer_Name = '" +customer.getCustName()+"', Address = '" + customer.getCustAdress() + "', Postal_Code = '" + customer.getCustZip() + "', Phone = '" +customer.getCustPhone()+ "', Last_Update = '"
                 + now +"', Last_Updated_By = '"+ User.getCurrentUser() +"', Division_ID = '" + customer.getCustDiv() + "' WHERE Customer_ID = '" + customer.getCustID() + "'";
        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate(query);
        System.out.println(customer.getCustID());
        int i = allCustomers.indexOf(customer);

        allCustomers.set(i, customer);
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        String query = "DELETE FROM customers where Customer_ID = '" + customer.getCustID() + "'";
        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate(query);
        int i = allCustomers.indexOf(customer);
        allCustomers.remove(i);
    }

    public static int getCustomerID(String name){
        for(Customer customer : allCustomers){
            if(customer.getCustName().equals(name)){
                return customer.getCustID();
            }
        }
        return 0;
    }

    public static ObservableList<Customer> getCustomers() {
        try {

            String query = "SELECT * FROM customers";
            PreparedStatement sm = conn.prepareStatement(query);
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String div = rs.getString("Division_ID");
                String phone = rs.getString("Phone");
                int id = rs.getInt("Customer_ID");
                Customer newCustomer = new Customer(customerName);
                newCustomer.setCustID(id);
                newCustomer.setCustName(customerName);
                newCustomer.setCustDiv(div);
                newCustomer.setCustZip(zip);
                newCustomer.setCustAddress(address);
                newCustomer.setCustPhone(phone);


                allCustomers.add(newCustomer);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }
}
