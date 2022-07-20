package project.civilization.views;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.SaveAndLoadController;
import project.civilization.enums.Menus;

import java.util.ArrayList;

public class LoadGamePage {

    public VBox vBox;
    public void initialize(){
        ArrayList<String> names = SaveAndLoadController.loadSavedGamesNames();
        for (String name: names) {
            Button button = new Button(name);
            button.setOnMouseClicked(event -> {
                SaveAndLoadController.loadGameWithJson(name);
                CivilizationApplication.changeMenu(Menus.MAPPAGE);
            });
            vBox.getChildren().add(button);
        }
    }

    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.GAMEMenu);
    }
}
