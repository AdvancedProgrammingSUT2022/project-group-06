package serverapp.views;

import org.json.JSONObject;
import serverapp.controllers.ChatController;
import serverapp.enums.Actions;

public class ChatMenu {

    public static String run(String command, JSONObject object) {
        if (command.equals(Actions.initializePublicChat.getCharacter()))
            return ChatController.initializePublicChat();
        else if (command.equals(Actions.sendMessage.getCharacter()))
            return ChatController.sendMessageResponse(object);
        else if (command.equals(Actions.getOnlineUsers.getCharacter()))
            return ChatController.getOnlineUsers();
        else
            return "invalid command";
    }
}
