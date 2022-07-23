package project.civilization.views;


import javafx.scene.input.MouseEvent;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Menus;

import java.util.UUID;

public class PausePanel {
    public void backToMain(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.MAIN);
    }
    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void backToGameMenu(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.GAMEMenu);
    }

    public void saveGame(MouseEvent mouseEvent) {
        //SaveAndLoadController.saveGameWithJson(UUID.randomUUID().toString());
    }

}