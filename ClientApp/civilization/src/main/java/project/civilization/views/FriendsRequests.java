package project.civilization.views;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;

import java.util.ArrayList;

public class FriendsRequests {
    public VBox vbox;
    public VBox requestVbox;

    public void initialize(){
        ArrayList<String> allRequests = UserController.getAllFreinShipRequests();
        for (String r:allRequests) {
            Text text = new Text(r);
            vbox.getChildren().add(text);
        }
    }

    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.PROFILE);
    }
}
