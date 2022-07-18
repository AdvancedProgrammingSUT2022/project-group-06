package serverapp.views;

import org.json.JSONObject;
import serverapp.controllers.InvitationController;
import serverapp.controllers.UserController;
import serverapp.enums.Actions;

public class Game {
    public static String run(String command, JSONObject jsonObject) {
        if(command.equals(Actions.SENDINITATION.getCharacter())){
            return InvitationController.CreateInvitation(jsonObject.getString("UUID"), jsonObject.getString("usernames"));
        }
        if(command.equals(Actions.ACCEPTINVITATION.getCharacter())){
            return InvitationController.acceptInvitation(jsonObject.getString("UUID"),jsonObject.getString("GameUUID"));
        }
        if(command.equals(Actions.REJECTINVITATION.getCharacter())){
            return InvitationController.rejectInvitation(jsonObject.getString("GameUUID"));
        }
        return "bad request format";
    }
}
