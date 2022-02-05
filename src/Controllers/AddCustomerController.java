package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import model.JDBC;
import util.CountryMgmt;
import util.CustomerMgmt;
import util.DivisionMgmt;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    public TextField customerNameInput;
    public TextField customerPhoneInput;
    public TextField customerAddressInput;
    public TextField customerZipCodeInput;
    public ComboBox customerCountryInput;
    public ComboBox<String> customerStateInput;
    public Button saveButton;
    public Button cancelButton;
    private static String selectedCountryString;
    private Object selectedCountry;
    public String selectedDivision;
    LocalDateTime time = LocalDateTime.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Country country : CountryMgmt.getCountryList()){
            customerCountryInput.getItems().add(country.getName());
        }

        /**
         * Handles clicking on the country combobox and sets division combobox appropriately
         */
        customerCountryInput.setOnAction(e -> {
            customerStateInput.getSelectionModel().clearSelection();
            customerStateInput.getItems().clear();

            int selectedIndex = customerCountryInput.getSelectionModel().getSelectedIndex();
            System.out.println(selectedIndex);
            int cid = 0;
            if (selectedIndex == 0) {

                cid = 1;
            }
            else if (selectedIndex == 1) {

                cid = 2;
            }
            else if (selectedIndex == 2) {

                cid = 3;
            }
            for (Division division : DivisionMgmt.getDivisions()) {
                if (division.getCountryID() == cid) {
                    customerStateInput.getItems().add(division.getName());
                }
            }
        });

        customerStateInput.setOnAction(e->{
           selectedDivision = customerStateInput.getValue();

        });

        cancelButton.setOnAction(e->{
            customerCountryInput.setValue(null);
            customerStateInput.setValue(null);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

        saveButton.setOnAction(e -> {
            int id;
            if(CustomerMgmt.getAllCustomers().isEmpty()){
                id = 0;
            }
            else {
                id = CustomerMgmt.getAllCustomers().size() + 1;
            }

            String name = customerNameInput.getText();
            String address = customerAddressInput.getText();
            String phone = customerPhoneInput.getText();
            int zip = Integer.parseInt(customerZipCodeInput.getText());
            int divisionID = DivisionMgmt.getDivisionID(selectedDivision);
            Customer newCustomer = new Customer(name);
            newCustomer.setCustID(id);
            newCustomer.setCustAddress(address);
            newCustomer.setCustDiv(divisionID);
            newCustomer.setCustPhone(phone);
            newCustomer.setCreateTime(time);
            newCustomer.setLastUpdate(time);
            newCustomer.setCustZip(zip);
            try {
                CustomerMgmt.addCustomer(newCustomer);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        });
    }
}
