package clientapp.views;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import clientapp.CivilizationApplication;
import clientapp.controllers.GameController;
import clientapp.controllers.UserController;
import clientapp.enums.Menus;


public class MainPage extends GameMenus{
    

    @FXML
    private Label name;
    @FXML
    private Button profile;

    public void initialize() {
        name.setText("Welcome " + UserController.getLoggedInUser().getUsername());
       

    }

    public void newGame(MouseEvent mouseEvent) {

        CivilizationApplication.changeMenu(Menus.GAMEMenu);

    }

    public void loadGame(MouseEvent mouseEvent) {
        //CivilizationApplication.changeMenu(Menus.LOADGAME);
        MapPage.isANewGame = false;
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
        System.exit(0);
    }

    public void setting(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.SETTING);
    }

    public void logout(MouseEvent mouseEvent)
    {
     
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            
            UserController.setLoggedInUser(null);
            Alert alertFinish=new Alert(AlertType.INFORMATION, "Logout successfull\nProceeding to login menu");
            alertFinish.showAndWait();
            CivilizationApplication.changeMenu(Menus.LOGIN);
        }
    }

}
