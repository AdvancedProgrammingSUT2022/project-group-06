package serverapp.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import serverapp.enums.Actions;
import serverapp.models.Chat;
import serverapp.models.Message;
import serverapp.models.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatController {

    private static int editingIndex;
    private static int editingChatID;
    private static String editingUuid;

    public static String initializePublicChat() {
        if (Chat.getPublicChat() == null) {
            Chat.setPublicChat(new Chat());
            for (String uuid : UserController.getUuids()) {
                Chat.getPublicChat().getParticipants().add(uuid);
                Chat.getPublicChat().getUsersMessages().put(uuid, new ArrayList<>());
            }
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", Actions.updateMessages.getCharacter());
            Gson gson = new Gson();
            jsonObject.put("messages", gson.toJson(Chat.getPublicChat().getAllMessages()));
            for (String participantUuid : Chat.getPublicChat().getParticipants()) {
                User user = UserController.getUserHashMap().get(participantUuid);
                NetWorkController.broadCast(user, jsonObject.toString());
            }
        }
        JSONObject object = new JSONObject();
        object.put("action", Actions.initializePublicChat.getCharacter());
        object.put("startedChat", Chat.getPublicChat().getChatID());
        return object.toString();
    }

    public static String sendMessageResponse(JSONObject object) {
        Gson gson = new Gson();
        String json;
        int chatID = new Gson().fromJson((String) object.get("chatID"), Integer.class);

        Chat chat = null;
        boolean found = false;
        for (Chat chat1 : Chat.getPrivateChats()) {
            if (!found && chat1.getChatID() == chatID) {
                chat = chat1;
                found = true;
            }
        }
        for (Chat chat1 : Chat.getRooms()) {
            if (!found && chat1.getChatID() == chatID) {
                chat = chat1;
                found = true;
            }
        }
        if (!found && chatID == Chat.getPublicChat().getChatID()) {
            chat = Chat.getPublicChat();
        }

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

        for (String participantUuid : chat.getParticipants()) {
            User user = UserController.getUserHashMap().get(participantUuid);
            NetWorkController.broadCast(user, jsonObject.toString());
        }

        return jsonObject.toString();
    }

    public static String getOnlineUsers(JSONObject input) {
        String senderUUID = (String) input.get("sender");
        String senderName = UserController.getUserHashMap().get(senderUUID).getUsername();

        JSONObject object = new JSONObject();
        object.put("action", Actions.getOnlineUsers.getCharacter());
        Gson gson = new Gson();
        ArrayList<String> usersName = new ArrayList<>();
        for (User user : UserController.getUsersArray()) {
            usersName.add(user.getUsername());
        }
        usersName.remove(senderName);
        object.put("users", gson.toJson(usersName));
        return object.toString();
    }

    public static String startPrivateChat(JSONObject object) {
        String username = (String) object.get("username");
        String senderUuid = (String) object.get("senderUuid");
        User invited = UserController.getUserByUserName(username);
        String invitedUuid = null;
        for (HashMap.Entry<String, User> m : UserController.getUserHashMap().entrySet()) {
            if (m.getValue() == invited) {
                invitedUuid = m.getKey();
            }
        }
        Chat chat = null;
        boolean found = false;
        for (Chat chat1 : Chat.getPrivateChats()) {
            if (chat1.getUsersMessages().get(senderUuid) != null
                    && chat1.getUsersMessages().get(invitedUuid) != null) {
                chat = chat1;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("action", Actions.updateMessages.getCharacter());
                Gson gson = new Gson();
                jsonObject.put("messages", gson.toJson(chat1.getAllMessages()));
                NetWorkController.broadCast(UserController.getUserHashMap().get(senderUuid), jsonObject.toString());

                found = true;
                break;
            }
        }
        if (!found) {
            chat = new Chat();
            chat.getParticipants().add(senderUuid);
            chat.getParticipants().add(invitedUuid);
            Chat.getPrivateChats().add(chat);
        }
        for (String participantUuid : chat.getParticipants()) {
            chat.getUsersMessages().put(participantUuid, new ArrayList<>());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", Actions.startPrivateChat.getCharacter());
        jsonObject.put("startedChat", chat.getChatID());
        return jsonObject.toString();
    }

    public static String deleteMessage(JSONObject object) {
        int index = (int) object.get("index");
        int chatId = (int) object.get("chatID");
        String senderUuid = (String) object.get("uuid");
        Chat chat = getChatByID(chatId);

        Message message = chat.getAllMessages().get(index);
        chat.getAllMessages().remove(message);
        chat.getUsersMessages().get(senderUuid).remove(message);

        JSONObject respond = new JSONObject();
        respond.put("action", Actions.updateMessages.getCharacter());
        respond.put("messages", new Gson().toJson(chat.getAllMessages()));

        for (String participantUuid : chat.getParticipants()) {
            User user = UserController.getUserHashMap().get(participantUuid);
            NetWorkController.broadCast(user, respond.toString());
        }
        return respond.toString();
    }

    public static Chat getChatByID(int id) {
        for (Chat chat : Chat.getPrivateChats()) {
            if (chat.getChatID() == id)
                return chat;
        }
        for (Chat chat : Chat.getRooms())
            if (chat.getChatID() == id)
                return chat;
        if (Chat.getPublicChat().getChatID() == id)
            return Chat.getPublicChat();
        return null;
    }

    public static String editMessagePrimary(JSONObject object) {
        editingIndex = (int) object.get("index");
        editingChatID = (int) object.get("chatID");
        editingUuid = (String) object.get("uuid");
        return "";
    }

    public static String editMessageFinal(JSONObject object) {
        String messageText = (String) object.get("message");
        Chat chat = getChatByID(editingChatID);
        Message message = chat.getAllMessages().get(editingIndex);
        message.setText(messageText);

        JSONObject respond = new JSONObject();
        respond.put("action", Actions.updateMessages.getCharacter());
        respond.put("messages", new Gson().toJson(chat.getAllMessages()));

        for (String participantUuid : chat.getParticipants()) {
            User user = UserController.getUserHashMap().get(participantUuid);
            NetWorkController.broadCast(user, respond.toString());
        }
        return respond.toString();
    }

}
