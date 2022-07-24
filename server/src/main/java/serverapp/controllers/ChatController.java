package serverapp.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import serverapp.enums.Actions;
import serverapp.models.Chat;
import serverapp.models.Message;
import serverapp.models.User;

import java.util.ArrayList;

public class ChatController {

    public static String initializePublicChat() {
        if (Chat.getPublicChat() == null) {
            Chat.setPublicChat(new Chat());
            for (String uuid : UserController.getUuids()) {
                Chat.getPublicChat().getParticipants().add(uuid);
                Chat.getPublicChat().getUsersMessages().put(uuid, new ArrayList<>());
            }
        }
        return "public chat initialized successfully";
    }

    public static String sendMessageResponse(JSONObject object) {
        Gson gson = new Gson();
        String json = String.valueOf(object.get("chat"));
        Chat chat = Chat.getPublicChat();//todo: this is a goddamn hardcode, fix it later

        gson = new Gson();
        json = String.valueOf(object.get("message"));
        Message message = gson.fromJson(json, Message.class);

        chat.getAllMessages().add(message);
        chat.getUsersMessages().get(message.getSenderUUID()).add(message);

        ArrayList<Message> messages = chat.getAllMessages();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", Actions.updateMessages.getCharacter());
        gson = new Gson();
        gson.toJson(messages);
        jsonObject.put("messages", gson.toJson(messages));
        for (User user : UserController.getUsersArray()) {
            NetWorkController.broadCast(user, jsonObject.toString());
        }

        return jsonObject.toString();
    }

}
