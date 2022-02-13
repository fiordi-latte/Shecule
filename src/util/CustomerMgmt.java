/**
 * Gets and manages all customer information from the database
 */
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

    /**
     * Inserts the customer into the database and adds it to the observable list
     * @param customer
     * @throws SQLException
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers VALUES ('"+customer.getCustID()+"', '"+customer.getCustName()+"', '" + customer.getCustAdress() + "', '" + customer.getCustZip() + "','" +customer.getCustPhone()+ "', '"
        + now + "',' "+ User.getCurrentUser() +"' ,'" + now +"', '"+ User.getCurrentUser() +"', '" + customer.getCustDiv() + "')";

        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate();


        allCustomers.add(customer);
    }

    /**
     * updates the selected customer in the database and sets them to the observable list
     * @param customer
     * @throws SQLException
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers set Customer_Name = '" +customer.getCustName()+"', Address = '" + customer.getCustAdress() + "', Postal_Code = '" + customer.getCustZip() + "', Phone = '" +customer.getCustPhone()+ "', Last_Update = '"
                 + now +"', Last_Updated_By = '"+ User.getCurrentUser() +"', Division_ID = '" + customer.getCustDiv() + "' WHERE Customer_ID = '" + customer.getCustID() + "'";
        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate(query);
        System.out.println(customer.getCustID());
        int i = allCustomers.indexOf(customer);

        allCustomers.set(i, customer);
    }

    /**
     * Delete customer from database and remove from the list
     * @param customer
     * @throws SQLException
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        String query = "DELETE FROM customers where Customer_ID = '" + customer.getCustID() + "'";
        PreparedStatement sm = conn.prepareStatement(query);
        sm.executeUpdate(query);
        int i = allCustomers.indexOf(customer);
        allCustomers.remove(i);
    }

    /**
     * gets the customer id by the passed name
     * @param name
     * @return int
     */

    public static int getCustomerID(String name){
        for(Customer customer : allCustomers){
            if(customer.getCustName().equals(name)){
                return customer.getCustID();
            }
        }
        return 0;
    }

    /**
     * gets the customer name by the passed id
     * @param id
     * @return String
     */
    public static String getCustomerNameByID(int id){
        for(Customer customer : allCustomers){
            if(customer.getCustID() == id){
                return customer.getCustName();
            }
        }
        return "Not found";
    }

    /**
     * gets and set the customer information from the database into a list
     * @return ObservableList
     */
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
