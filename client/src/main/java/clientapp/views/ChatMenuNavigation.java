package clientapp.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import clientapp.CivilizationApplication;
import clientapp.enums.Menus;

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
