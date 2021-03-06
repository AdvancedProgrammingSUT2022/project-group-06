package project.civilization.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Menus;

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
