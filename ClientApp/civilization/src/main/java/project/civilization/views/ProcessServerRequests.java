package project.civilization.views;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.UserController;
import project.civilization.enums.Actions;
import project.civilization.enums.Menus;

public class ProcessServerRequests {
    private static String inviteToGame(String inviter) {
        String invitationText = inviter + "wants to play with you!"
                +"\nwould you accept her request?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, invitationText, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Alert alertFinish=new Alert(Alert.AlertType.INFORMATION, "please wait to other enemies accept their requests");
            alertFinish.showAndWait();
            return "accepted";
        }else return "rejected";//regectGameInvitation();
    }

}
