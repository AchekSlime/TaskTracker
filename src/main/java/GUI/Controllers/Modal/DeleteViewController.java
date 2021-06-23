package GUI.Controllers.Modal;

import java.net.URL;
import java.util.ResourceBundle;

import DBService.Utils.ServiceException;
import GUI.Controllers.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteViewController extends AbstractModalController {

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setParentController(MainMenuController controller) {
        parent = controller;
    }

    @Override
    public void setConfig() {
        switch (type) {
            case "Task":
                try {
                    ObservableList<String> tasks = FXCollections.observableArrayList(parent.getService().getAllTaskTitles());
                    objectComboBox.setItems(tasks);
                    objectComboBox.setPromptText("titles");
                    selectLabel.setText("Delete Task :");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                }
                break;
            case "User":
                try {
                    ObservableList<String> users = FXCollections.observableArrayList(parent.getService().getAllUserNames());
                    objectComboBox.setItems(users);
                    objectComboBox.setPromptText("names");
                    selectLabel.setText("Delete User :");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                }
                break;
            case "Project":
                try {
                    ObservableList<String> projects = FXCollections.observableArrayList(parent.getService().getAllProjectNames());
                    objectComboBox.setItems(projects);
                    objectComboBox.setPromptText("names");
                    selectLabel.setText("Delete Project :");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                }
                break;
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label selectLabel;

    @FXML
    private Button delButton;

    @FXML
    private ComboBox<String> objectComboBox;

    private String delName = "";

    @FXML
    void delButtonOnAction(ActionEvent event) {
        if (!delName.equals("")) {
            switch (type) {
                case "Task":
                    try {
                        parent.getService().deleteTask(delName);
                        parent.setTextArea(parent.getService().getAllTasksAsString());
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) delButton.getScene().getWindow()).close();
                    }
                    break;
                case "User":
                    try {
                        parent.getService().deleteUser(delName);
                        parent.setTextArea(parent.getService().getAllUsersAsString());
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) delButton.getScene().getWindow()).close();
                    }
                    break;
                case "Project":
                    try {
                        parent.getService().deleteProject(delName);
                        parent.setTextArea(parent.getService().getAllProjectsAsString());
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) delButton.getScene().getWindow()).close();
                    }
                    break;
            }
        }
    }

    @FXML
    void objectComboBoxOnAction(ActionEvent event) {
        delName = objectComboBox.getValue();
    }

    @FXML
    void initialize() {
    }

}
