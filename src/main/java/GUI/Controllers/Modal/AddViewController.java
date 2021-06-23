package GUI.Controllers.Modal;

import DBService.Utils.ServiceException;
import GUI.Controllers.MainMenuController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddViewController extends AbstractModalController {

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
                try {
                    ArrayList<String> usersFromSefvice = parent.getService().getAllUserNames();
                    usersFromSefvice.add("");
                    ObservableList<String> users = FXCollections.observableArrayList(usersFromSefvice);
                    holderComboBox.setItems(users);
                    nameLabel.setText("Enter Task title :");
                    holderLabel.setText("Select user-holder name :");
                    nameTextField.setPromptText("Title");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                }
                break;
            case "User":
                ArrayList<String> projectsFromService;
                try {
                    projectsFromService = parent.getService().getAllProjectNames();
                    projectsFromService.add("");
                    ObservableList<String> projects = FXCollections.observableArrayList(projectsFromService);
                    holderComboBox.setItems(projects);
                    nameLabel.setText("Enter User name :");
                    holderLabel.setText("Select project-holder name :");
                    nameTextField.setPromptText("Name");
                } catch (ServiceException ex) {
                    parent.createExceptionWindow(ex.message(), ex.sysMessage());
                }
                break;
            case "Project":
                nameLabel.setText("Enter Project name :");
                holderComboBox.setDisable(true);
                nameTextField.setPromptText("Project");
                break;
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label nameLabel;

    @FXML
    private Label holderLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> holderComboBox;

    @FXML
    private Button addButton;


    private String holderObjectName = "";

    private String objectName = "";

    @FXML
    void holderComboBoxOnAction(ActionEvent event) {
        holderObjectName = holderComboBox.getValue();
    }

    @FXML
    void nameTextFieldOnAction(ActionEvent event) {
        objectName = nameTextField.getText();
    }

    @FXML
    void addButtonOnAction(ActionEvent event) {
        if (!nameTextField.getText().equals("")) {
            switch (type) {
                case "Task":
                    try {
                        parent.getService().addTask(nameTextField.getText());
                        if (!holderObjectName.equals(""))
                            parent.getService().setTasksOnUser(nameTextField.getText(), holderObjectName);
                        parent.setTextArea(parent.getService().getAllTasksAsString());
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) addButton.getScene().getWindow()).close();
                    }
                    break;
                case "User":
                    try {
                        parent.getService().addUser(nameTextField.getText());
                        if (!holderObjectName.equals(""))
                            parent.getService().setUsersOnProject(nameTextField.getText(), holderObjectName);
                        parent.setTextArea(parent.getService().getAllUsersAsString());
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) addButton.getScene().getWindow()).close();
                    }
                    break;
                case "Project":
                    try {
                        parent.getService().addProject(nameTextField.getText());
                        parent.setTextArea(parent.getService().getAllProjectsAsString());
                    } catch (ServiceException ex) {
                        parent.createExceptionWindow(ex.message(), ex.sysMessage());
                    } finally {
                        ((Stage) addButton.getScene().getWindow()).close();
                    }
                    break;
            }
        }
    }

    @FXML
    void initialize() {
    }
}
