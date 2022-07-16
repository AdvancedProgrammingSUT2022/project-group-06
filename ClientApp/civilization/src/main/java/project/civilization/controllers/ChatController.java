package project.civilization.controllers;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.civilization.models.Message;
import project.civilization.views.ChatMenu;

import java.util.ArrayList;

public class ChatController {
    public static void sendMessage(String text, BorderPane pane) {
        shiftMessagesUp();
        Message message = new Message(pane, text);
    }

    public static void editMessage(Message message, String typedString) {
        message.setText(typedString);
        ChatMenu.setEditingMessage(null);
    }

    public static void deleteMessage(Message message, BorderPane pane) {
        shiftMessagesDown(message);
        pane.getChildren().remove(message.getHBox());
        Message.getMessages().remove(message);
    }

    public static void shiftMessagesUp() {
        ArrayList<Message> messages = Message.getMessages();
        for (Message message : messages) {
            message.getHBox().setLayoutY(message.getHBox().getLayoutY() - 20);
        }
    }

    public static void shiftMessagesDown(Message deleted) {
        int index = 0;
        ArrayList<Message> messages = Message.getMessages();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getHBox().getLayoutY() == deleted.getHBox().getLayoutY()) {
                index = i;
                break;
            }
        }
        for (int i = 0; i < index; i++) {
            messages.get(i).getHBox().setLayoutY(messages.get(i).getHBox().getLayoutY() + 20);
        }
    }
}
