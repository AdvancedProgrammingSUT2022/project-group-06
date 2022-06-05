package project.civilization.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Menus;

public class ChatMenuNavigation {
    @FXML
    private Button publicChatButton;
    @FXML
    private Button privateChatsButton;
    @FXML
    private Button roomsButton;

    public void goToPublicChats() {
        CivilizationApplication.changeMenu(Menus.CHAT);
    }

    public void goToPrivateChats() {
        CivilizationApplication.changeMenu(Menus.STARTPRIVATECHAT);
    }

    public void goToRooms() {

    }

}
