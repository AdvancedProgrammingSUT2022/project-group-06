package project.civilization.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.ChatController;
import project.civilization.enums.Menus;

public class ChatMenuNavigation {

    @FXML
    private Button publicChatButton;
    @FXML
    private Button privateChatsButton;
    @FXML
    private Button roomsButton;

    public void initialize() {
    }


    public void goToPublicChats() {
        ChatController.initializePublicChat();
        CivilizationApplication.changeMenu(Menus.PUBLICCHAT);
    }

    public void goToPrivateChats() {
        CivilizationApplication.changeMenu(Menus.STARTPRIVATECHAT);
    }

    public void goToRooms() {

    }

}
