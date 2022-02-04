package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Customer;
import sample.JDBC;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable{
    @FXML
    public TableView<Customer> customerView;
    @FXML
    public TableColumn<Customer, String> customerName;
    @FXML
    public TableColumn<Customer, Integer> customerID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));

    }
}
