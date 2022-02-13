package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ContactReport;
import model.ReportByMonth;
import util.AppointmentMgmt;
import util.ContactMgmt;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class ReportsController implements Initializable {
    @FXML
    public Button exit;
    @FXML
    public TableView contactView;
    @FXML
    public TableView customerView;
    @FXML
    public TableColumn<ContactReport, String> appTitle;
    @FXML
    public TableColumn<ContactReport, String> appType;
    @FXML
    public TableColumn<ContactReport, String> appDescription;
    @FXML
    public TableColumn<ContactReport, String> appEnd;
    @FXML
    public TableColumn<ContactReport, String> appStart;
    @FXML
    public TableColumn<ContactReport, String> appId;
    @FXML
    public TableColumn<ContactReport, String> appCustId;
    @FXML
    public ComboBox<String> contact;
    @FXML
    public RadioButton byTypeRadio;
    @FXML
    public RadioButton byMonthRadio;
    @FXML
    public RadioButton byLocationRadio;
    @FXML
    public TableColumn<ReportByMonth, String> numOfCustomers;
    @FXML
    public TableColumn<ReportByMonth, String> monthOrType;


    //TODO ADD MONTHLY REPORT
    //TODO ADD TYPE REPORT


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numOfCustomers.setCellValueFactory(new PropertyValueFactory<>("reportCount"));
        monthOrType.setCellValueFactory(new PropertyValueFactory<>("reportMonth"));

        byLocationRadio.setOnAction(e->{
            monthOrType.setText("Location");
            try {
                customerView.setItems(AppointmentMgmt.reportByLocation());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });


        byMonthRadio.setOnAction(e->{
            monthOrType.setText("Month");
            try {
                customerView.setItems(AppointmentMgmt.reportByMonths());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        byTypeRadio.setOnAction(e->{
            monthOrType.setText("Type");
            try {
                customerView.setItems(AppointmentMgmt.reportByType());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });



        appId.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("appDescription"));

        appType.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        appCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        contact.setItems(ContactMgmt.getContactNames());


        contact.setOnAction(e->{
            String selectedContact = contact.getValue();
            int contactId = ContactMgmt.getContactID(selectedContact);
            System.out.println(contactId);

            try {
                contactView.setItems(AppointmentMgmt.reportContacts(contactId));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        exit.setOnAction(e-> {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });

       // contactView.setItems(AppointmentMgmt.reportContacts(contactId));
    }
}
