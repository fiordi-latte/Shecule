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
    public int selectedIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustomer = MainFormController.getCustomer();
        customerNameInput.setText(updateCustomer.getCustName());
        customerPhoneInput.setText(updateCustomer.getCustPhone());
        customerAddressInput.setText(updateCustomer.getCustAdress());
        customerZipCodeInput.setText(updateCustomer.getCustZip());

        customerCountryInput.getSelectionModel().select(updateCustomer.getCustCid());
        System.out.println(updateCustomer.getCustID());

        /**
         * Get the name of the selected customers division
         */

        try {
            String divisionName = DivisionMgmt.getDivisionName(Integer.parseInt(updateCustomer.getCustDiv()));
            DivisionMgmt.getDivisionName(Integer.parseInt(updateCustomer.getCustDiv()));
            customerStateInput.getSelectionModel().select(DivisionMgmt.getDivisionName(Integer.parseInt(updateCustomer.getCustDiv())));
            selectedDivision = divisionName;
        }
        catch (Exception e){
            System.out.println(updateCustomer.getCustDiv());
        }

        /**
         * get country ID from division
         */

        int countryID = DivisionMgmt.getCountryName(Integer.parseInt(updateCustomer.getCustDiv()));

        customerCountryInput.getSelectionModel().select(CountryMgmt.getCountryName
                (countryID));
        for(Country country : CountryMgmt.getCountryList()){
            customerCountryInput.getItems().add(country.getName());
        }

        String custName = customerNameInput.getText();
        Customer newCustomer = new Customer(custName);
        int id = CustomerMgmt.getCustomerID(custName);

        newCustomer.setCustID(id);

        /**
         * auto-populate state/provence combobox based on already selected option
         */
        selectedIndex = customerCountryInput.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        int newID = 0;
        if (selectedIndex == 0) {

            newID = 1;
        }
        else if (selectedIndex == 1) {

            newID = 2;
        }
        else if (selectedIndex == 2) {

            newID = 3;
        }
        for (Division division : DivisionMgmt.getDivisions()) {
            if (division.getCountryID() == newID) {
                customerStateInput.getItems().add(division.getName());
            }
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
            String name = customerNameInput.getText();
            String address = customerAddressInput.getText();
            String phone = customerPhoneInput.getText();
            String zip = customerZipCodeInput.getText();
            String divisionID = selectedDivision;
            String divID = String.valueOf(DivisionMgmt.getDivisionID(divisionID));

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


            updateCustomer.setCustName(name);
            updateCustomer.setCustAddress(address);
            updateCustomer.setCustDiv(divID);
            updateCustomer.setCustPhone(phone);
            updateCustomer.setCreateTime(time);
            updateCustomer.setLastUpdate(time);
            updateCustomer.setCustZip(zip);
            try {
                CustomerMgmt.updateCustomer(updateCustomer);
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

