package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contact;
import model.ContactReport;
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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appId.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        //appLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        //appContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appType.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        appCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //userID.setCellValueFactory(new PropertyValueFactory<>("uid"));
        //AppointmentMgmt.setAppointments();

        contact.setItems(ContactMgmt.getContactNames());
       // String selectedContact = contact.getValue();
       // int contactId = ContactMgmt.getContactID(selectedContact);

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

       // contactView.setItems(AppointmentMgmt.reportContacts(contactId));
    }
}
