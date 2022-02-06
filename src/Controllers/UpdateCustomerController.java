package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import util.CountryMgmt;
import util.CustomerMgmt;
import util.DivisionMgmt;
import util.ErrorCheck;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    @FXML
    public TextField customerNameInput;
    public TextField customerPhoneInput;
    public TextField customerAddressInput;
    public TextField customerZipCodeInput;
    public ComboBox customerCountryInput;
    public ComboBox<String> customerStateInput;
    public Button saveButton;
    public Button cancelButton;
    public String selectedDivision;
    LocalDateTime time = LocalDateTime.now();
    Customer updateCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateCustomer = MainFormController.getCustomer();
        customerNameInput.setText(updateCustomer.getCustName());
        customerPhoneInput.setText(updateCustomer.getCustPhone());
        customerAddressInput.setText(updateCustomer.getCustAdress());
        customerZipCodeInput.setText(updateCustomer.getCustZip());

        customerCountryInput.getSelectionModel().select(updateCustomer.getCustCid());

        //customerStateInput.getItems().add(division.getName());
        //Division currentDivision =
        /**
         * Get the name of the selected customers division
         */
        String divisionName = DivisionMgmt.getDivisionName(Integer.parseInt(updateCustomer.getCustDiv()));
        //System.out.println(divisionName);
        DivisionMgmt.getDivisionName(Integer.parseInt(updateCustomer.getCustDiv()));
        customerStateInput.getSelectionModel().select(DivisionMgmt.getDivisionName(Integer.parseInt(updateCustomer.getCustDiv())));
        selectedDivision = divisionName;

        /**
         * get country ID from division
         */

        int countryID = DivisionMgmt.getCountryName(Integer.parseInt(updateCustomer.getCustDiv()));

        customerCountryInput.getSelectionModel().select(CountryMgmt.getCountryName
                (countryID));
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
            /**
             *
             *
            int id;
            if(CustomerMgmt.getAllCustomers().isEmpty()){
                id = 0;
            }
            else {
                id = CustomerMgmt.getAllCustomers().size() + 1;
            }
            */



            String name = customerNameInput.getText();
            String address = customerAddressInput.getText();
            String phone = customerPhoneInput.getText();
            String zip = customerZipCodeInput.getText();
            String divisionID = selectedDivision;

            if(ErrorCheck.isEmpty(name) || ErrorCheck.isEmpty(address) || ErrorCheck.isEmpty(phone) || ErrorCheck.isEmpty(zip) || ErrorCheck.isEmpty(divisionID)){
                Alert newAlert = new Alert(Alert.AlertType.ERROR);
                newAlert.setContentText("Fill all required fields");
                newAlert.showAndWait();
            }

            if(!ErrorCheck.isInt(zip) || !ErrorCheck.isInt(phone)){
                Alert newAlert = new Alert(Alert.AlertType.ERROR);
                newAlert.setContentText("Please enter only numbers in the phone and zip code fields");
                newAlert.showAndWait();
            }

            Customer newCustomer = new Customer(name);
            //newCustomer.setCustID(id);
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
    public void displayError(){
        Alert invalidIput = new Alert(Alert.AlertType.ERROR);
        invalidIput.setTitle("ERROR");
        invalidIput.setContentText("Invalid Input, Please Retry");
        invalidIput.showAndWait();

    }
 }

