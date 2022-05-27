package project.civilization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.civilization.controllers.UserController;
import project.civilization.views.LoginMenu;
import project.civilization.views.Music;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class CivilizationApplication extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML();
        assert root != null;
        Scene scene = new Scene(root );
        CivilizationApplication.scene = scene;
        stage.setTitle("Cuphead");
        stage.setScene(scene);
        stage.show();
        Music.addMusic("songs/start.mp3");
    }

    public static void main(String[] args) {
        launch();
/*        Scanner scanner = new Scanner(System.in);
        LoginMenu loginMenu = new LoginMenu();
        UserController.importSavedUsers();
        loginMenu.run(scanner);*/
    }
    private static Parent loadFXML(){
        try {
            URL address = new URL(CivilizationApplication.class.getResource("fxml/login-page.fxml").toExternalForm());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}