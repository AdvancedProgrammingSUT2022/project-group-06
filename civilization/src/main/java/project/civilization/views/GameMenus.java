package project.civilization.views;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Menus;

public class GameMenus {

    public void backToMain(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.MAIN);
    }

}
