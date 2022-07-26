package project.civilization.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;

import java.io.FileNotFoundException;
import java.net.URL;

public class LoginPage extends GameMenus{
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private Label error;
    @FXML
    private Button loginButton;


    public void login(MouseEvent mouseEvent) {
        if (username.getText().equals("") || password.getText().equals("")) {
            error.setText("Fill all fields");

            // error.setStyle("-fx-text-fill: #ff0066;");
            return;
        }
        String result;
        if (!(result = UserController.login(username.getText(), password.getText())).equals("logged in")) {
            error.setText(result);

            // error.setStyle("-fx-text-fill: #ff0066;");
            return;
        }

        CivilizationApplication.changeMenu(Menus.MAIN);
    }


    public void register(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.REGISTER);;
    }
    public void exit(MouseEvent mouseEvent)
    {
        UserController.saveUsers();
        System.exit(0);

    }
}