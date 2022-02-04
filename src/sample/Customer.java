package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private int custID;
    private String custName;

    public Customer(String name){
        this.custName = name;
    }

    public void setCustName(String name){
        this.custName = name;
    }

    public String getCustName(){
        return custName;
    }

    public void setCustID(int id){
        this.custID = id;
    }

    public int getCustID(){
        return custID;
    }

    public getCustomers(){

    }
}
