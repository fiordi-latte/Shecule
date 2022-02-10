package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;



public class ReportsController implements Initializable {
    @FXML
    public Button exit;
    @FXML
    public ComboBox contact;
    @FXML
    public TableView contactView;
    @FXML
    public TableView customerView;
    @FXML
    public TableColumn appTitle;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
