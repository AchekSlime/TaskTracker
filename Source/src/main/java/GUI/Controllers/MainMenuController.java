package GUI.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DBService.AppService;
import DBService.ApplicationConfig;
import DBService.Utils.ServiceException;
import GUI.Controllers.Modal.AbstractModalController;
import GUI.Controllers.Modal.ExceptionViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {
    private ApplicationConfig appConfig;
    private AppService service;

    public AppService getService() {
        return service;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea info;

    @FXML
    private Button showAllUsers;

    @FXML
    private Button showAllTasks;

    @FXML
    private Button showAllProjects;

    @FXML
    private Button showUser;

    @FXML
    private Button showTask;

    @FXML
    private Button showProject;

    @FXML
    private Button setTaskOnUser;

    @FXML
    private Button setUserOnProject;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button addProjectButton;

    @FXML
    private Button delTaskButton;

    @FXML
    private Button delUserButton;

    @FXML
    private Button delProjectButton;


    public void setTextArea(String text) {
        info.setText(text);
    }

    @FXML
    void showAllUsersOnAction(ActionEvent event) {
        try {
            info.setText(service.getAllUsersAsString());
        } catch (ServiceException ex) {
            createExceptionWindow(ex.message(), ex.sysMessage());
        }
    }

    @FXML
    void showAllTasksOnAction(ActionEvent event) {
        try {
            info.setText(service.getAllTasksAsString());
        } catch (ServiceException ex) {
            createExceptionWindow(ex.message(), ex.sysMessage());
        }
    }

    @FXML
    void showAllProjectsOnAction(ActionEvent event) {
        try {
            info.setText(service.getAllProjectsAsString());
        } catch (ServiceException ex) {
            createExceptionWindow(ex.message(), ex.sysMessage());
        }
    }

    @FXML
    void showTaskOnAction(ActionEvent event) {
        generateModalWindow("Task", "Find Task", "/View/FindView.fxml");
    }

    @FXML
    void showUserOnAction(ActionEvent event) {
        generateModalWindow("User", "Find User", "/View/FindView.fxml");
    }

    @FXML
    void showProjectOnAction(ActionEvent event) {
        generateModalWindow("Project", "Find Project", "/View/FindView.fxml");
    }


    @FXML
    void setTaskOnUserOnAction(ActionEvent event) {
        generateModalWindow("TaskOnUser", "Set Task On User", "/View/SetView.fxml");
    }

    @FXML
    void setUserOnProjectOnAction(ActionEvent event) {
        generateModalWindow("UserOnProject", "Set User On Proj", "/View/SetView.fxml");
    }

    @FXML
    void addTaskButtonOnAction(ActionEvent event) {
        generateModalWindow("Task",  "Add Task", "/View/AddView.fxml");
    }

    @FXML
    void addUserButtonOnAction(ActionEvent event) {
        generateModalWindow("User", "Add User", "/View/AddView.fxml");
    }

    @FXML
    void addProjectButtonOnAction(ActionEvent event) {
        generateModalWindow("Project", "Add Project", "/View/AddView.fxml");
    }

    @FXML
    void delTaskButtonOnAction(ActionEvent event) {
        generateModalWindow("Task", "Delete Task", "/View/DelView.fxml");
    }

    @FXML
    void delUserButtonOnAction(ActionEvent event) {
        generateModalWindow("User", "Delete User", "/View/DelView.fxml");
    }

    @FXML
    void delProjectButtonOnAction(ActionEvent event) {
        generateModalWindow("Project", "Delete Project", "/View/DelView.fxml");
    }


    private void generateModalWindow(String type, String windowTitle, String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        System.out.println("path = " + path);
        Stage modalStage = new Stage();
        modalStage.setTitle(windowTitle);

        init(modalStage, loader);

        Parent root = loader.getRoot();
        modalStage.setResizable(false);
        Scene scene = new Scene(root);
        modalStage.setScene(scene);

        AbstractModalController modalController = loader.getController();
        modalController.setParentController(this);
        modalController.setType(type);
        modalController.setConfig();

        modalStage.showAndWait();
    }

    public void createExceptionWindow(String message, String sysMessage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ExceptionView.fxml"));
        Stage exception = new Stage();
        exception.setTitle("Exception");

        init(exception, loader);

        Parent root = loader.getRoot();
        exception.setResizable(false);
        Scene scene = new Scene(root);
        exception.setScene(scene);

        ExceptionViewController modalController = loader.getController();
        modalController.setConfig(message, sysMessage);

        exception.showAndWait();
    }

    private void init(Stage stage, FXMLLoader loader) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(showUser.getScene().getWindow());
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setOnCloseRequest((windowEvent) -> {
            stage.close();
        });
    }

    @FXML
    void initialize() {
        appConfig = new ApplicationConfig();
        service = appConfig.getService();
    }
}
