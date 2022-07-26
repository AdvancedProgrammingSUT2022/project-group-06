package project.civilization.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Actions;
import project.civilization.enums.MenuCategory;
import project.civilization.models.Chat;
import project.civilization.models.Message;
import project.civilization.models.User;
import project.civilization.views.PublicChatMenu;

import java.io.IOException;
import java.util.ArrayList;

public class ChatController {

    public static Integer initializePublicChat() {
        JSONObject object = new JSONObject();
        object.put("action", Actions.initializePublicChat.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            JSONObject jsonObject = new JSONObject(CivilizationApplication.dataInputStream.readUTF());
            return (Integer) jsonObject.get("startedChat");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Message> sendMessage(String text, int chatID) {
        Message message = new Message(text, User.getUuid());
        JSONObject object = new JSONObject();
        object.put("action", Actions.sendMessage.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        Gson gson = new Gson();
        String messageString = gson.toJson(message).toString();
        object.put("message", messageString);
        gson = new Gson();
        object.put("chatID", gson.toJson(chatID));
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            JSONObject jsonObject = new JSONObject(CivilizationApplication.dataInputStream.readUTF());
            return new Gson().fromJson((String) jsonObject.get("messages"), new TypeToken<ArrayList<Message>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getOnlineUsers() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", Actions.getOnlineUsers.getCharacter());
        jsonObject.put("menu", MenuCategory.Chat.getCharacter());
        jsonObject.put("sender", User.getUuid());
        try {
            CivilizationApplication.dataOutputStream.writeUTF(jsonObject.toString());
            CivilizationApplication.dataOutputStream.flush();
            JSONObject object = new JSONObject(CivilizationApplication.dataInputStream.readUTF());
            return new Gson().fromJson((String) object.get("users"), new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateMessages(String messagesJson) {
        JSONObject jsonObject = new JSONObject(messagesJson);
        ArrayList<Message> messages = new Gson().fromJson((String) jsonObject.get("messages"), new TypeToken<ArrayList<Message>>() {
        }.getType());

        PublicChatMenu.showMessages(messages, CivilizationApplication.chatPane);
    }

    public static Integer startPrivateChat(String username) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.startPrivateChat.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        object.put("username", username);
        object.put("senderUuid", User.getUuid());
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            JSONObject jsonObject = new JSONObject(CivilizationApplication.dataInputStream.readUTF());
            return (Integer) jsonObject.get("startedChat");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteMessage(Integer index, Integer chatId) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.deleteMessage.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        object.put("index", index);
        object.put("chatID", chatId);
        object.put("uuid", User.getUuid());
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editMessage(Integer index, Integer chatId) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.editMessageButton.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        object.put("index", index);
        object.put("chatID", chatId);
        object.put("uuid", User.getUuid());
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editMessageFinal(String message) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.editMessageFinal.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        object.put("message", message);
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer startRoom(ArrayList<String> usernames) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.startRoom.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        object.put("senderUuid", User.getUuid());
        object.put("usernames", usernames);
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            JSONObject jsonObject = new JSONObject(CivilizationApplication.dataInputStream.readUTF());
            return (Integer) jsonObject.get("chatID");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void acceptFriendRequest(String username) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.acceptFriendship.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        object.put("invitedUuid", User.getUuid());
        object.put("inviterUsername", username);

        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rejectFriendRequest(String username) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.rejectFriendship.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        object.put("invitedUuid", User.getUuid());
        object.put("inviterUsername", username);
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
