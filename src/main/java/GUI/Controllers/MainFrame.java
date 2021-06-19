package GUI.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import DBService.AppService;
import DBService.ApplicationConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MainFrame {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea info;

    @FXML
    private Button testsButton;

    @FXML
    void onClickMethod(ActionEvent event) {
        ApplicationConfig appConf = new ApplicationConfig();
        AppService service = appConf.getService();
        info.setText(info.getText() + service.getAllByString());
    }

    @FXML
    void initialize() {
        assert info != null : "fx:id=\"info\" was not injected: check your FXML file 'test.fxml'.";
        assert testsButton != null : "fx:id=\"testsButton\" was not injected: check your FXML file 'test.fxml'.";

    }
}
