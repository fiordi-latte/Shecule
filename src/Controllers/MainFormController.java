package Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import util.CustomerMgmt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable{
    @FXML
    public TableView<Customer> customerView;
    @FXML
    public TableColumn<Customer, String> customerName;
    @FXML
    public TableColumn<Customer, Integer> customerID;
    @FXML
    public Button addCust;
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerMgmt.getCustomers();
        //customerID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        //System.out.println(customers);
        for(Customer customer : CustomerMgmt.allCustomers){
            System.out.println(customer.getCustName());
        }
        customerView.setItems(CustomerMgmt.allCustomers);

        addCust.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../Views/AddCustomer.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Add Customer");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }




}
