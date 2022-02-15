package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import util.CountryMgmt;
import util.CustomerMgmt;
import util.DivisionMgmt;
import util.ErrorCheck;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the AddCustomer view<br>
 * Lambda expressions are used for GUI interactions to simplify the code and make it more readable
 * the lambdas also allow for flexibility and reuse
 */

public class AddCustomerController implements Initializable {
    @FXML
    public TextField customerNameInput;
    @FXML
    public TextField customerPhoneInput;
    @FXML
    public TextField customerAddressInput;
    @FXML
    public TextField customerZipCodeInput;
    @FXML
    public ComboBox customerCountryInput;
    @FXML
    public ComboBox<String> customerStateInput;
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;
    @FXML
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
         * Lambda expression handles clicking on the country combobox and sets division combobox appropriately
         */
        customerCountryInput.setOnAction(e -> {
            customerStateInput.getSelectionModel().clearSelection();
            customerStateInput.getItems().clear();

            selectedIndex = customerCountryInput.getSelectionModel().getSelectedIndex();

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

        /**
         * Lambda expression to handle when the user sets the division combobox
         */
        customerStateInput.setOnAction(e->{
           selectedDivision = customerStateInput.getValue();

        });

        /**
         * Lambda expression to handle when the cancel button is pressed
         */
        cancelButton.setOnAction(e->{
            customerCountryInput.setValue(null);
            customerStateInput.setValue(null);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

        /**
         * Lambda expression to handle when the save button is pressed
         */
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
