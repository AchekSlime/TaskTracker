package GUI.Controllers.Modal;

import java.net.URL;
import java.util.ResourceBundle;

import GUI.Controllers.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ExceptionViewController {

    public void setConfig(String message, String sysMessage) {
        messageLabel.setText(message);
        sysMessageArea.setText(sysMessage);
    }

    private String message;

    private String sysMessage;

    @FXML
    private Label messageLabel;

    @FXML
    private Button okButton;

    @FXML
    private TextArea sysMessageArea;

    @FXML
    void okButtonOnAction(ActionEvent event) {
        ((Stage)okButton.getScene().getWindow()).close();
    }

    @FXML
    void initialize() {

    }

}
