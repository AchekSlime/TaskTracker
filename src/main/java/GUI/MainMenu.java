package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/View/MainMenuView.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest((windowEvent) -> {primaryStage.close();});
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
