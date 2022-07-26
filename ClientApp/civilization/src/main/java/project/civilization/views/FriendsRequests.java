package project.civilization.views;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.ChatController;
import project.civilization.controllers.GameController;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;

import java.util.ArrayList;

public class FriendsRequests {
    public VBox vbox;
    public VBox requestVbox;
    private Button accept;
    private Button reject;

    public void initialize(){
        ArrayList<String> allRequests = UserController.getAllFreinShipRequests();
        for (String r:allRequests) {
            Text text = new Text(r);
            vbox.getChildren().add(text);
            accept = new Button("accept");
            reject = new Button("reject");
            accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    ChatController.acceptFriendRequest(r);
                    back(mouseEvent);
                }
            });
            reject.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    ChatController.rejectFriendRequest(r);
                    back(mouseEvent);
                }
            });
            vbox.getChildren().add(accept);
            vbox.getChildren().add(reject);
        }
    }

    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.PROFILE);
    }
}
