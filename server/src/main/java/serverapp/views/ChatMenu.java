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
            return ChatController.getOnlineUsers(object);
        else if (command.equals(Actions.startPrivateChat.getCharacter()))
            return ChatController.startPrivateChat(object);
        else if (command.equals(Actions.deleteMessage.getCharacter()))
            return ChatController.deleteMessage(object);
        else if (command.equals(Actions.editMessageButton.getCharacter()))
            return ChatController.editMessagePrimary(object);
        else if (command.equals(Actions.editMessageFinal.getCharacter()))
            return ChatController.editMessageFinal(object);
        else if (command.equals(Actions.startRoom.getCharacter()))
            return ChatController.startRoom(object);
        else
            return "invalid command";
    }
}
