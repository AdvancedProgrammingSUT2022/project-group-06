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

    public static void initializePublicChat() {
        JSONObject object = new JSONObject();
        object.put("action", Actions.initializePublicChat.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Message> sendMessage(String text, Chat chat) {
        Message message = new Message(text, User.getUuid());
        JSONObject object = new JSONObject();
        object.put("action", Actions.sendMessage.getCharacter());
        object.put("menu", MenuCategory.Chat.getCharacter());
        Gson gson = new Gson();
        String messageString = gson.toJson(message).toString();
        object.put("message", messageString);
        gson = new Gson();
        object.put("chat", gson.toJson(chat));
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

    public static void updateMessages(String messagesJson) {
        JSONObject jsonObject = new JSONObject(messagesJson);
        ArrayList<Message> messages = new Gson().fromJson((String) jsonObject.get("messages"), new TypeToken<ArrayList<Message>>() {
        }.getType());

        PublicChatMenu.showMessages(messages, CivilizationApplication.chatPane);
    }

//    public static void editMessage(Message message, String typedString) {
//        message.setText(typedString);
//        PublicChatMenu.setEditingMessage(null);
//    }
//
//    public static void deleteMessage(Message message, BorderPane pane) {
//        shiftMessagesDown(message);
//        pane.getChildren().remove(message.getHBox());
//        Message.getMessages().remove(message);
//    }
//
//    public static void shiftMessagesUp() {
//        ArrayList<Message> messages = Message.getMessages();
//        for (Message message : messages) {
//            message.getHBox().setLayoutY(message.getHBox().getLayoutY() - 20);
//        }
//    }
//
//    public static void shiftMessagesDown(Message deleted) {
//        int index = 0;
//        ArrayList<Message> messages = Message.getMessages();
//        for (int i = 0; i < messages.size(); i++) {
//            if (messages.get(i).getHBox().getLayoutY() == deleted.getHBox().getLayoutY()) {
//                index = i;
//                break;
//            }
//        }
//        for (int i = 0; i < index; i++) {
//            messages.get(i).getHBox().setLayoutY(messages.get(i).getHBox().getLayoutY() + 20);
//        }
//    }
}
