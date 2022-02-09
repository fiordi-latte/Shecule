package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import model.JDBC;
import util.CountryMgmt;
import util.CustomerMgmt;
import util.DivisionMgmt;
import util.ErrorCheck;


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
    int selectedIndex;
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

            selectedIndex = customerCountryInput.getSelectionModel().getSelectedIndex();
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
                id = CustomerMgmt.getAllCustomers().get(CustomerMgmt.getAllCustomers().size() - 1).getCustID() + 1;
            }



                String name = customerNameInput.getText();
                String address = customerAddressInput.getText();
                String phone = customerPhoneInput.getText();
                String zip = customerZipCodeInput.getText();
               // String divisionID = selectedDivision;
                int divID = DivisionMgmt.getDivisionID(selectedDivision);

                if(ErrorCheck.isEmpty(name) || ErrorCheck.isEmpty(address) || ErrorCheck.isEmpty(phone) || ErrorCheck.isEmpty(zip)){
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setContentText("Fill all required fields");
                    newAlert.showAndWait();
                    return;
                }

                if(!ErrorCheck.isInt(zip) || !ErrorCheck.isInt(phone)){
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setContentText("Please enter only numbers in the phone and zip code fields");
                    newAlert.showAndWait();
                    return;
                }

                Customer newCustomer = new Customer(name);
                newCustomer.setCustID(id);
                newCustomer.setCustAddress(address);
                newCustomer.setCustDiv(String.valueOf(divID));
                newCustomer.setCustPhone(phone);
                newCustomer.setCreateTime(time);
                newCustomer.setLastUpdate(time);
                newCustomer.setCustZip(zip);
                newCustomer.setCustCid(selectedIndex);
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
