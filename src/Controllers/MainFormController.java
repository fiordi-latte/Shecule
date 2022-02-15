/**
 * Controller for the main form view AKA the customer view
 */
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
import model.Appointment;
import model.Customer;
import model.User;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;


public class MainFormController implements Initializable{
    @FXML
    public TableView<Customer> customerView;
    @FXML
    public TableColumn<Customer, String> customerName;
    @FXML
    public TableColumn<Customer, Integer> custId;
    @FXML
    public Button addCust;
    @FXML
    public Button updateCust;
    @FXML
    public Button appointments;
    @FXML
    public Button deleteCust;
    @FXML
    public Button reports;
    @FXML
    public TableColumn<Customer, String> custPhone;
    @FXML
    public TableColumn<Customer, String> custAddress;
    @FXML
    public TableColumn<Customer, String> custPost;
    @FXML
    public TableColumn<Customer, Integer> custDivision;
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    private static Customer updateCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryMgmt.setCountries();
        DivisionMgmt.setDivisions();
        CustomerMgmt.getCustomers();
        ContactMgmt.setContacts();

        AppointmentMgmt.setAppointments();
        upcomingAppointmentAlert();
        /**
         * Lambda expression to handle when the reports button is pressed
         */
        reports.setOnAction(e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../Views/ReportsForm.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Reports");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        /**
         * Lambda expression to handle when the appointments button is pressed
         */
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

        /**
         * Lambda expression to handle when the update button is pressed
         */
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

        /**
         * Lambda expression to handle when the delete button is pressed
         */
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

        /**
         * Lambda expression to handle when the add button is pressed
         */
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

        custId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        custPhone.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        custPost.setCellValueFactory(new PropertyValueFactory<>("custZip"));
        custDivision.setCellValueFactory(new PropertyValueFactory<>("custDiv"));

        customerView.setItems(CustomerMgmt.allCustomers);

    }

    public static Customer getCustomer(){
        return updateCustomer;
    }

    public static void upcomingAppointmentAlert(){
        for(Appointment app : AppointmentMgmt.getAppointments()){
            if(app.getLocalStartTime().isAfter(ZonedDateTime.now()) && app.getLocalStartTime().isBefore((ZonedDateTime.now().plusMinutes(15)))){
                ErrorCheck.displayAlert("Upcoming appointment at "+ app.getFormattedStartTime() + " ID: " + app.getId());
                return;
            }
        }
        ErrorCheck.displayAlert("No upcoming appointments");
        return;
    }

}
