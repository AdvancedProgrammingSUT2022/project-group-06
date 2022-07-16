package serverapp.views;

import org.json.JSONObject;
import serverapp.controllers.UserController;
import serverapp.enums.Actions;

public class LoginMenu {
    public static String run(String command, JSONObject obj) {
        if(command.equals(Actions.REGISTER.getCharacter())){
            return UserController.register(obj.getString("username"),obj.getString("password"),obj.getString("nickname"));
        }if(command.equals(Actions.LOGIN.getCharacter())){
            return UserController.login(obj.getString("username"),obj.getString("password"));
        }
        return "bad request format";
    }
}
