package GUI.Controllers.Modal;

import java.net.URL;
import java.util.ResourceBundle;

import DBService.Entities.Project;
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

public class FindViewController extends AbstractModalController {

    @Override
    public void setParentController(MainMenuController controller) {
        parent = controller;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setConfig() {
        switch (type) {
            case "Task":
                titleLabel.setText("Select Title to display");
                ObservableList<String> tasks = null;
                try {
                    tasks = FXCollections.observableArrayList(parent.getService().getAllTaskTitles());
                    objectComboBox.setItems(tasks);
                    objectComboBox.setPromptText("titles");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                } finally {
                    ((Stage) findButton.getScene().getWindow()).close();
                }

                break;
            case "User":
                titleLabel.setText("Select User to display");
                ObservableList<String> users = null;
                try {
                    users = FXCollections.observableArrayList(parent.getService().getAllUserNames());
                    objectComboBox.setItems(users);
                    objectComboBox.setPromptText("names");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                } finally {
                    ((Stage) findButton.getScene().getWindow()).close();
                }


                break;
            case "Project":
                titleLabel.setText("Select Project to display");
                ObservableList<String> projects = null;
                try {
                    projects = FXCollections.observableArrayList(parent.getService().getAllProjectNames());
                    objectComboBox.setItems(projects);
                    objectComboBox.setPromptText("names");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                } finally {
                    ((Stage) findButton.getScene().getWindow()).close();
                }

                break;
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> objectComboBox;

    @FXML
    private Label titleLabel;

    @FXML
    private Button findButton;

    private String name;

    @FXML
    void objectComboBoxOnAction(ActionEvent event) {
        name = objectComboBox.getValue();
    }

    @FXML
    void findButtonOnAction(ActionEvent event) {
        switch (type) {
            case "Task":
                if (!(name.equals(""))) {
                    try {
                        parent.setTextArea(parent.getService().getTaskByTitleAsString(name));
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) findButton.getScene().getWindow()).close();
                    }
                }
                break;
            case "User":
                if (!(name.equals(""))) {
                    try {
                        parent.setTextArea(parent.getService().getUserByNameAsString(name));
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) findButton.getScene().getWindow()).close();
                    }
                }
                break;
            case "Project":
                if (!(name.equals(""))) {
                    try {
                        parent.setTextArea(parent.getService().getProjectByNameAsString(name));
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) findButton.getScene().getWindow()).close();
                    }
                }
                break;
        }
    }

    @FXML
    void initialize() {
    }
}
