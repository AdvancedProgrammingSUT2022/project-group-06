package project.civilization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;
import project.civilization.views.ChatMenu;
import project.civilization.views.Music;
import project.civilization.views.ProcessServerRequests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;

public class CivilizationApplication extends Application {
    public static Stage stages;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML(Menus.LOGIN);
        //Parent root = loadMapForTest();
        stages = stage;
        assert root != null;
        Scene scene = new Scene(root );
        stage.setTitle("Civilization");
        stage.setScene(scene);
        stage.show();
        //Music.addMusic("songs/start.mp3");
    }

    public static void main(String[] args) {
        UserController.initializeNetwork();
        launch();
        /*
        Scanner scanner = new Scanner(System.in);
        LoginMenu loginMenu = new LoginMenu();
        UserController.importSavedUsers();
        loginMenu.run(scanner);*/
    }
    public static void changeMenu(Menus menuName){
        Parent root = loadFXML(menuName);

        assert root != null;
        Scene scene = new Scene(root );

        stages.setScene(scene);
    }

    public static void showAlert(){

    }
    public static void loadMapForTest(){
        try {
            URL address = new URL(CivilizationApplication.class.getResource("fxml/" + "map-page" + ".fxml").toExternalForm());
            FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static Parent loadFXML(Menus menuName){
        if(menuName == Menus.GAME){
            Music.addMusic("songs/MUS_Genie_A.wav");
        }
        try {
            URL address = new URL(CivilizationApplication.class.getResource("fxml/" + menuName.getCharacter() + ".fxml").toExternalForm());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}