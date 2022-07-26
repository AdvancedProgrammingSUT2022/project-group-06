package serverapp.views;

import org.json.JSONObject;
import serverapp.controllers.UserController;
import serverapp.enums.Actions;

public class Profile {
    public static String run(String command, JSONObject jsonObject) {
        if(command.equals(Actions.DELETEACOUNT.getCharacter())){
            return UserController.deleteAccount(jsonObject.getString("UUID"));
        }
        if(command.equals(Actions.CHANGENICKNAME.getCharacter())){
            return UserController.changeNickname(jsonObject.getString("UUID"),jsonObject.getString("nickname"));
        }
        if(command.equals(Actions.CHAGEPASSWORD.getCharacter())){
            return UserController.changePassword(jsonObject.getString("UUID"),jsonObject.getString("password"));
        }
        if(command.equals(Actions.UNIQUEUSERNAME.getCharacter())){
            return UserController.isUsernameUnique(jsonObject.getString("username"));
        }
        if(command.equals(Actions.getAllFreinShipRequests.getCharacter())){
            return UserController.getAllFreinShipRequests(jsonObject.getString("UUID"));
        }
        if(command.equals(Actions.getAllFriendsNames.getCharacter())){
            return UserController.getAllFriendsNames(jsonObject.getString("UUID"));
        }
        if(command.equals(Actions.sendFriendShipRequest.getCharacter())){
            return UserController.sendFriendShipRequest(jsonObject.getString("UUID"), jsonObject.getString("anotherUsername"));
        }
        if(command.equals(Actions.getPicUrl.getCharacter())){
            return UserController.getPicUrl(jsonObject.getString("UUID"));
        }
        return "bad request format";
    }
}
