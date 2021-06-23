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

public class SetViewController extends AbstractModalController {

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
        ObservableList<String> users = null;
        try {
            users = FXCollections.observableArrayList(parent.getService().getAllUserNames());
        } catch (ServiceException ex) {
            parent.createExceptionWindow(ex.message(), ex.sysMessage());
        } finally {
            ((Stage) setButton.getScene().getWindow()).close();
        }

        switch (type) {
            case "TaskOnUser":
                try {
                    ObservableList<String> tasks = FXCollections.observableArrayList(parent.getService().getAllTaskTitles());
                    firstObject.setItems(tasks);
                    secondObject.setItems(users);

                    firstObject.setPromptText("Task");
                    secondObject.setPromptText("User");

                    firstObjectLabel.setText("Select Task");
                    secondObjectLabel.setText("Select User");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                } finally {
                    ((Stage) setButton.getScene().getWindow()).close();
                }
                break;

            case "UserOnProject":
                ObservableList<String> projects;
                try {
                    projects = FXCollections.observableArrayList(parent.getService().getAllProjectNames());
                    firstObject.setItems(users);
                    secondObject.setItems(projects);

                    firstObject.setPromptText("User");
                    secondObject.setPromptText("Project");

                    firstObjectLabel.setText("Select User");
                    secondObjectLabel.setText("Select Project");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                } finally {
                    ((Stage) setButton.getScene().getWindow()).close();
                }
                break;
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label firstObjectLabel;

    @FXML
    private Label secondObjectLabel;

    @FXML
    private ComboBox<String> firstObject;

    @FXML
    private ComboBox<String> secondObject;

    @FXML
    private Button setButton;

    private String firstObjectName;

    private String secondObjectName;

    @FXML
    void firstObjectOnAction(ActionEvent event) {
        firstObjectName = firstObject.getValue();
    }

    @FXML
    void secondObjectOnAction(ActionEvent event) {
        secondObjectName = secondObject.getValue();
    }

    @FXML
    void setButtonOnAction(ActionEvent event) {

        if (!(firstObjectName.equals("")) && !(secondObjectName.equals(""))) {
            switch (type) {
                case "TaskOnUser":
                    try {
                        parent.getService().setTasksOnUser(firstObjectName, secondObjectName);
                        parent.setTextArea(parent.getService().getTaskByTitleAsString(firstObjectName));
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) setButton.getScene().getWindow()).close();
                    }
                    break;
                case "UserOnProject":
                    try {
                        parent.getService().setUsersOnProject(firstObjectName, secondObjectName);
                        parent.setTextArea(parent.getService().getUserByNameAsString(firstObjectName));
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) setButton.getScene().getWindow()).close();
                    }
                    break;
            }
        }
    }

    @FXML
    void initialize() {
    }
}
