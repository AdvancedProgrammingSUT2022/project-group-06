package project.civilization.views;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;

import java.util.ArrayList;

public class ChallengePage {
    public BorderPane borderPane;
    public VBox invitationBox;
    public Text error;
    private TextField[] textFields = new TextField[10];
    private int numberOfPlayers;
    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.GAMEMenu);
    }

    public void initialize() {
        numberOfPlayers =  InitializeGameInfo.getNumberOFPlayers();
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            textFields[i] = new TextField();
            textFields[i].setPromptText("enter enemy username");
            invitationBox.getChildren().add(textFields[i]);
            textFields[i].setMinWidth(300);
/*            TextField invitationText = new TextField();
            invitationText.setPromptText("enter your invitation text");
            borderPane.getChildren().add(invitationText);*/
        }
        Button apply = new Button();
        apply.setMinWidth(300);
        apply.setText("send");
        invitationBox.getChildren().add(apply);
        apply.setOnMouseClicked(this::applySendInvitation);

        Button back = new Button();
        back.setMinWidth(300);
        back.setText("back");
        back.setOnMouseClicked(this::back);
        invitationBox.getChildren().add(back);

        borderPane.getChildren().add(invitationBox);
    }

    private void applySendInvitation(MouseEvent mouseEvent) {
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            String enemy = textFields[i].getText();
            if(UserController.isUsernameUnique(enemy)){
                error.setText("user number"+(i+1)+" is not available");
                return;
            }
        }
        ArrayList<String> enemyUsernames = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            String enemy = textFields[i].getText();
            enemyUsernames.add(enemy);
        }
        UserController.sendInvitation(enemyUsernames);
/*        for (int i = 0; i < numberOfPlayers - 1; i++) {
            Player player = new Player(textFields[i].getText());
        }
        new Player(UserController.loggedInUser.getUsername());
        CivilizationApplication.changeMenu(Menus.MAPPAGE);*/
    }
}
