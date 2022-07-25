package project.civilization.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.ChatController;
import project.civilization.enums.Menus;
import project.civilization.models.User;

import java.util.ArrayList;

public class ChatMenuNavigation {

    @FXML
    private Button publicChatButton;
    @FXML
    private Button privateChatsButton;
    @FXML
    private Button roomsButton;
    @FXML
    private Button backButton;

    private static ArrayList<String> onlineUsers;

    public static ArrayList<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void initialize() {
    }


    public void goToPublicChats() {
        PublicChatMenu.setChat(ChatController.initializePublicChat());
        CivilizationApplication.changeMenu(Menus.PUBLICCHAT);
    }

    public void goToPrivateChats() {
        onlineUsers = ChatController.getOnlineUsers();
        CivilizationApplication.changeMenu(Menus.STARTPRIVATECHAT);
    }

    public void goToRooms() {
        onlineUsers = ChatController.getOnlineUsers();
        CivilizationApplication.changeMenu(Menus.STARTROOMS);
    }

    public void back() {
        CivilizationApplication.changeMenu(Menus.MAIN);
    }
}
