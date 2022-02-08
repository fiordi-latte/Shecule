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
import util.CountryMgmt;
import util.CustomerMgmt;
import util.DivisionMgmt;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @FXML
    public Button updateCust;
    @FXML
    public Button appointments;
    @FXML
    public Button deleteCust;
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    LocalDateTime time = LocalDateTime.now();
    private static Customer updateCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryMgmt.setCountries();
        DivisionMgmt.setDivisions();
        CustomerMgmt.getCustomers();
        //customerID.setCellValueFactory(new PropertyValueFactory<>("custId"));

        appointments.setOnAction(e-> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../Views/AppointmentForm.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Appointments");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        updateCust.setOnAction(e -> {
            updateCustomer = customerView.getSelectionModel().getSelectedItem();


            try {
                Parent root = FXMLLoader.load(getClass().getResource("../Views/UpdateCustomer.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Update Customer");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        deleteCust.setOnAction(e -> {
            updateCustomer = customerView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete?");
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {

                    try {
                        CustomerMgmt.deleteCustomer(updateCustomer);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });

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

        customerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        //System.out.println(customers);
        for(Customer customer : CustomerMgmt.allCustomers){
            System.out.println(customer.getCustName());
        }
        customerView.setItems(CustomerMgmt.allCustomers);


    }

    public static Customer getCustomer(){
        return updateCustomer;
    }


}
