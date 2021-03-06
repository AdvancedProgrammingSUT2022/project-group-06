package project.civilization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;
import project.civilization.views.MapPage;
import project.civilization.views.Music;

import java.io.IOException;
import java.net.URL;

public class CivilizationApplication extends Application {
    public static Stage stages;
    public static MapPage mapPageController;
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
        UserController.importSavedUsers();
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
/*        Parent root = loadFXML(menuName);
        if (menuName.equals(Menus.CHAT))
            ChatMenu.setPane((BorderPane) root);
        CivilizationApplication.scene.setRoot(root);*/
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
            FXMLLoader loader = new FXMLLoader(address);
            if(menuName.equals(Menus.MAPPAGE))
            {
                MapPage mapPage=new MapPage();
                mapPageController=mapPage;
                loader.setController(mapPage);
            }
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}