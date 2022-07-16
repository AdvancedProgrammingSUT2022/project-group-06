package clientapp.views;

import clientapp.CivilizationApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import clientapp.enums.Menus;

public class StartPrivateChatMenu {
    @FXML
    private Text text;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button startChatButton;

    public void enterChatMenu() {
        CivilizationApplication.changeMenu(Menus.CHAT);
    }
}
