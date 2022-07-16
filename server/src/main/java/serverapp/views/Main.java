package serverapp.views;

import org.json.JSONObject;
import serverapp.controllers.UserController;
import serverapp.enums.Actions;

public class Main {
    public static String run(String command, JSONObject jsonObject) {
        if(command.equals(Actions.LOGOUT.getCharacter())){
            return UserController.logout(jsonObject.getString("UUID"));
        }
        if(command.equals(Actions.SCOREBOARD.getCharacter())){
            return UserController.getTopUsers();
        }
        return "bad request format";
    }
}
