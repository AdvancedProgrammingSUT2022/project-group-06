package project.civilization.views;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;


public class MainPage extends GameMenus{
    

    @FXML
    private Label name;
    @FXML
    private Button profile;

    public void initialize() {
        name.setText("Welcome");
    }

    public void newGame(MouseEvent mouseEvent) {

        CivilizationApplication.changeMenu(Menus.GAMEMenu);

    }

    public void loadGame(MouseEvent mouseEvent) {
        //CivilizationApplication.changeMenu(Menus.LOADGAME);
        CivilizationApplication.loadMapForTest();
    }

    public void profile(MouseEvent mouseEvent) {
        
        CivilizationApplication.changeMenu(Menus.PROFILE);
    }

    public void scoreBoard(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.SCORE);
    }

    public void exit(MouseEvent mouseEvent) {
        UserController.saveUsers();
        UserController.exit();
        System.exit(0);
    }

    public void setting(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.SETTING);
    }

    public void chat(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.CHATNAVIGATION);
    }

    public void logout(MouseEvent mouseEvent)
    {
     
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            
            UserController.logoutUSer();
            Alert alertFinish=new Alert(AlertType.INFORMATION, "Logout successfully\nProceeding to login menu");
            alertFinish.showAndWait();
            CivilizationApplication.changeMenu(Menus.LOGIN);
        }
    }

}
