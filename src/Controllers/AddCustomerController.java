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
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    public TextField customerNameInput;
    public TextField customerPhoneInput;
    public TextField customerAddressInput;
    public TextField customerZipCodeInput;
    public ComboBox customerCountryInput;
    public ComboBox customerStateInput;
    public Button saveButton;
    public Button cancelButton;
    public Country selectedCountry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Country country : CountryMgmt.getCountries()){
            customerCountryInput.getItems().add(country.getName());
        };
        System.out.println(selectedCountry.getID());

        if (selectedCountry != null) {
        System.out.println(selectedCountry.getID());
        selectedCountry = (Country) customerCountryInput.getSelectionModel().getSelectedItem();

        int cid = selectedCountry.getID();

            for (Division division : DivisionMgmt.getDivisions()) {
                if (division.getCountryID() == cid) {
                    customerStateInput.getItems().add(division.getName());
                }
            }
        }


        saveButton.setOnAction(e -> {
            int id;
            if(CustomerMgmt.getAllCustomers().isEmpty()){
                id = 0;
            }
            else {
                id = CustomerMgmt.getAllCustomers().size() + 1;
            }

            String name = customerNameInput.getText();
            Customer newCustomer = new Customer(name);
            newCustomer.setCustID(id);
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
