package project.civilization.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;

import java.io.FileNotFoundException;
import java.net.URL;

public class LoginPage extends GameMenus{
    public Text error;
    @FXML
    private Button loginButton;
    @FXML
    private TextField password;
    @FXML
    private TextField username;

    public void register(MouseEvent mouseEvent) throws FileNotFoundException {

    }

    public void login(MouseEvent mouseEvent) {

    }
}