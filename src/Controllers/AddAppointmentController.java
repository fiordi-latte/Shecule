package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    @FXML
    public TextField appTitle;
    @FXML
    public TextField appDescription;
    @FXML
    public TextField appType;
    @FXML
    public TextField appLocation;
    @FXML
    public TextField appStartTime;
    @FXML
    public TextField appEndTime;
    @FXML
    public ComboBox<String> contact;
    @FXML
    public DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String title = appTitle.getText();
        String description = appDescription.getText();
        String type = appType.getText();
        String location = appLocation.getText();
        String start = appStartTime.getText();
        String end = appEndTime.getText();
        LocalDate date = datePicker.getValue();


    }
}
