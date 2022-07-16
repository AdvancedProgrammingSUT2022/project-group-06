package clientapp.views;

import clientapp.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import clientapp.CivilizationApplication;
import clientapp.enums.Menus;

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
        if ((result = UserController.login(username.getText(), password.getText())) != null) {
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