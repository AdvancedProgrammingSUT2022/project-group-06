package clientapp.views;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import clientapp.CivilizationApplication;
import clientapp.enums.Menus;

public class GameMenus {

    public void backToMain(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.MAIN);
    }

}
