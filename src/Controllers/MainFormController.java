package Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import util.CustomerMgmt;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable{
    @FXML
    public TableView<Customer> customerView;
    @FXML
    public TableColumn<Customer, String> customerName;
    @FXML
    public TableColumn<Customer, Integer> customerID;
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customers = CustomerMgmt.getCustomers();
        //customerID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        System.out.println(customers);
        //customerView.setItems(customers);

    }


}
